package com.kodong.underscore.pgpayment.service;

import com.kodong.underscore.auth.entity.User;
import com.kodong.underscore.auth.repository.UserRepository;
import com.kodong.underscore.pgpayment.dto.PaymentHistoryDto;
import com.kodong.underscore.pgpayment.dto.PaymentHistoryList;
import com.kodong.underscore.pgpayment.dto.PaymentInfoDto;
import com.kodong.underscore.pgpayment.entity.Membership;
import com.kodong.underscore.pgpayment.entity.TossPayment;
import com.kodong.underscore.pgpayment.repository.MemberShipRepository;
import com.kodong.underscore.pgpayment.repository.PaymentsRepository;

import com.kodong.underscore.pgpayment.repository.TossPaymentsSpecs;
import com.kodong.underscore.pgpayment.request.PaymentApprove;
import com.kodong.underscore.pgpayment.response.TossPayApprove;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class PgPayService {

    @Value("${spring.toss-payments.api.key}")
    private String apiKey;
    private final PaymentsRepository paymentsRepository;
    private final UserRepository userRepository;
    private final MemberShipRepository memberShipRepository;

    public PgPayService(PaymentsRepository paymentsRepository, MemberShipRepository memberShipRepository, UserRepository userRepository) {
        this.paymentsRepository = paymentsRepository;
        this.userRepository = userRepository;
        this.memberShipRepository = memberShipRepository;
    }

    public HttpHeaders createHeader(){
        HttpHeaders headers = new HttpHeaders();
        String encodedAuthKey = Base64.getEncoder().encodeToString((apiKey + ":").getBytes(StandardCharsets.UTF_8));

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Basic " + encodedAuthKey);



        return headers;
    }


    //결제 승인
    @Transactional
    public PaymentInfoDto approveTossPayments(String orderId, String paymentKey, int amount){
        HttpHeaders headers = createHeader();
        RestTemplate restTemplate = new RestTemplate();
        PaymentApprove paymentApprove = new PaymentApprove(orderId, paymentKey, amount);

        HttpEntity<PaymentApprove> request = new HttpEntity<>(paymentApprove,headers);
        String apiUrl = "https://api.tosspayments.com/v1/payments/confirm";


        TossPayApprove approveResponse = restTemplate.postForObject(apiUrl, request, TossPayApprove.class);


        String status = approveResponse.getStatus();
        if(Objects.equals(status, "DONE"))
            status = "결제완료";

        String approvedAt = approveResponse.getApprovedAt();
        String[] dateAndTime = approvedAt.split("T");
        String paymentDate = dateAndTime[0];
        String paymentTime = dateAndTime[1];

        LocalDate billingDate = calculateBillingDate(LocalDate.parse(paymentDate));

        Optional<User> userInfo = userRepository.findById(1L);

        User user = userInfo.get();

        user.updateMaxRefundableViews(3);

        //결제한 멤버십 user 정보에 저장
        Membership membership = memberShipRepository.findByAmount(amount);
        user.updateMembership(membership);

        userRepository.save(user);

        TossPayment tossPayment = TossPayment.builder()
                .paymentKey(approveResponse.getPaymentKey())
                .userId(1L)
                .orderId(approveResponse.getOrderId())
                .orderName(approveResponse.getOrderName())
                .method(approveResponse.getMethod())
                .amount(approveResponse.getTotalAmount())
                .approvedAtDate(LocalDate.parse(paymentDate))
                .approvedAtTime(paymentTime)
                .paymentStatus(status)
                .billingDate(billingDate)
                .receiptUrl(approveResponse.getReceipt().getUrl())
                .build();

        PaymentInfoDto paymentInfoDto = PaymentInfoDto.builder()
                .orderId(approveResponse.getOrderId())
                .orderName(approveResponse.getOrderName())
                .approvedAt(approveResponse.getApprovedAt())
                .paymentMethod(approveResponse.getMethod())
                .status(status)
                .amount(approveResponse.getTotalAmount())
                .build();


        paymentsRepository.save(tossPayment);





        return paymentInfoDto;

    }







    private LocalDate calculateBillingDate(LocalDate approvedAtDate) {

        LocalDate billingDate = approvedAtDate.plusMonths(1);

        return billingDate;
    }


    //특정 회원 결제내역
    public PaymentHistoryList getPaymentsHistory(Long userId,String paymentStatus, Pageable pageable){

        Specification<TossPayment> spec = Specification.where(TossPaymentsSpecs.hasUserId(userId));

        if(paymentStatus != null && !paymentStatus.isBlank()){
            spec = spec.and(TossPaymentsSpecs.hasPaymentStatus(paymentStatus));
        }


        int totalPayments = (int) paymentsRepository.count(spec);

        Page<TossPayment> payments = paymentsRepository.findAll(spec, pageable);



        Page<PaymentHistoryDto> paymentHistory = payments.map(payment -> PaymentHistoryDto.builder()
                .orderId(payment.getOrderId())
                .orderName(payment.getOrderName())
                .method(payment.getMethod())
                .approvedAtDate(payment.getApprovedAtDate())
                .paymentStatus(payment.getPaymentStatus())
                .billingDate(payment.getBillingDate())
                .amount(payment.getAmount())
                .receiptUrl(payment.getReceiptUrl())
                .build()
        );

        return new PaymentHistoryList(paymentHistory,totalPayments);

    }













}

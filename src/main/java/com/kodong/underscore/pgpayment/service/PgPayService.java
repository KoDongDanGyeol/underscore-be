package com.kodong.underscore.pgpayment.service;

import com.kodong.underscore.auth.entity.User;
import com.kodong.underscore.auth.repository.UserRepository;
import com.kodong.underscore.pgpayment.dto.*;
import com.kodong.underscore.pgpayment.entity.Membership;
import com.kodong.underscore.pgpayment.entity.TossPayment;
import com.kodong.underscore.pgpayment.repository.MemberShipRepository;
import com.kodong.underscore.pgpayment.repository.PaymentsRepository;
import com.kodong.underscore.pgpayment.repository.TossPaymentsSpecs;
import com.kodong.underscore.pgpayment.request.CancelUserInfo;
import com.kodong.underscore.pgpayment.request.PaymentApprove;
import com.kodong.underscore.pgpayment.response.TossPayApprove;
import com.kodong.underscore.pgpayment.response.TossPayCancel;
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






    private LocalDate calculateBillingDate(LocalDate approvedAtDate) {

        LocalDate billingDate = approvedAtDate.plusMonths(1);

        return billingDate;
    }






    //전체 환불
    public PaymentCancelDto CancelPayment(CancelInfo cancelInfo, Long userId){

        String paymentKey = cancelInfo.getPaymentKey();
        String cancelReason = cancelInfo.getCancelReason();

        TossPayment payment = paymentsRepository.findByUserIdAndPaymentKey(userId, paymentKey);
        int amount = payment.getAmount();


        CancelUserInfo cancelUserInfo = CancelUserInfo.builder()
                .cancelReason(cancelReason)
                .cancelAmount(amount)
                .build();





            TossPayCancel tossPayCancel = CancelTossPayments(cancelUserInfo,paymentKey);
            TossPayCancel.Cancel cancels = tossPayCancel.getCancels()[0];
            String cancelStatus = cancels.getCancelStatus();

            String canceledAt = cancels.getCanceledAt();
            String[] cancelDateAndTime = canceledAt.split("T");
            String canceledDate = cancelDateAndTime[0];
            String canceledTime = cancelDateAndTime[1];

            log.info("canceledDate = {}",canceledDate);
            log.info("canceledTime = {}",canceledTime);




            if(Objects.equals(cancelStatus, "DONE"))
                cancelStatus = "환불완료";


            PaymentCancelDto cancelDto = PaymentCancelDto.builder()
                    .cancelReason(cancels.getCancelReason())
                    .canceledAtDate(canceledDate)
                    .canceledAtTime(canceledTime)
                    .cancelAmount(cancels.getCancelAmount())
                    .cancelStatus(cancelStatus)
                    .build();

            return cancelDto;








    }


    public PaymentCancelDto CancelPaymentPartial(CancelInfo cancelInfo, Long userId){
        String paymentKey = cancelInfo.getPaymentKey();
        String cancelReason = cancelInfo.getCancelReason();


        //결제일 가져오기
        TossPayment payment = paymentsRepository.findByUserIdAndPaymentKey(userId, paymentKey);
        LocalDate approvedAtDate = payment.getApprovedAtDate();

        //유저가 가입한 멤버십 정보 가져오기
        Optional<User> user = userRepository.findById(userId);
        User userInfo = user.get();
        Membership membership = userInfo.getMembership();

        int duration = membership.getDuration();
        int amount = membership.getAmount();

        int period = 0;

        //1은 한달, 12는 1년
        if(duration == 1)
            period = 30;
        else if(duration == 12)
            period = 365;



        LocalDate today = LocalDate.now();
        long days = ChronoUnit.DAYS.between(approvedAtDate, today);//결제일과 현재 날짜 차이

        int refundableAmount = 0;

        if(days < (period / 3)){
            refundableAmount = (amount * 2) / 3;
        }else if(days < Math.round(((double) period /2))){
            refundableAmount = amount / 2;

        }

        CancelUserInfo cancelUserInfo = CancelUserInfo.builder()
                .cancelReason(cancelReason)
                .cancelAmount(refundableAmount)
                .build();


        TossPayCancel tossPayCancel = CancelTossPayments(cancelUserInfo, paymentKey);

        TossPayCancel.Cancel cancels = tossPayCancel.getCancels()[0];
        String cancelStatus = cancels.getCancelStatus();

        String canceledAt = cancels.getCanceledAt();
        String[] cancelDateAndTime = canceledAt.split("T");
        String canceledDate = cancelDateAndTime[0];
        String canceledTime = cancelDateAndTime[1];

        log.info("canceledDate = {}",canceledDate);
        log.info("canceledTime = {}",canceledTime);




        if(Objects.equals(cancelStatus, "DONE"))
            cancelStatus = "환불완료";


        PaymentCancelDto cancelDto = PaymentCancelDto.builder()
                .cancelReason(cancels.getCancelReason())
                .canceledAtDate(canceledDate)
                .canceledAtTime(canceledTime)
                .cancelAmount(cancels.getCancelAmount())
                .cancelStatus(cancelStatus)
                .build();

        return cancelDto;



    }

    //토스페이먼츠 결제 취소 요청
    private TossPayCancel CancelTossPayments(CancelUserInfo cancelUserInfo,String paymentKey) {
        HttpHeaders headers = createHeader();
        RestTemplate restTemplate = new RestTemplate();



        HttpEntity<CancelUserInfo> request = new HttpEntity<>(cancelUserInfo,headers);

        String url = "https://api.tosspayments.com/v1/payments/"+paymentKey+"/cancel";

        TossPayCancel tossPayCancel = restTemplate.postForObject(url, request, TossPayCancel.class);



        return tossPayCancel;

    }



}

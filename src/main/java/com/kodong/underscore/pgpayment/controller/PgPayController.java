package com.kodong.underscore.pgpayment.controller;

import com.kodong.underscore.auth.entity.User;
import com.kodong.underscore.auth.repository.UserRepository;
import com.kodong.underscore.pgpayment.dto.CancelInfo;
import com.kodong.underscore.pgpayment.dto.PaymentCancelDto;
import com.kodong.underscore.pgpayment.dto.PaymentHistoryList;
import com.kodong.underscore.pgpayment.dto.PaymentInfoDto;
import com.kodong.underscore.pgpayment.entity.TossPayment;
import com.kodong.underscore.pgpayment.repository.PaymentsRepository;
import com.kodong.underscore.pgpayment.service.PgPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@RestController
@RequestMapping("/admin/pgPay")
@Slf4j
public class PgPayController {
    private final PgPayService pgPayService;
    private final PaymentsRepository paymentsRepository;
    private final UserRepository userRepository;

    public PgPayController(PgPayService pgPayService, PaymentsRepository paymentsRepository, UserRepository userRepository) {
        this.pgPayService = pgPayService;
        this.paymentsRepository = paymentsRepository;
        this.userRepository = userRepository;
    }




    @GetMapping("/approve")
    @ResponseBody
    public PaymentInfoDto approvePayment(@RequestParam("orderId") String orderId,
                                         @RequestParam("paymentKey") String paymentKey,
                                         @RequestParam("amount") int amount){

        log.info("orderId={}",orderId);
        log.info("paymentKey={}",paymentKey);
        log.info("amount={}",amount);

        PaymentInfoDto tossPayApproveInfo = pgPayService.approveTossPayments(orderId,paymentKey,amount);



        return tossPayApproveInfo;
    }





    @GetMapping("{userId}/payment-history")
    public ResponseEntity<PaymentHistoryList> getPayments(@PathVariable("userId") Long userId,
                                                          @RequestParam(value = "page", defaultValue = "0") int page,
                                                          @RequestParam(value="size",defaultValue = "5") int size,
                                                          @RequestParam(value = "paymentStatus",required = false) String paymentStatus){
        log.info("userId={}",userId);
        log.info("paymentStatus={}",paymentStatus);

        Pageable pageable = PageRequest.of(page, size);
        PaymentHistoryList paymentsHistory = pgPayService.getPaymentsHistory(userId, paymentStatus, pageable);


        return ResponseEntity.ok(paymentsHistory);


    }

    @PostMapping("/refund")
    public ResponseEntity<?> refundPayment(@RequestBody CancelInfo cancelInfo,
                                           @RequestParam("userId") Long userId){

        String paymentKey = cancelInfo.getPaymentKey();

        log.info("userId = {}",userId);
        log.info("paymentKey={}",paymentKey);

        TossPayment payment = paymentsRepository.findByUserIdAndPaymentKey(userId, paymentKey);
        LocalDate approvedAtDate = payment.getApprovedAtDate();

        Optional<User> userInfo = userRepository.findById(userId);
        Integer maxRefundableViews = userInfo.get().getMaxRefundableViews();



        boolean check = IsRefundable(approvedAtDate, maxRefundableViews);
        log.info("check = {}", check);



        try{
            if(check){
                PaymentCancelDto paymentCancelDto = pgPayService.CancelPayment(cancelInfo, userId);
                return ResponseEntity.ok(paymentCancelDto);
            }else{
                PaymentCancelDto paymentCancelDto = pgPayService.CancelPaymentPartial(cancelInfo, userId);
                return ResponseEntity.ok(paymentCancelDto);
            }



        }catch (IllegalStateException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }



    }


    private boolean IsRefundable(LocalDate approvedAtDate, int maxRefundableViews) {
        boolean check = false;

        LocalDate today = LocalDate.now();

        long days = ChronoUnit.DAYS.between(approvedAtDate, today);//결제일과 현재 날짜 차이

        if((days< 7 && days >= 0) && (maxRefundableViews > 0)){
            check = true;
        }else if((days>7) || (maxRefundableViews<=0)){
            return check;
        }


        return check;
    }






}

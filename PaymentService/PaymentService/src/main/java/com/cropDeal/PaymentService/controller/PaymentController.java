package com.cropDeal.PaymentService.controller;

import com.cropDeal.PaymentService.dto.OrderDTO;
import com.cropDeal.PaymentService.dto.PaymentRequestDTO;
import com.cropDeal.PaymentService.entity.Payment;
import com.cropDeal.PaymentService.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    @Autowired
    private  PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/process")
    public Payment makePayment(@RequestBody PaymentRequestDTO request) {
        return paymentService.processPayment(request.getOrderId(), request.getAccountNumber());
    }



    @GetMapping("/message")
    public String getMessage(){
        return "YEs you can make the payment.. :)";
    }


}
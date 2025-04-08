package com.CropDeal.PaymentGateWay.controller;

import com.CropDeal.PaymentGateWay.dto.ProductRequest;
import com.CropDeal.PaymentGateWay.dto.StripeResponse;
import com.CropDeal.PaymentGateWay.service.StripeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cropdeal/v1")
public class StripeController {

    private StripeService stripeService;

    public StripeController(StripeService stripeService){
        this.stripeService = stripeService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<StripeResponse> checkoutProduct(@RequestBody ProductRequest productRequest){
        StripeResponse stripeResponse = stripeService.checkoutProducts(productRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(stripeResponse);
    }

    @GetMapping("/success")
    public ResponseEntity<String> getSuccess(){
        return ResponseEntity.ok("Your payment is confirmend Go to the Invoic part");
    }
    @GetMapping("/failed")
    public ResponseEntity<String> getFail(){
        return ResponseEntity.ok("Your payment is not  confirmend Go to the Invoic part");
    }


}

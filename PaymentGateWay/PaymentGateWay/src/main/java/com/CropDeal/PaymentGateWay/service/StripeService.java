package com.CropDeal.PaymentGateWay.service;


import com.CropDeal.PaymentGateWay.dto.ProductRequest;
import com.CropDeal.PaymentGateWay.dto.StripeResponse;
import com.stripe.Stripe;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeService {
     // Stripe API
    // productName , amount , quantity , currency
    // -> return statement Sessionid and url

    @Value("${stripe.secretKey}")
    private String secretKey;

  public StripeResponse checkoutProducts(ProductRequest productRequest){
      Stripe.apiKey = secretKey;
      SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData.builder()
              .setName(productRequest.getName()).build();

     SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
              .setCurrency(productRequest.getCurrency()==null?"USD":productRequest.getCurrency())
              .setUnitAmount(productRequest.getAmount())
             .setProductData(productData)
              .build();

     SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder().setQuantity(productRequest.getQuentity())
             .setPriceData(priceData)
             .build();

     SessionCreateParams params = SessionCreateParams.builder().setMode(SessionCreateParams.Mode.PAYMENT)
             .setSuccessUrl("http://localhost:8088/cropdeal/v1/success")
             .setCancelUrl("http://localhost:8088/cropdeal/v1/failed")
             .addLineItem(lineItem)
             .build();

     Session session = null;
     try{
        session =  Session.create(params);
     } catch (StripeException e) {
         System.out.println("Some thing went wrong ");
         throw new RuntimeException(e);
     }

      StripeResponse response = new StripeResponse();
      response.setStatus("SUCCESS");
      response.setMessage("PAYMENT IS DONE");
      response.setSessionId(session.getId());
      response.setSessionUrl(session.getUrl());
      return response;


  }

}

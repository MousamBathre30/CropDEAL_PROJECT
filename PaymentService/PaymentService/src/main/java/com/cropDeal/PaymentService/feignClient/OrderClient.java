package com.cropDeal.PaymentService.feignClient;

import com.cropDeal.PaymentService.dto.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ORDER-SERVICE")
public interface OrderClient {
    @GetMapping("orders/{orderId}")
    OrderDTO getOrderById(@PathVariable("orderId") Long orderId);
}

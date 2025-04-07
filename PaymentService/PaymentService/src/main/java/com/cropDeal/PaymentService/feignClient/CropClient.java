package com.cropDeal.PaymentService.feignClient;

import com.cropDeal.PaymentService.dto.CropDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;

@FeignClient(name = "CROP-SERVICE")
public interface CropClient {

    @GetMapping("/crops/{cropId}")
    List<CropDTO> getCropById(@PathVariable("cropId") Long cropId);
}

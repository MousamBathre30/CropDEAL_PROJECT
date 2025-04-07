package com.cropdeal.Orderservice.feignclient;

import com.cropdeal.Orderservice.dto.CropDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "CROP-SERVICE", url = "http://localhost:8081/crops")
public interface CropClient {
    @GetMapping("/cropId/{cropId}")
    List<CropDTO> getCropById(@PathVariable Long cropId); // ✅ Should return List
}


package com.cropdealer.CropService.controller;

import com.cropdealer.CropService.dto.CropDTO;
import com.cropdealer.CropService.entity.Crop;
import com.cropdealer.CropService.service.CropService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/crops")
public class CropController {

    @Autowired
    private  CropService cropService;
    private static final Logger logger = LoggerFactory.getLogger(CropController.class);


    @PostMapping
    public ResponseEntity<Crop> addCrop(@RequestBody CropDTO cropDTO) {
        logger.info("Received Crop Data: {}", cropDTO);

        Crop savedCrop = cropService.addCrop(cropDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedCrop);
    }

    @GetMapping
    public ResponseEntity<List<Crop>> getAllCrops() {
        List<Crop> crops = cropService.getAllCrops();
        return ResponseEntity.ok(crops);
    }

    @GetMapping("/message")
    public ResponseEntity<String> message() {
        return ResponseEntity.ok("Crop service is working");
    }

    @GetMapping("/farmer/{farmerID}")
    public ResponseEntity<List<Crop>> getCropsByFarmer(@PathVariable Long farmerID) {
        List<Crop> crops = cropService.getCropsByFarmer(farmerID);
        return ResponseEntity.ok(crops);
    }
}

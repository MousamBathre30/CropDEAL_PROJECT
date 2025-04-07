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

    @GetMapping("/cropId/{cropId}")
    public ResponseEntity<List<CropDTO>> getCropById(@PathVariable Long cropId){
        List<Crop> savedCrop = cropService.getCropsByCropID(cropId);
        List<CropDTO> cropDTOs = savedCrop.stream().map(this::convertToDTO).toList();
        return ResponseEntity.ok(cropDTOs); // ✅ Correct
    }

    private CropDTO convertToDTO(Crop crop) {
        CropDTO dto = new CropDTO();
        dto.setFarmerId(crop.getFarmerId());
        dto.setCropName(crop.getCropName());
        dto.setCropType(crop.getCropType());
        dto.setPrice(crop.getPrice());
        dto.setQuantity(crop.getQuantity());
        dto.setLocation(crop.getLocation());
        return dto;
    }

}

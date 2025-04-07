package com.cropdealer.CropService.service;

import com.cropdealer.CropService.dto.CropDTO;
import com.cropdealer.CropService.entity.Crop;
import com.cropdealer.CropService.repository.CropRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(force = true)

@Slf4j
@Service
public class CropService {

    @Autowired
    private  CropRepository cropRepository;


    public Crop addCrop(CropDTO cropDTO) {
        log.info("Received Crop Data: {}", cropDTO);

        Crop crop = new Crop();
        crop.setFarmerId(cropDTO.getFarmerId());
        crop.setCropName(cropDTO.getCropName());
        crop.setCropType(cropDTO.getCropType());
        crop.setPrice(cropDTO.getPrice());
        crop.setQuantity(cropDTO.getQuantity());
        crop.setLocation(cropDTO.getLocation());

        Crop savedCrop = cropRepository.save(crop);
        log.info("Saved Crop: {}", savedCrop);

        return savedCrop;
    }

    public List<Crop> getAllCrops() {
        return cropRepository.findAll();
    }

    public List<Crop> getCropsByFarmer(Long farmerId) {
        return cropRepository.findByFarmerId(farmerId);
    }

    public List<Crop> getCropsByCropID(Long id){
        return cropRepository.findByCropId(id);
    }
}

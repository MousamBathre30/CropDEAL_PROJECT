package com.cropdealer.CropService.repository;

import com.cropdealer.CropService.entity.Crop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CropRepository extends JpaRepository<Crop , Long> {
    List<Crop> findByFarmerID(Long farmerID);

}

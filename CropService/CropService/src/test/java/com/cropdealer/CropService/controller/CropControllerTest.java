package com.cropdealer.CropService.controller;

import com.cropdealer.CropService.dto.CropDTO;
import com.cropdealer.CropService.entity.Crop;
import com.cropdealer.CropService.service.CropService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CropControllerTest {

    @Mock
    private CropService cropService;

    @InjectMocks
    private CropController cropController;

    private Crop crop;
    private CropDTO cropDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        cropDTO = new CropDTO();
        cropDTO.setCropName("Rice");
        cropDTO.setCropType("Kharif");
        cropDTO.setFarmerId(10L);
        cropDTO.setPrice((long) 2200.0);

        crop = new Crop();
        crop.setCropId(1L);
        crop.setCropName("Rice");
        crop.setCropType("Kharif");
        crop.setFarmerId(10L);
        crop.setPrice((long) 2200.0);
    }

    @Test
    void testUpdateCrop() {
        Long cropId = 1L;
        when(cropService.updateCrop(eq(cropId), any(CropDTO.class))).thenReturn(crop);

        ResponseEntity<Crop> response = cropController.updateCrop(cropId, cropDTO);

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Rice", response.getBody().getCropName());
        verify(cropService, times(1)).updateCrop(cropId, cropDTO);
    }
}

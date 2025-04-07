package com.cropdealer.CropService;

import com.cropdealer.CropService.service.CropService;
import org.junit.jupiter.api.Test;




import com.cropdealer.CropService.dto.CropDTO;
import com.cropdealer.CropService.entity.Crop;
import com.cropdealer.CropService.repository.CropRepository;
import org.junit.jupiter.api.BeforeEach;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CropServiceTest {

	private CropService cropService;
	private CropRepository cropRepository;

	@BeforeEach
	void setUp() {
		cropRepository = mock(CropRepository.class);
		cropService = new CropService();
		cropService.setCropRepository(cropRepository);
	}

	@Test
	void testAddCrop_shouldReturnSavedCrop() {
		// Arrange
		CropDTO dto = new CropDTO();
		dto.setFarmerId(1L);
		dto.setCropName("Wheat");
		dto.setCropType("Grain");
		dto.setPrice((long) 120.0);
		dto.setQuantity(10.0);
		dto.setLocation("Delhi");

		Crop savedCrop = new Crop();
		savedCrop.setFarmerId(dto.getFarmerId());
		savedCrop.setCropName(dto.getCropName());
		savedCrop.setCropType(dto.getCropType());
		savedCrop.setPrice(dto.getPrice());
		savedCrop.setQuantity(dto.getQuantity());
		savedCrop.setLocation(dto.getLocation());

		when(cropRepository.save(any(Crop.class))).thenReturn(savedCrop);

		// Act
		Crop result = cropService.addCrop(dto);

		// Assert
		assertNotNull(result);
		assertEquals("Wheat", result.getCropName());
		verify(cropRepository, times(1)).save(any(Crop.class));
	}

	@Test
	void testGetAllCrops_shouldReturnList() {
		// Arrange
		List<Crop> crops = Arrays.asList(new Crop(), new Crop());
		when(cropRepository.findAll()).thenReturn(crops);

		// Act
		List<Crop> result = cropService.getAllCrops();

		// Assert
		assertEquals(2, result.size());
		verify(cropRepository).findAll();
	}

	@Test
	void testGetCropsByFarmer_shouldReturnCrops() {
		// Arrange
		Long farmerId = 1L;
		List<Crop> crops = Arrays.asList(new Crop(), new Crop());
		when(cropRepository.findByFarmerId(farmerId)).thenReturn(crops);

		// Act
		List<Crop> result = cropService.getCropsByFarmer(farmerId);

		// Assert
		assertEquals(2, result.size());
		verify(cropRepository).findByFarmerId(farmerId);
	}

	@Test
	void testGetCropsByCropID_shouldReturnCrops() {
		// Arrange
		Long cropId = 101L;
		List<Crop> crops = Arrays.asList(new Crop());
		when(cropRepository.findByCropId(cropId)).thenReturn(crops);

		// Act
		List<Crop> result = cropService.getCropsByCropID(cropId);

		// Assert
		assertEquals(1, result.size());
		verify(cropRepository).findByCropId(cropId);
	}
}

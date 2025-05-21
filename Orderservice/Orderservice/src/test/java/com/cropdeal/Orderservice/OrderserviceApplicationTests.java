package com.cropdeal.Orderservice;

import com.cropdeal.Orderservice.dto.CropDTO;
import com.cropdeal.Orderservice.entitty.Order;
import com.cropdeal.Orderservice.feignclient.CropClient;
import com.cropdeal.Orderservice.repo.OrderRepository;
import com.cropdeal.Orderservice.service.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

	@Mock
	private OrderRepository orderRepository;

	@Mock
	private CropClient cropClient;

	@InjectMocks
	private OrderServiceImpl orderService;

	private Order order;
	private CropDTO cropDTO;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		order = new Order();
		order.setId(1L);
		order.setCropId(10L);
		order.setDealerId(100L);
		order.setQuantity(5.0);

		cropDTO = new CropDTO();
		cropDTO.setCropId(10L);
		cropDTO.setCropName("Wheat");
		cropDTO.setCropType("Grain");
		cropDTO.setQuantity(10.0);
		cropDTO.setFarmerId(200L);
	}

	@Test
	void testPlaceOrder_Success() {
		when(cropClient.getCropById(order.getCropId())).thenReturn(Collections.singletonList(cropDTO));
		when(orderRepository.save(any(Order.class))).thenReturn(order);

		Order result = orderService.placeOrder(order);

		assertEquals("Pending", result.getStatus());
		assertEquals("Wheat", result.getCropName());
		assertEquals("Grain", result.getCropType());
		assertEquals(200L, result.getFarmerId());
		verify(orderRepository, times(1)).save(order);
	}

	@Test
	void testPlaceOrder_CropNotFound() {
		when(cropClient.getCropById(order.getCropId())).thenReturn(Collections.emptyList());

		Exception exception = assertThrows(RuntimeException.class, () -> {
			orderService.placeOrder(order);
		});

		assertTrue(exception.getMessage().contains("Crop not found"));
	}

	@Test
	void testPlaceOrder_InsufficientQuantity() {
		cropDTO.setQuantity(3.0);
		when(cropClient.getCropById(order.getCropId())).thenReturn(Collections.singletonList(cropDTO));

		Exception exception = assertThrows(RuntimeException.class, () -> {
			orderService.placeOrder(order);
		});

		assertTrue(exception.getMessage().contains("Requested quantity is more than available"));
	}
}
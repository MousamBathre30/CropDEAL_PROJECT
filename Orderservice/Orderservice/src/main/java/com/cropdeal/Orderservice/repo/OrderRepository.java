package com.cropdeal.Orderservice.repo;

import com.cropdeal.Orderservice.entitty.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByDealerId(Long dealerId);

    List<Order> findByDealerIdAndStatus(Long dealerId, String pending);

    List<Order> findByCropId(Long cropId);
}

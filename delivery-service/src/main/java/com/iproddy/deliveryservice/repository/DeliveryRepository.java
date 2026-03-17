package com.iproddy.deliveryservice.repository;

import com.iproddy.deliveryservice.model.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
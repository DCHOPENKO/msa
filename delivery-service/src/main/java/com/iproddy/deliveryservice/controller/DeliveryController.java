package com.iproddy.deliveryservice.controller;

import com.iproddy.common.util.JsonUtil;
import com.iproddy.deliveryservice.controller.dto.DeliveryDto;
import com.iproddy.deliveryservice.mapper.DeliveryMapper;
import com.iproddy.deliveryservice.model.entity.Delivery;
import com.iproddy.deliveryservice.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;
    private final DeliveryMapper deliveryMapper;

    @GetMapping
    public List<DeliveryDto.Response.Base> findAll() {
        List<Delivery> deliveries = deliveryService.findAll();
        return deliveryMapper.toResponseList(deliveries);
    }

    @GetMapping("/{id}")
    public DeliveryDto.Response.Base findById(@PathVariable Long id) {
        Delivery delivery = deliveryService.findByIdOrThrow(id);
        return deliveryMapper.toResponse(delivery);
    }

    @PostMapping
    public DeliveryDto.Response.Base create(@RequestBody DeliveryDto.Request.Base request) {
        log.info("Starting create new delivery with payload: {}", JsonUtil.stringify(request));
        Delivery delivery = deliveryMapper.toEntity(request);
        Delivery saved = deliveryService.save(delivery);
        log.info("Delivery created with id {}", saved.getId());
        return deliveryMapper.toResponse(saved);
    }

    @PutMapping("/{id}")
    public DeliveryDto.Response.Base update(@PathVariable Long id, @RequestBody DeliveryDto.Request.Base request) {
        log.info("Starting updating delivery with id: {}, payload: {}", id, JsonUtil.stringify(request));
        Delivery delivery = deliveryMapper.toEntity(request);
        delivery.setId(id);
        Delivery updated = deliveryService.update(delivery);
        log.info("Delivery updated with id {}", updated.getId());
        return deliveryMapper.toResponse(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.info("Attempt to delete delivery with id: {}", id);
        deliveryService.delete(id);
    }
}

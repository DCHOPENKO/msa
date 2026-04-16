package com.iproddy.deliveryservice.service;

import com.iproddy.common.exception.NotFoundException;
import com.iproddy.deliveryservice.kafka.producer.DeliveryCreateEventProducer;
import com.iproddy.deliveryservice.model.entity.Delivery;
import com.iproddy.deliveryservice.model.enums.DeliveryStatus;
import com.iproddy.deliveryservice.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryCreateEventProducer deliveryCreateEventProducer;

    @Transactional(readOnly = true)
    public List<Delivery> findAll() {
        return deliveryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Delivery findByIdOrThrow(Long id) {
        return deliveryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Delivery with id: %s not found".formatted(id)));
    }

    public Delivery save(Delivery entity) {
        entity.setId(null);
        entity.setStatus(DeliveryStatus.CREATED);
        Delivery savedEntity = deliveryRepository.save(entity);
        deliveryCreateEventProducer.send(savedEntity);
        return savedEntity;
    }

        public Delivery update(Delivery entity) {
        findByIdOrThrow(entity.getId());
        return deliveryRepository.save(entity);
    }

    public void delete(Long id) {
        deliveryRepository.deleteById(id);
    }
}
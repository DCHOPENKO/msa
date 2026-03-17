package com.iproddy.deliveryservice.service;

import com.iproddy.common.exception.NotFoundException;
import com.iproddy.deliveryservice.model.entity.Delivery;
import com.iproddy.common.model.enums.DeliveryStatus;
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
        return deliveryRepository.save(entity);
    }

        public Delivery update(Delivery entity) {
        findByIdOrThrow(entity.getId());
        return deliveryRepository.save(entity);
    }

    public void delete(Long id) {
        deliveryRepository.deleteById(id);
    }
}
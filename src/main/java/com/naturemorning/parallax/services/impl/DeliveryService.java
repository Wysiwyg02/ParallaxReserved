/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naturemorning.parallax.services.impl;

import com.naturemorning.parallax.services.IDeliveryService;
import org.springframework.stereotype.Service;
import com.naturemorning.parallax.repository.DeliveryRepository;
import com.naturemorning.parallax.models.Delivery;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class DeliveryService implements IDeliveryService {
    
    @Autowired
    private DeliveryRepository deliveryRepository;
    
    @Override
    public Delivery save(Delivery entity) {
        return deliveryRepository.save(entity);
    }

    @Override
    public Delivery update(Delivery entity) {
        return deliveryRepository.save(entity);
    }

    @Override
    public void delete(Delivery entity) {
        deliveryRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        deliveryRepository.deleteById(id);
    }

    @Override
    public Delivery find(Long id) {
        return deliveryRepository.findById(id).orElse(null);
    }

    @Override
    public List<Delivery> findAll() {
        return deliveryRepository.findAll();
    }

    @Override
    public void deleteInBatch(List<Delivery> users) {
        deliveryRepository.deleteInBatch(users);
    }
    
}

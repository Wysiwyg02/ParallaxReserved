/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naturemorning.parallax.services.impl;

import com.naturemorning.parallax.models.Reservation;
import com.naturemorning.parallax.services.IReservationService;
import com.naturemorning.parallax.repository.ReservationRepository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService implements IReservationService {
    
    @Autowired
    private ReservationRepository reservationRepository;
    
    @Override
    public Reservation save(Reservation entity) {
        return reservationRepository.save(entity);
    }

    @Override
    public Reservation update(Reservation entity) {
        return reservationRepository.save(entity);
    }

    @Override
    public void delete(Reservation entity) {
        reservationRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        reservationRepository.deleteById(id);
    }

    @Override
    public Reservation find(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public void deleteInBatch(List<Reservation> users) {
        reservationRepository.deleteInBatch(users);
    }
}

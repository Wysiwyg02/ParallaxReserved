/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.naturemorning.parallax.services.impl;

import com.naturemorning.parallax.models.Cashier;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.naturemorning.parallax.repository.CashierRepository;
import com.naturemorning.parallax.services.ICashierService;

@Service
public class CashierService implements ICashierService {

    @Autowired
    private CashierRepository cashierRepository;

    @Override
    public Cashier save(Cashier entity) {
        return cashierRepository.save(entity);
    }

    @Override
    public Cashier update(Cashier entity) {
        return cashierRepository.save(entity);
    }

    @Override
    public void delete(Cashier entity) {
        cashierRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        cashierRepository.deleteById(id);
    }

    @Override
    public Cashier find(Long id) {
        return cashierRepository.findById(id).orElse(null);
    }

    @Override
    public List<Cashier> findAll() {
        return cashierRepository.findAll();
    }

    @Override
    public void deleteInBatch(List<Cashier> users) {
        cashierRepository.deleteInBatch(users);
    }
}

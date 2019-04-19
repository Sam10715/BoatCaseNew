package com.example.boat.controller;

import com.example.boat.model.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class PriceService {
    @Autowired
    PriceRepository priceRepository;

    public void changeStandardRowPricePerHour(Price price) {


        if (priceRepository.findAll().isEmpty()) {

            priceRepository.save(price);
        } else {
            Price p = priceRepository.findAll().get(0);
            p.setRowStanderdPrice(price.getRowStanderdPrice());


        }

    }

    public void changeStandardElcPricePerHour(Price price) {


        if (priceRepository.findAll().isEmpty()) {

            priceRepository.save(price);
        } else {
            Price p = priceRepository.findAll().get(0);
            p.setElcStandardPrice(price.getElcStandardPrice());


        }


    }

    public void changeRowActualPrice(Price price) {


        if (priceRepository.findAll().isEmpty()) {

            priceRepository.save(price);
        } else {
            Price p = priceRepository.findAll().get(0);
            p.setRowActualPrice(price.getRowActualPrice());


        }


    }

    public void changeElcActualPrice(Price price) {


        if (priceRepository.findAll().isEmpty()) {

            priceRepository.save(price);
        } else {
            Price p = priceRepository.findAll().get(0);
            p.setElcActualPrice(price.getElcActualPrice());


        }


    }


}
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

    public void changeRowPricePerHour(Price price) {


        if (priceRepository.findAll().isEmpty()) {

            priceRepository.save(price);
        } else {
            Price p = priceRepository.findAll().get(0);
            p.setRowBoatPerHourPrice(price.getRowBoatPerHourPrice());


        }

    }

    public void changeElcPricePerHour(Price price) {


        if (priceRepository.findAll().isEmpty()) {

            priceRepository.save(price);
        } else {
            Price p = priceRepository.findAll().get(0);
            p.setElcBoatPerHourPrice(price.getElcBoatPerHourPrice());


        }


    }

    public void changeRowResPrice(Price price) {


        if (priceRepository.findAll().isEmpty()) {

            priceRepository.save(price);
        } else {
            Price p = priceRepository.findAll().get(0);
            p.setRowReservationPrice(price.getRowReservationPrice());


        }


    }

    public void changeElcResPrice(Price price) {


        if (priceRepository.findAll().isEmpty()) {

            priceRepository.save(price);
        } else {
            Price p = priceRepository.findAll().get(0);
            p.setElcReservationPrice(price.getElcReservationPrice());


        }


    }


}
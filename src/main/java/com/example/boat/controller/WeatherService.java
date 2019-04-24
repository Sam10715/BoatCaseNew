package com.example.boat.controller;

import com.example.boat.model.Price;
import com.example.boat.model.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class WeatherService {
    @Autowired
    WeatherRepository weatherRepository;
    @Autowired
    PriceRepository priceRepository;

    public void saveWeather(Weather weather) {
        if (weatherRepository.findAll().isEmpty()) {

            weatherRepository.save(weather);

        } else {
            Weather w = weatherRepository.findAll().get(0);
            w.setDescription(weather.getDescription());


        }

        changePriceBasedOnWeather();

    }
    public  void changePriceBasedOnWeather(){
        Weather w = weatherRepository.findAll().get(0);
        Price price = priceRepository.findAll().get(0);
        double rowS = price.getRowStanderdPrice();
        double elcS = price.getElcStandardPrice();

        switch (w.getDescription()) {

            case "clear sky":
                price.setRowActualPrice(rowS + rowS * 0.5);
                price.setElcActualPrice(elcS + elcS * 0.5);
                break;
            case "few clouds":
                price.setRowActualPrice(rowS + rowS * 0.25);
                price.setElcActualPrice(elcS + elcS * 0.25);
                break;
            default:
                price.setRowActualPrice(rowS);
                price.setElcActualPrice(elcS);
                break;


        }
        priceRepository.save(price);


    }

}

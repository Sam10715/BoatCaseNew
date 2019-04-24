package com.example.boat.controller;

import com.example.boat.model.Weather;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WeatherRepository extends CrudRepository<Weather,Long> {
    List<Weather> findAll();

}

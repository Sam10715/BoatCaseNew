package com.example.boat.controller;

import com.example.boat.model.Boat;
import com.example.boat.model.Price;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PriceRepository extends CrudRepository<Price,Long> {
    List<Price> findAll();
    Price findById(long id);


}


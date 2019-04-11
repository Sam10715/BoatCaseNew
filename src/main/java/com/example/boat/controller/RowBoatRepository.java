package com.example.boat.controller;

import com.example.boat.model.Boat;
import com.example.boat.model.RowBoat;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RowBoatRepository extends CrudRepository<RowBoat,Long> {
    List<RowBoat> findAll();
    RowBoat findById(long id);

}

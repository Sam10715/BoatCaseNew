package com.example.boat.controller;

import com.example.boat.model.Boat;
import com.example.boat.model.Trip;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TripRepository extends CrudRepository<Trip,Long> {
    List<Trip> findAll();
    Trip findById(long id);
}

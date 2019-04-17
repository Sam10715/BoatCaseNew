package com.example.boat.controller;

import com.example.boat.model.Boat;
import com.example.boat.model.Guest;
import com.example.boat.model.Trip;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GuestRepository extends CrudRepository<Guest,Long> {
    List<Guest> findAll();
    Guest findById(long id);

}

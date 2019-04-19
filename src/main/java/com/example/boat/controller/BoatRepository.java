package com.example.boat.controller;


import com.example.boat.model.Boat;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BoatRepository  extends CrudRepository<Boat,Long> {
    List<Boat> findAll();
    Boat findById(long id);
    void deleteByBoatNumber(int boatNumber);


}

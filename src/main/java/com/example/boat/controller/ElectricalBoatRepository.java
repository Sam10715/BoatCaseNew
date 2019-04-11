package com.example.boat.controller;
import com.example.boat.model.ElectricalBoat;

import com.example.boat.model.RowBoat;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ElectricalBoatRepository  extends CrudRepository<ElectricalBoat,Long> {
    List<ElectricalBoat> findAll();
    ElectricalBoat findById(long id);

}

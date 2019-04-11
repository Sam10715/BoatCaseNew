package com.example.boat.controller;

import com.example.boat.model.RowBoat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class RowBoatService {
    @Autowired
    RowBoatRepository rowBoatRepository;

    public void saveRowBoat(RowBoat rowBoat){
        rowBoatRepository.save(rowBoat);
    }
}

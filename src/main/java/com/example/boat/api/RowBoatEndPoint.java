package com.example.boat.api;

import com.example.boat.controller.BoatService;
import com.example.boat.controller.RowBoatService;
import com.example.boat.model.Boat;
import com.example.boat.model.RowBoat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RowBoatEndPoint {

    @Autowired
    RowBoatService rowBoatService;
    @Autowired
    BoatService boatService;
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/save-rowboat", method = RequestMethod.POST, consumes = "application/json")
    public void saveOneBoat(@RequestBody RowBoat rowBoat) {
        boatService.saveBoat(rowBoat);

    }


    }


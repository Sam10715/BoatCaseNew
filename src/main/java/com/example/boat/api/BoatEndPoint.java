package com.example.boat.api;

import com.example.boat.controller.BoatService;
import com.example.boat.model.Boat;
import com.example.boat.model.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BoatEndPoint {
    @Autowired
    BoatService boatService;

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/save-boat", method = RequestMethod.POST, consumes = "application/json")
    public void saveOneBoat(@RequestBody Boat boat) {
        boatService.saveBoat(boat);

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/get-boats-overview", method = RequestMethod.GET)
    public List<List<Integer>> getAllBoatsOerView(@RequestParam String date) {
        return boatService.allBoatsOverView(date);


    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/block-boat", method = RequestMethod.GET, consumes = "application/json")
    public List<Trip> blockOneBoat(@RequestParam int boatNumber) {
     return    boatService.blockBoat(boatNumber);

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/unblock-boat", method = RequestMethod.POST, consumes = "application/json")
    public void unBlockOneBoat(@RequestParam int boatNumber) {
        boatService.unBlockBoat(boatNumber);

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/get-inporgress-boats", method = RequestMethod.GET)
    public List<Boat> getAllInProgressBoats() {
     return   boatService.getInProgressBoats();

    }



}

package com.example.boat.api;

import com.example.boat.controller.BoatService;
import com.example.boat.controller.TripRepository;
import com.example.boat.controller.TripService;
import com.example.boat.model.Boat;
import com.example.boat.model.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TripEndPoint {


    @Autowired
    TripService tripService;

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/get-all-trips", method = RequestMethod.GET)
    public List<Trip> getAllTrips() {
        return tripService.getTrips();

    }


    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/get-ava-boats", method = RequestMethod.GET)
    public List<Boat> checkBoatsAva(String type) {
        return tripService.getAvaBoats(type);

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/start-trip", method = RequestMethod.POST, consumes = "application/json")
    public void startOneTrip(@RequestParam String type) {

        tripService.startTrip(type);

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/stop-trip", method = RequestMethod.POST, consumes = "application/json")
    public void stopOneTrip(@RequestParam int boatNumber) {
        tripService.stopTripByBoatNumber(boatNumber);

    }
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/get-trip-overview", method = RequestMethod.GET)
    public List<Integer> stopOneTrip(@RequestParam String date) {
      return   tripService.getTripOverView(date);

    }
}

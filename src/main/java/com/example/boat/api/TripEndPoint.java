package com.example.boat.api;

import com.example.boat.controller.BoatService;
import com.example.boat.controller.TripRepository;
import com.example.boat.controller.TripService;
import com.example.boat.model.Boat;
import com.example.boat.model.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    @RequestMapping(value = "/get-ava-boats/{type}/{numberOfPersons}", method = RequestMethod.GET)
    public List<Boat> checkBoatsAva(@PathVariable String type,@PathVariable int numberOfPersons) {

        return tripService.getAvaBoats(type,numberOfPersons);

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/start-trip", method = RequestMethod.POST, consumes = "application/json")
    public void startOneTrip(@RequestBody Trip trip) {

        tripService.startTrip(trip);

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/stop-trip/{boatNumber}", method = RequestMethod.GET)
    public Trip stopOneTrip(@PathVariable int boatNumber) {

        return tripService.stopTripByBoatNumber(boatNumber);


    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/get-trip-overview", method = RequestMethod.GET)
    public List<Integer> getTripOverViews(@RequestParam String date) {
        return tripService.getTripOverView(date);

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/make-reservation", method = RequestMethod.POST, consumes = "application/json")
    public void doReserve(@RequestBody Trip trip) {
        tripService.makeReservation(trip);

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/get-avaBoats-reservation", method = RequestMethod.GET)
    public List<Boat> getAvaBoatsReservations(@RequestParam String start, @RequestParam String end, @RequestParam String type,@RequestParam int numberOfPerson) {
        LocalDateTime start1 = LocalDateTime.parse(start);
        LocalDateTime end1 = LocalDateTime.parse(end);
        return tripService.getAvailableBoatsReservation(start1,end1,type,numberOfPerson);

    }


}

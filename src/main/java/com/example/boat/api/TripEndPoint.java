package com.example.boat.api;

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
    @RequestMapping(value = "/get-trip-overview/{date}", method = RequestMethod.GET)
    public List<Number> getTripOverViews(@PathVariable String date) {
        return tripService.getTripOverView(date);

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/make-reservation", method = RequestMethod.POST, consumes = "application/json")
    public Trip doReserve(@RequestBody Trip trip) {
       return tripService.makeReservation(trip);

    }

        @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/get-Reserved-trips", method = RequestMethod.GET)
    public List<Trip> getReservedTrips() {
      return tripService.getReservedTripForToday();

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/get-allReserved-trips", method = RequestMethod.GET)
    public List<Trip> getAllReservedTrips() {
        return tripService.getAllReservedTrips();

    }
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/delete-reservation/{id}", method = RequestMethod.GET)
    public void deleteReservation(@PathVariable long id) {
         tripService.deleteReservation(id);

    }


    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/get-un-reserved-trips", method = RequestMethod.GET)
    public List<Trip> getUnReservedTrips() {
        return tripService.getAllUnReservedTrips();

    }






}

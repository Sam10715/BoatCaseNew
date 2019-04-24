package com.example.boat.api;

import com.example.boat.controller.BoatService;
import com.example.boat.model.Boat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    @RequestMapping(value = "/get-boats-overview/{date}", method = RequestMethod.GET)
    public List<List<Number>> getAllBoatsOerView(@PathVariable String date) {
        return boatService.allBoatsOverView(date);


    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/block-boat/{boatNumber}", method = RequestMethod.GET)
    public void blockOneBoat(@PathVariable int boatNumber) {
         boatService.blockBoat(boatNumber);

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/unblock-boat/{boatNumber}", method = RequestMethod.GET)
    public void unBlockOneBoat(@PathVariable int boatNumber) {
        boatService.unBlockBoat(boatNumber);

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/get-inporgress-boats", method = RequestMethod.GET)
    public List<Boat> getAllInProgressBoats() {
     return   boatService.getInProgressBoats();

    }
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/get-ava-boats/{type}/{numberOfPersons}", method = RequestMethod.GET)
    public List<Boat> checkBoatsAva(@PathVariable String type,@PathVariable int numberOfPersons) {

        return boatService.getAvaBoats(type,numberOfPersons);

    }
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/get-avaBoats-reservation/{start}/{end}/{type}/{numberOfPerson}", method = RequestMethod.GET)
    public List<Boat> getAvaBoatsReservations(@PathVariable String start, @PathVariable String end, @PathVariable String type,@PathVariable int numberOfPerson) {
System.out.println(start);

        LocalDateTime start1 = LocalDateTime.parse(start);
        LocalDateTime end1 = LocalDateTime.parse(end);
//        LocalDateTime start2 =start1.plusHours(2);
//        LocalDateTime end2 =end1.plusHours(2);
        return boatService.getAvailableBoatsReservation(start1,end1,type,numberOfPerson);

    }
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/get-unblocked-boats", method = RequestMethod.GET)
    public List<Boat> getUnblockedBoats() {

        return boatService.getUnBlockedBoats();

    }
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/get-blocked-boats", method = RequestMethod.GET)
    public List<Boat> getBlockedBoats() {

        return boatService.getBlockedBoats();

    }
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/disable-boat/{boatNumber}", method = RequestMethod.GET)
    public void deleteBoat(@PathVariable int  boatNumber) {

          boatService.disableBoat(boatNumber);

    }
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/get-all-working-Boats", method = RequestMethod.GET)
    public List<Boat> getAllWorkingBoats() {

       return boatService.getAllWorkingBoat();

    }
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/get-all-Boats", method = RequestMethod.GET)
    public List<Boat> getAllBoats() {

        return boatService.getAllBoats();

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/get-disabled-Boats", method = RequestMethod.GET)
    public List<Boat> getDisableBoats() {

        return boatService.getDisabledBoats();

    }


    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/delete-boat/{boatNumber}", method = RequestMethod.DELETE)
    public void getDisableBoats(@PathVariable int boatNumber) {

         boatService.deleteBoat(boatNumber);

    }





}

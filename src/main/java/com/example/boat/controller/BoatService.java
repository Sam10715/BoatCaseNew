package com.example.boat.controller;

import com.example.boat.model.Boat;
import com.example.boat.model.ElectricalBoat;
import com.example.boat.model.RowBoat;
import com.example.boat.model.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class BoatService {
    @Autowired
    BoatRepository boatRepository;
    @Autowired
    TripService tripService;
    @Autowired
    TripRepository tripRepository;

    public void saveBoat(@RequestBody Boat boat) {

        boatRepository.save(boat);

    }


    public List<Integer> oneBoatOverView(String date, Boat boat) {
        int inProgressCounter = 0;
        int endedCounter = 0;
        int incomeCounter = 0;
        int avreageDurationCounter = 0;
        int type = 0;

        List<Integer> counters = new ArrayList<>();
        List<Trip> a = tripService.getEndedAndInProgressTripsByDate(date);
        List<Trip> c = new ArrayList<>();

        for (Trip t : a) {
            if (t.getBoat().getBoatNumber() == boat.getBoatNumber()) {

                c.add(t);

            }

        }
        for (Trip k : c) {
            if (k.getTripStatus().equals("In progress")) {
                inProgressCounter = inProgressCounter + 1;
            } else {
                Duration d = Duration.between(k.getStartDate(), k.getEndDate());
                endedCounter = endedCounter + 1;
                avreageDurationCounter = (int) (d.getSeconds() + avreageDurationCounter);

            }

            incomeCounter = (int) (k.getPrice() + incomeCounter);

        }
        avreageDurationCounter = avreageDurationCounter / 3600;

        if (boat instanceof ElectricalBoat) {
            type = 1;
        } else if (boat instanceof RowBoat) {
            type = 2;
        }


        counters.add(boat.getBoatNumber());
        counters.add(inProgressCounter);
        counters.add(endedCounter);
        counters.add(incomeCounter);
        counters.add(avreageDurationCounter);
        counters.add(type);


        return counters;

    }

    public List<List<Integer>> allBoatsOverView(String date) {
        List<Boat> boats = boatRepository.findAll();
        List<List<Integer>> counters = new ArrayList<>();
        List<Integer> counter1 = new ArrayList<>();
        int totalTime = 0;
        int totalIncome = 0;

        for (Boat b : boats) {

            List<Integer> a = oneBoatOverView(date, b);
            totalIncome = a.get(3) + totalIncome;
            totalTime = a.get(4) + totalTime;
            counters.add(a);
        }
        counter1.add(totalIncome);
        counter1.add(totalTime);

        counters.add(counter1);
        return counters;
    }

    public List<Trip> blockBoat(int boatNumber) {
        List<Trip> trips = new ArrayList<>();
        for (Boat b : boatRepository.findAll()) {

            if (b.getBoatNumber() == boatNumber) {
                b.setBoatMaintenanceStatus(true);
                boatRepository.save(b);
                break;
            }
        }

        for (Trip trip : tripRepository.findAll()) {
            if (trip.getTripStatus().equals("Reserved")) {
                if (trip.getBoat().getBoatNumber() == boatNumber) {
                    trip.setTripStatus("Un Reserved");
                    trip.setBoat(null);

                    tripService.makeReservation(trip);


                }

            }


        }
        for (Trip trip : tripRepository.findAll()) {
            if (trip.getTripStatus().equals("Un Reserved")) {
                trips.add(trip);
            }
        }

        return trips;
    }

    public void unBlockBoat(int boatNumber) {
        for (Boat b : boatRepository.findAll()) {

            if (b.getBoatNumber() == boatNumber) {
                b.setBoatMaintenanceStatus(false);

                boatRepository.save(b);
                break;
            }
        }


    }

    public  List<Boat> getInProgressBoats(){
        List<Boat> boats= new ArrayList<>();
        List<Trip> trips = tripService.getInProgressTrips();
        for(Trip t:trips){
            boats.add(t.getBoat());
        }
        return boats;
    }



}

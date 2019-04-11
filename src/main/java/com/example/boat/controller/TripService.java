package com.example.boat.controller;

import com.example.boat.model.Boat;
import com.example.boat.model.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
@Transactional
public class TripService {
    @Autowired
    TripRepository tripRepository;
    @Autowired
    BoatRepository boatRepository;

    public List<Trip> getInProgressTrips() {
        List<Trip> trips = new ArrayList<>();
        for (Trip t : tripRepository.findAll()) {

            if (t.getTripStatus().equals("In progress")) {

                trips.add(t);
            }
        }
        return trips;

    }

    public List<Trip> getActiveTripsByDate(String date) {
        List<Trip> trips = new ArrayList<>();
        List<Trip> trips1 = tripRepository.findAll();
        LocalDateTime startDate = LocalDateTime.parse(date);

        for (Trip t : trips1) {
            if ((t.getStartDate().getDayOfYear() == startDate.getDayOfYear()) & (startDate.getYear() == t.getStartDate().getYear())) {
                if (t.getTripStatus().equals("not Active")) {
                    continue;
                }
                trips.add(t);
            }
        }

        return trips;


    }


    public List<Boat> getAvaBoats() {
        List<Trip> trips = new ArrayList<>();
        List<Boat> boats = new ArrayList<>();
        List<Boat> boats1 = boatRepository.findAll();
        for (Trip t : tripRepository.findAll()) {

            if (t.getTripStatus().equals("In progress")) {

                trips.add(t);
            }
        }
        for (Trip t1 : trips) {
            boats.add(t1.getBoat());
        }
        for (Boat b1 : boats) {

            for (Boat b : boatRepository.findAll()) {
                if (b1.getBootNumber() == b.getBootNumber()) {
                    boats1.remove(b);
                }
            }
        }
        boats1.sort(Comparator.comparing(Boat::getCounter));
        return boats1;

    }


    public void startTrip(Trip trip) {

        List<Boat> boats = getAvaBoats();
        trip.setBoat(boats.get(0));
        long counter = boats.get(0).getCounter();
        boats.get(0).setCounter(counter + 1);

        trip.setTripStatus("In progress");
        trip.setStartDate(LocalDateTime.now());
        tripRepository.save(trip);
    }

    public void stopTripByBoatNumber(int boatNumber) {
        List<Trip> trips = getInProgressTrips();
        Trip trip = new Trip();
        for (Trip t : trips) {

            if (t.getBoat().getBootNumber() == boatNumber) {

                trip = t;

            }
        }
        stopTrip(trip);

    }

    public void stopTrip(Trip trip1) {
        Trip trip = tripRepository.findById(trip1.getId());

        trip.setTripStatus("Ended");
        LocalDateTime a = trip.getStartDate();
        LocalDateTime b = LocalDateTime.now();
        trip.setEndDate(b);
        Duration duration = Duration.between(a, b);
        double x = duration.getSeconds();

        double hours = (x) / 3600;
        trip.setPrice(trip.getBoat().getPricePerHour() * hours);
        tripRepository.save(trip);

    }

    public List<Integer> getTripOverView(String date) {

        List<Integer> counters = new ArrayList<>();
        List<Integer> boatNumbers = new ArrayList<>();
        List<Trip> trips1 = getActiveTripsByDate(date);

        int inProgressCounter = 0;
        int endedCounter = 0;
        int incomeCounter = 0;
        int avreageDurationCounter = 0;
        for (Trip t : trips1) {


            if (t.getTripStatus().equals("In progress")) {
                inProgressCounter = inProgressCounter + 1;
            }
            if
            (t.getTripStatus().equals("Ended")) {
                System.out.println(t.getStartDate());
                System.out.println(t.getEndDate());
                Duration d = Duration.between(t.getStartDate(), t.getEndDate());
                endedCounter = endedCounter + 1;
                avreageDurationCounter = (int) (d.getSeconds() + avreageDurationCounter);

            }
            if (!boatNumbers.contains(t.getBoat().getBootNumber())) {
                boatNumbers.add(t.getBoat().getBootNumber());
            }
            incomeCounter = (int) (t.getPrice() + incomeCounter);

        }
        avreageDurationCounter = avreageDurationCounter / 3600;
        if (endedCounter != 0) {
            avreageDurationCounter = avreageDurationCounter / endedCounter;
        }

        counters.add(inProgressCounter);
        counters.add(endedCounter);
        counters.add(incomeCounter);
        counters.add(avreageDurationCounter);
        counters.addAll(boatNumbers);
        return counters;
    }

    public List<Trip> getTrips() {
        return tripRepository.findAll();
    }
}

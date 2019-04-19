package com.example.boat.controller;

import com.example.boat.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
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
    BoatService boatService;
    @Autowired
    PriceRepository priceRepository;
    @Autowired
    GuestRepository guestRepository;
    @Autowired
    GuestService guestService;

    //Trip Functions
    //*******************************************************************


    public List<Trip> getInProgressTrips() {
        List<Trip> trips = new ArrayList<>();
        for (Trip t : tripRepository.findAll()) {

            if (t.getTripStatus().equals("In progress")) {

                trips.add(t);
            }
        }
        return trips;

    }

    public void startTrip(Trip trip) {

        if (trip.getId() != 0) {
            Trip t = tripRepository.findById(trip.getId());
            t.setTripStatus("In progress");
            tripRepository.save(t);

        } else {

            String type = trip.getBoatType();
            List<Boat> boats = boatService.getAvaBoats(type, trip.getNumberOfPerson());


            trip.setBoat(boats.get(0));
            long counter = boats.get(0).getCounter();
            boats.get(0).setCounter(counter + 1);
            trip.setTripStatus("In progress");
            trip.setStartDate(LocalDateTime.now());
            Guest g = trip.getGuest();
            guestRepository.save(g);
            tripRepository.save(trip);


        }
    }

    public Trip stopTripByBoatNumber(int boatNumber) {
        List<Trip> trips = getInProgressTrips();
        Trip trip = new Trip();
        for (Trip t : trips) {

            if (t.getBoat().getBoatNumber() == boatNumber) {

                trip = t;

            }
        }
        stopTrip(trip);
        return trip;

    }

    public void stopTrip(Trip trip1) {
        Trip trip = tripRepository.findById(trip1.getId());

        if (trip.isReservationStatus()) {

            trip.setTripStatus("Cleaning");
            double price = trip.getPrice();

            //trip.setEndDate(LocalDateTime.now());
            double discount = guestService.discountCal(trip.getGuest());


            trip.setPrice(price - price * discount);
            tripRepository.save(trip);


        } else {
            LocalDateTime a = trip.getStartDate();
            LocalDateTime b = LocalDateTime.now();
            trip.setEndDate(b);
            Duration duration = Duration.between(a, b);
            double x = duration.getSeconds();
            Guest g = trip.getGuest();
            double discount = guestService.discountCal(g);

            double hours = (x) / 3600;

            if (hours < 1) {
                hours = 1;

            }


            if (trip.getBoatType().equals("Electrical")) {
                double price = priceRepository.findAll().get(0).getElcStandardPrice() * hours;
                double price1 = priceRepository.findAll().get(0).getElcActualPrice() * hours;
                if (price1 > price) {
                    trip.setPrice(price1 - price1 * discount);
                } else {
                    trip.setPrice(price - price * discount);
                }
            }


        if (trip.getBoatType().equals("Row")) {
            double price = priceRepository.findAll().get(0).getRowStanderdPrice() * hours;
            double price1 = priceRepository.findAll().get(0).getRowActualPrice() * hours;
            if (price1 > price) {
                trip.setPrice(price1 - price1 * discount);
            } else {
                trip.setPrice(price - price * discount);
            }

            System.out.println("Price is : " + price);
            System.out.println("Discount is : " + discount);
        }


        if (trip.getBoat() instanceof ElectricalBoat) {
            trip.setTripStatus("Charging");
        } else {
            trip.setTripStatus("Ended");
        }

        tripRepository.save(trip);

    }

}
    //********************************************************************


    // Trip OverView Functions
    // ********************************************************************
    public List<Trip> getEndedAndInProgressTripsByDate(String date) {
        List<Trip> trips = new ArrayList<>();
        List<Trip> trips1 = tripRepository.findAll();
        LocalDateTime startDate = LocalDateTime.parse(date);

        for (Trip t : trips1) {
            if (t.getStartDate().toLocalDate().equals(startDate.toLocalDate())) {
                if (t.getTripStatus().equals("In progress") | (t.getTripStatus().equals("Ended"))) {
                    trips.add(t);

                } else {
                    continue;
                }
            }
        }

        return trips;
    }


    public List<Number> getTripOverView(String date) {

        List<Number> counters = new ArrayList<>();
        List<Number> boatNumbers = new ArrayList<>();
        List<Trip> trips1 = getEndedAndInProgressTripsByDate(date);

        int inProgressCounter = 0;
        int endedCounter = 0;
        double incomeCounter = 0;
        double avreageDurationCounter = 0;
        for (Trip t : trips1) {


            if (t.getTripStatus().equals("In progress")) {
                inProgressCounter = inProgressCounter + 1;
            }
            if
            (t.getTripStatus().equals("Ended")) {

                Duration d = Duration.between(t.getStartDate(), t.getEndDate());
                endedCounter = endedCounter + 1;
                avreageDurationCounter =  (d.getSeconds() + avreageDurationCounter);

            }
            if (!boatNumbers.contains(t.getBoat().getBoatNumber())) {
                boatNumbers.add(t.getBoat().getBoatNumber());
            }
            incomeCounter =  (t.getPrice() + incomeCounter);

        }
        avreageDurationCounter = avreageDurationCounter / 3600;
        if (endedCounter != 0) {
            avreageDurationCounter = avreageDurationCounter / endedCounter;
        }
        avreageDurationCounter = avreageDurationCounter*100;
        avreageDurationCounter = Math.round(avreageDurationCounter);
        avreageDurationCounter = avreageDurationCounter /100;

        incomeCounter = incomeCounter*100;
        incomeCounter = Math.round(incomeCounter);
        incomeCounter = incomeCounter /100;


        counters.add(inProgressCounter);
        counters.add(endedCounter);
        counters.add(incomeCounter);
        counters.add(avreageDurationCounter);
        counters.addAll(boatNumbers);
        return counters;
    }


    //********************************************************************


    //CRUD Function and shared function


    public List<Trip> getTrips() {
        return tripRepository.findAll();
    }

    public void changeTripStatus(Trip trip) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime a = trip.getEndDate();
        if (!trip.isReservationStatus()) {
            if (trip.getTripStatus().equals("Charging")) {
               // LocalDateTime now = LocalDateTime.now();
                ElectricalBoat electricalBoat = (ElectricalBoat) trip.getBoat();

                //LocalDateTime a = trip.getEndDate();
                a = a.plusMinutes(electricalBoat.getChargeTime());
                if (now.isAfter(a)) {
                    trip.setTripStatus("Ended");


                }


            }
        } else {

            if (trip.getTripStatus().equals("Cleaning")) {

             //   LocalDateTime now = LocalDateTime.now();
               // LocalDateTime a = trip.getEndDate();
                a = a.plusMinutes(2);

                if (now.isAfter(a)) {

                    if (trip.getBoatType().equals("Row")) {
                        trip.setTripStatus("Ended");
                    } else if (trip.getBoatType().equals("Electrical")) {
                        trip.setTripStatus("Charging");
                        if (now.isAfter(a)) {
                            trip.setTripStatus("Ended");


                        }

                    }

                }

            } else if (trip.getTripStatus().equals("Charging")) {
              //  LocalDateTime now = LocalDateTime.now();
                ElectricalBoat electricalBoat = (ElectricalBoat) trip.getBoat();

                //LocalDateTime a = trip.getEndDate();
                a = a.plusMinutes(electricalBoat.getChargeTime());
                if (now.isAfter(a)) {
                    trip.setTripStatus("Ended");


                }


            }


        }


    }

    //********************************************************************


    //Reservation function
    //********************************************************************

    public List<Trip> getInProgressAndChargingTripsByDate(String date) {
        List<Trip> trips = new ArrayList<>();
        List<Trip> trips1 = tripRepository.findAll();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = LocalDateTime.parse(date);
        // if ((startDate.getDayOfYear() > now.getDayOfYear()) & (startDate.getYear() == now.getYear()))
        if ((startDate.toLocalDate().isAfter(now.toLocalDate()))) {


            return trips;
        }
        for (Trip t : trips1) {
            if (t.getStartDate().toLocalDate().equals(startDate.toLocalDate())) {
                if (t.getTripStatus().equals("Cleaning") | t.getTripStatus().equals("In progress") | t.getTripStatus().equals("Charging")) {
                    trips.add(t);

                } else {
                    continue;
                }
            }
        }

        return trips;


    }



    public List<Trip> getReservedTripByType(String type) {
        List<Trip> trips = tripRepository.findAll();
        List<Trip> trips1 = new ArrayList<>();
        for (Trip t : trips) {

            if (t.getTripStatus().equals("Reserved")) {
                if (t.getBoatType().equals(type)) {

                    trips1.add(t);

                }


            }

        }
        return trips1;
    }


    public void reserveTrip() {


    }

    public boolean checkDateTimeReservation(LocalDateTime startTime, LocalDateTime endTime, Boat boat, String type) {
        List<Trip> currentTrip = getReservedTripByType(type);
        long boatId = boat.getId();
        startTime = startTime.minusHours(1);


        for (Trip current : currentTrip) {
            if (current.getBoat().getId() != boatId) {

                continue;
            }

            if (startTime.isBefore(current.getEndDate()) && current.getStartDate().isBefore(endTime)) {

                return false;
            }
        }
        return true;
    }



    public Trip makeReservation(Trip trip) {
        long counter = 0;
        LocalDateTime startDate = trip.getStartDate();
        LocalDateTime endTime = trip.getEndDate();
        String type = trip.getBoatType();
        List<Boat> boats = boatService.getAvailableBoatsReservation(startDate, endTime, type, trip.getNumberOfPerson());

        if(boats.size()>0) {



            trip.setTripStatus("Reserved");
            trip.setBoat(boats.get(0));
            counter = boats.get(0).getCounter();
            boats.get(0).setCounter(counter + 1);
            trip.setReservationStatus(true);
            Guest g = trip.getGuest();
            guestRepository.save(g);
            Duration duration = Duration.between(startDate, endTime);
            double x = duration.getSeconds();

            double hours = (x) / 3600;

            if (hours < 1) {
                hours = 1;
            }



                if (trip.getBoatType().equals("Electrical")) {
                    double price = priceRepository.findAll().get(0).getElcStandardPrice() * hours;
                    trip.setPrice(price);
                }
                if (trip.getBoatType().equals("Row")) {
                    double price = priceRepository.findAll().get(0).getRowStanderdPrice() * hours;
                    trip.setPrice(price);
                }



            tripRepository.save(trip);
            return trip;
        }
      return null;
    }

    public List<Trip> getReservedTripForToday(){
        List<Trip> trips = tripRepository.findAll();
        trips.removeIf((t1 -> (!t1.getTripStatus().equals("Reserved"))));
        trips.removeIf((t1)->(!t1.getStartDate().toLocalDate().equals(LocalDate.now())));

        return trips;
    }

    public  List<Trip> getAllReservedTrips(){
        List<Trip> trips = tripRepository.findAll();
        trips.removeIf((t1 -> (!t1.getTripStatus().equals("Reserved"))));
        return trips;
    }
    public void deleteReservation(long id){
        Trip t=tripRepository.findById(id);
        Boat b = t.getBoat();
        b.setCounter(b.getCounter()-1);
        tripRepository.deleteById(id);

    }
    public  List<Trip> getAllUnReservedTrips(){
        List<Trip> trips = tripRepository.findAll();
        trips.removeIf((t1 -> (!t1.getTripStatus().equals("Un Reserved"))));
        return trips;
    }


}

//********************************************************************




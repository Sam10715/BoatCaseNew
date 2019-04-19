package com.example.boat.controller;

import com.example.boat.model.Boat;
import com.example.boat.model.ElectricalBoat;
import com.example.boat.model.RowBoat;
import com.example.boat.model.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
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

    public boolean saveBoat(@RequestBody Boat boat) {

        for (Boat b : boatRepository.findAll()) {
            if (b.getBoatNumber() == boat.getBoatNumber()) {
                return false;
            }
        }
        boatRepository.save(boat);
        return true;
    }


    public List<Number> oneBoatOverView(String date, Boat boat) {
        int inProgressCounter = 0;
        int endedCounter = 0;
        double incomeCounter = 0;
        double avreageDurationCounter = 0;
        int type = 0;

        List<Number> counters = new ArrayList<>();
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
            } else if (k.getTripStatus().equals("Ended")) {
                Duration d = Duration.between(k.getStartDate(), k.getEndDate());

                endedCounter = endedCounter + 1;
                avreageDurationCounter = (d.getSeconds() + avreageDurationCounter);

            }

            incomeCounter =  (k.getPrice() + incomeCounter);

        }

        avreageDurationCounter = avreageDurationCounter / 3600;


        if (boat instanceof ElectricalBoat) {
            type = -1;
        } else if (boat instanceof RowBoat) {
            type = -2;
        }

        incomeCounter = incomeCounter*100;
        incomeCounter = Math.round(incomeCounter);
        incomeCounter = incomeCounter /100;

        avreageDurationCounter = avreageDurationCounter*100;
        avreageDurationCounter = Math.round(avreageDurationCounter);
        avreageDurationCounter = avreageDurationCounter /100;



        counters.add(boat.getBoatNumber());
        counters.add(inProgressCounter);
        counters.add(endedCounter);
        counters.add(incomeCounter);
        counters.add(avreageDurationCounter);
        counters.add(type);


        return counters;
    }

    public List<List<Number>> allBoatsOverView(String date) {



        List<Boat> boats = boatRepository.findAll();
        List<List<Number>> counters = new ArrayList<>();
        List<Number> counter1 = new ArrayList<>();
        double totalTime = 0;
        double totalIncome = 0;

        for (Boat b : boats) {

            List<Number> a = oneBoatOverView(date, b);
            totalIncome = (double)a.get(3) + totalIncome;
            totalTime = (double)a.get(4) + totalTime;
            counters.add(a);
        }
        totalIncome = totalIncome*100;
        totalIncome = Math.round(totalIncome);
        totalIncome = totalIncome /100;

        totalTime = totalTime*100;
        totalTime = Math.round(totalTime);
        totalTime = totalTime /100;

        counter1.add(totalIncome);
        counter1.add(totalTime);


        counters.add(counter1);


        return counters;
    }

    public void blockBoat(int boatNumber) {
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
                    Boat b =trip.getBoat();
                    b.setCounter(b.getCounter() -1);
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


    }

    public void unBlockBoat(int boatNumber) {
        for (Boat b : boatRepository.findAll()) {

            if (b.getBoatNumber() == boatNumber) {
                b.setBoatMaintenanceStatus(false);

                boatRepository.save(b);
                break;
            }
        }
        List<Trip> trips1= tripService.getAllUnReservedTrips();
        for(Trip trip:trips1){
            tripService.makeReservation(trip);
        }

    }

    public List<Boat> getInProgressBoats() {
        List<Boat> boats = new ArrayList<>();
        List<Trip> trips = tripService.getInProgressTrips();
        for (Trip t : trips) {
            boats.add(t.getBoat());
        }
        return boats;
    }

    public List<Boat> getAvaBoats(String type, int numberOfPoeple) {

        List<Trip> trips = new ArrayList<>();
        List<Boat> boats = new ArrayList<>();
        List<Boat> boats1 = boatRepository.findAll();

        for (Trip t : tripRepository.findAll()) {
            tripService.changeTripStatus(t);
            if (t.getTripStatus().equals("Cleaning") | t.getTripStatus().equals("In progress") | t.getTripStatus().equals("Charging") | t.getTripStatus().equals("Reserved")) {

                trips.add(t);
                if (t.getTripStatus().equals("Reserved")) {
                    if (t.getStartDate().toLocalDate().isAfter(LocalDate.now())) {
                        trips.remove(t);


                    }

                }

            }
        }

        for (Trip t1 : trips) {
            boats.add(t1.getBoat());
        }
        for (Boat b1 : boats) {

            for (Boat b : boatRepository.findAll()) {
                if (b1.getBoatNumber() == b.getBoatNumber()) {
                    boats1.remove(b);
                }
            }
        }


        if (type.equals("Electrical")) {

            boats1.removeIf((b1 -> (b1 instanceof RowBoat)));
            boats1.removeIf((b1 -> (b1.isBoatMaintenanceStatus())));
            boats1.removeIf((b1 -> (b1.getNumberOfSeats()) < numberOfPoeple));

        }
        if (type.equals("Row")) {


            boats1.removeIf((b1 -> (b1 instanceof ElectricalBoat)));
            boats1.removeIf((b1 -> (b1.isBoatMaintenanceStatus())));
            boats1.removeIf((b1 -> (b1.getNumberOfSeats()) < numberOfPoeple));

        }
        if (boats1.isEmpty()) {
            boats1 = getAvaReservedBoatForToday(type);
            boats1.removeIf((b1 -> (b1.isBoatMaintenanceStatus())));
            boats1.removeIf((b1 -> (b1.getNumberOfSeats()) < numberOfPoeple));

            return boats1;


        }

        boats1.sort(Comparator.comparing(Boat::getCounter));

        return boats1;

    }

    public List<Boat> getAvaReservedBoatForToday(String type) {
        List<Trip> trips = tripRepository.findAll();
        List<Trip> trips1 = new ArrayList<>();
        List<Boat> boats = new ArrayList<>();
        LocalDateTime a = LocalDateTime.now();
        for (Trip t : trips) {

            if (t.getTripStatus().equals("Reserved")) {
                if (t.getBoatType().equals(type)) {
                    if (t.getStartDate().toLocalDate().equals(LocalDateTime.now().toLocalDate())) {
                        trips1.add(t);


                    }

                }
            }

        }
        trips1.sort(Comparator.comparing(Trip::getStartDate).reversed());
        for (Trip t1 : trips1) {
            if (t1.getBoatType().equals("Electrical")) {
                ElectricalBoat b = (ElectricalBoat) (t1.getBoat());
                a = a.plusMinutes(b.getChargeTime());
                a = a.plusHours(1);

                if (t1.getStartDate().isAfter(a)) {
                    boats.add(t1.getBoat());
                }
            } else if (t1.getBoatType().equals("Row")) {
                a = a.plusHours(1);
                if (t1.getStartDate().isAfter(a)) {
                    boats.add(t1.getBoat());
                }

            }

        }
        return boats;
    }

    public List<Boat> getAvailableBoatsReservation(LocalDateTime startTime, LocalDateTime endTime, String type, int numberOfPoeple) {
        String date = startTime.toString();
        List<Boat> boats = getAvaBoatsByDateAndType(date, type, numberOfPoeple);

        List<Boat> availableBoats = new ArrayList<>();
        for (Boat boat : boats) {
            if (tripService.checkDateTimeReservation(startTime, endTime, boat, type)) {
                availableBoats.add(boat);
                availableBoats.sort(Comparator.comparing(Boat::getCounter));

            }

        }

        return availableBoats;


    }

    public List<Boat> getAvaBoatsByDateAndType(String date, String type, int numberOfPoeple) {
        List<Trip> trips = tripService.getInProgressAndChargingTripsByDate(date);
        List<Boat> boats = new ArrayList<>();
        List<Boat> boats1 = boatRepository.findAll();
        for (Trip t : tripRepository.findAll()) {
            tripService.changeTripStatus(t);
        }
        for (Trip t1 : trips) {
            boats.add(t1.getBoat());
        }
        for (Boat b1 : boats) {
            for (Boat b : boatRepository.findAll()) {
                if (b1.getBoatNumber() == b.getBoatNumber()) {
                    boats1.remove(b);
                }
            }
        }
        if (type.equals("Electrical")) {
            boats1.removeIf((b1 -> (b1 instanceof RowBoat)));
            boats1.removeIf((b1 -> (b1.isBoatMaintenanceStatus())));
            boats1.removeIf((b1 -> (b1.getNumberOfSeats()) < numberOfPoeple));
        }
        if (type.equals("Row")) {
            boats1.removeIf((b1 -> (b1 instanceof ElectricalBoat)));
            boats1.removeIf((b1 -> (b1.isBoatMaintenanceStatus())));
            boats1.removeIf((b1 -> (b1.getNumberOfSeats()) < numberOfPoeple));
        }

        boats1.sort(Comparator.comparing(Boat::getCounter));
        return boats1;

    }

   public List<Boat> getUnBlockedBoats(){

        List<Boat> boats = boatRepository.findAll();
         boats.removeIf((b)->(!b.isBoatMaintenanceStatus()));
         return boats;

   }
    public List<Boat> getBlockedBoats(){

        List<Boat> boats = boatRepository.findAll();
        boats.removeIf((b)->(b.isBoatMaintenanceStatus()));
        return boats;

    }
    public void deleteBoat(int boatNumber){




      boatRepository.deleteByBoatNumber(boatNumber);


    }
    public List<Boat> getAllBoat(){

     return    boatRepository.findAll();

    }
}

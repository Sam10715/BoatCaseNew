package com.example.boat.controller;

import com.example.boat.model.Boat;
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

    public void saveBoat(@RequestBody Boat boat) {

        boatRepository.save(boat);

    }


    public List<Integer> oneBoatOverView(String date, Boat boat) {
        int inProgressCounter = 0;
        int endedCounter = 0;
        int incomeCounter = 0;
        int avreageDurationCounter = 0;

        List<Integer> counters = new ArrayList<>();
        List<Trip> a = tripService.getActiveTripsByDate(date);
        List<Trip> c = new ArrayList<>();

        for (Trip t : a) {
            if (t.getBoat().getBootNumber() == boat.getBootNumber()) {

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


        counters.add(boat.getBootNumber());
        counters.add(inProgressCounter);
        counters.add(endedCounter);
        counters.add(incomeCounter);
        counters.add(avreageDurationCounter);


        return counters;

    }

    public  List<List<Integer>> allBoatsOverView(String date) {
       List<Boat> boats =boatRepository.findAll();
        List<List<Integer>> counters = new ArrayList<>();
        List<Integer> counter1 = new ArrayList<>();
        int totalTime = 0;
        int totalIncome = 0;

        for (Boat b : boats) {

            List<Integer> a = oneBoatOverView( date, b);
            totalIncome = a.get(3) + totalIncome;
            totalTime = a.get(4) + totalTime;
            counters.add(a);
        }
        counter1.add(totalIncome);
        counter1.add(totalTime);

        counters.add(counter1);
        return counters;
    }

}

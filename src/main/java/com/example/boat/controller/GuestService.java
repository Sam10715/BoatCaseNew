package com.example.boat.controller;

import com.example.boat.model.Guest;
import com.example.boat.model.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Transactional

public class GuestService {

    @Autowired
    GuestRepository guestRepository;
    @Autowired
    TripRepository tripRepository;


    public double discountCal(Guest g){
        long counter1=1;
        double discount=0;
        List<Guest> guests= new ArrayList<>();
        for(Trip t:tripRepository.findAll()){
            if(t.getTripStatus().equals("Ended"))
            {
                guests.add(t.getGuest());
            }

        }



        for (Guest guest : guests) {
            if (guest.getIdNumber().equals(g.getIdNumber())) {
                counter1 = 1 + counter1;
            }

        }
        if(counter1>1){
            if(counter1==2){
                discount= 5.0/100.0;
            }
            else if(counter1==3){
                discount=10.0/100.0;
            }
            else if(counter1==4){
                discount=15.0/100.0;
            }
            else if(counter1==5){
                discount=20.0/100.0;
            }
            else if(counter1>5){
                discount=25.0/100.0;

            }
        }

return discount;
    }


}

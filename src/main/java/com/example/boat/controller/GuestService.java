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

        double discount=0;
        List<Guest> guests= guestRepository.findAll();
       if(guests.contains(g)){
           g.setCounter(g.getCounter()+1);
       }
       long counter1= g.getCounter();

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

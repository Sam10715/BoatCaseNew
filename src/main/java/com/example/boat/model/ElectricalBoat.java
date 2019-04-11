package com.example.boat.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "Electricalboat")
public class ElectricalBoat extends Boat {
    @Column(name = "chargetime")
    private  int chargeTime;

    public int getChargeTime() {
        return chargeTime;
    }

    public void setChargeTime(int chargeTime) {
        this.chargeTime = chargeTime;
    }
}

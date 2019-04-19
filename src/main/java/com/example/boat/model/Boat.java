package com.example.boat.model;

import javax.persistence.*;

@Entity
@Table(name = "Boat")
@Inheritance(
        strategy = InheritanceType.JOINED
)
public class Boat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Access(AccessType.PROPERTY)
    private long id;
    @Column(name="numberofseats")
    private  int numberOfSeats;
    @Column(name="boatnumber")
    private  int boatNumber;

    @Column(name = "counter")
    private  long counter;
    @Column(name = "boatmaintenancestatus")
    private boolean BoatMaintenanceStatus;




    public long getCounter() {
        return counter;
    }

    public void setCounter(long counter) {
        this.counter = counter;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public int getBoatNumber() {
        return boatNumber;
    }

    public void setBoatNumber(int boatNumber) {
        this.boatNumber = boatNumber;
    }
    

    public boolean isBoatMaintenanceStatus() {
        return BoatMaintenanceStatus;
    }

    public void setBoatMaintenanceStatus(boolean boatMaintenanceStatus) {
        BoatMaintenanceStatus = boatMaintenanceStatus;
    }


}

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
    @Column(name="bootnumber")
    private  int bootNumber;
    @Column(name="priceperhour")
    private   double pricePerHour;
    @Column(name = "counter")
    private  long counter;

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

    public int getBootNumber() {
        return bootNumber;
    }

    public void setBootNumber(int bootNumber) {
        this.bootNumber = bootNumber;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }


}

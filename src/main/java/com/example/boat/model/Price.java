package com.example.boat.model;

import javax.persistence.*;

@Entity
@Table(name = "Price")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Access(AccessType.PROPERTY)
    private long id;
    @Column(name = "rowboatperhourprice")
    private double rowBoatPerHourPrice;
    @Column(name = "elcboatperhourprice")
    private double elcBoatPerHourPrice;
    @Column(name = "rowreservationprice")
    private double rowReservationPrice;
    @Column(name = "elcreservationprice")
    private double elcReservationPrice;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getRowBoatPerHourPrice() {
        return rowBoatPerHourPrice;
    }

    public void setRowBoatPerHourPrice(double rowBoatPerHourPrice) {
        this.rowBoatPerHourPrice = rowBoatPerHourPrice;
    }

    public double getElcBoatPerHourPrice() {
        return elcBoatPerHourPrice;
    }

    public void setElcBoatPerHourPrice(double elcBoatPerHourPrice) {
        this.elcBoatPerHourPrice = elcBoatPerHourPrice;
    }

    public double getRowReservationPrice() {
        return rowReservationPrice;
    }

    public void setRowReservationPrice(double rowReservationPrice) {
        this.rowReservationPrice = rowReservationPrice;
    }

    public double getElcReservationPrice() {
        return elcReservationPrice;
    }

    public void setElcReservationPrice(double elcReservationPrice) {
        this.elcReservationPrice = elcReservationPrice;
    }
}

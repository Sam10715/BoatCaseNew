package com.example.boat.model;

import javax.persistence.*;

@Entity
@Table(name = "Price")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Access(AccessType.PROPERTY)
    private long id;
    @Column(name = "rowstandaredprice")
    private double rowStanderdPrice;
    @Column(name = "elcstandaredprice")
    private double elcStandardPrice;
    @Column(name = "rowactualprice")
    private double rowActualPrice;
    @Column(name = "elcactualprice")
    private double elcActualPrice;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getRowStanderdPrice() {
        return rowStanderdPrice;
    }

    public void setRowStanderdPrice(double rowStanderdPrice) {
        this.rowStanderdPrice = rowStanderdPrice;
    }

    public double getElcStandardPrice() {
        return elcStandardPrice;
    }

    public void setElcStandardPrice(double elcStandardPrice) {
        this.elcStandardPrice = elcStandardPrice;
    }

    public double getRowActualPrice() {
        return rowActualPrice;
    }

    public void setRowActualPrice(double rowActualPrice) {
        this.rowActualPrice = rowActualPrice;
    }

    public double getElcActualPrice() {
        return elcActualPrice;
    }

    public void setElcActualPrice(double elcActualPrice) {
        this.elcActualPrice = elcActualPrice;
    }
}

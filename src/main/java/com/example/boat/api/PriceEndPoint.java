package com.example.boat.api;

import com.example.boat.controller.PriceService;
import com.example.boat.model.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PriceEndPoint {
    @Autowired
    PriceService priceService;

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/change-row-price", method = RequestMethod.POST, consumes = "application/json")
    public void changeRowPrice(@RequestBody Price price) {
        priceService.changeRowPricePerHour(price);

    }
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/change-elc-price", method = RequestMethod.POST, consumes = "application/json")
    public void changeElcPrice(@RequestBody Price price) {
        priceService.changeElcPricePerHour(price);

    }
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/change-row-res-price", method = RequestMethod.POST, consumes = "application/json")
    public void changeRowResPrice(@RequestBody Price price) {
        priceService.changeRowResPrice(price);

    }
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/change-elc-res-price", method = RequestMethod.POST, consumes = "application/json")
    public void changeElcResPrice(@RequestBody Price price) {
        priceService.changeElcResPrice(price);

    }











}

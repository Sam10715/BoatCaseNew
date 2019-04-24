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
        priceService.changeStandardRowPricePerHour(price);

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/change-elc-price", method = RequestMethod.POST, consumes = "application/json")
    public void changeElcPrice(@RequestBody Price price) {
        priceService.changeStandardElcPricePerHour(price);

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/change-row-actual-price", method = RequestMethod.POST, consumes = "application/json")
    public void changeRowResPrice(@RequestBody Price price) {
        priceService.changeRowActualPrice(price);

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/change-elc-actual-price", method = RequestMethod.POST, consumes = "application/json")
    public void changeElcResPrice(@RequestBody Price price) {
        priceService.changeElcActualPrice(price);

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/get-price", method = RequestMethod.GET)
    public Price getPrice() {
        return priceService.getPrice();

    }


}

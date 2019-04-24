package com.example.boat.api;

import com.example.boat.controller.WeatherService;
import com.example.boat.model.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class WeatherEndPoint {
    @Autowired
    WeatherService weatherService;


    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/save-weather", method = RequestMethod.POST, consumes = "application/json")
    public void saveWeather(@RequestBody Weather weather){
        weatherService.saveWeather(weather);
    }
}

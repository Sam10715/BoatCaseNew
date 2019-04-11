package com.example.boat.api;

import com.example.boat.controller.BoatService;
import com.example.boat.model.Boat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BoatEndPoint {
    @Autowired
    BoatService boatService;

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/save-boat", method = RequestMethod.POST, consumes = "application/json")
    public void saveOneBoat(@RequestBody Boat boat) {
        boatService.saveBoat(boat);

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/get-boats-overview", method = RequestMethod.GET)
    public List<List<Integer>> saveOneBoat(@RequestParam String date) {
   return      boatService.allBoatsOverView(date);


    }


}

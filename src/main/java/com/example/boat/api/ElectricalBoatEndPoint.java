package com.example.boat.api;

import com.example.boat.controller.BoatService;
import com.example.boat.model.ElectricalBoat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ElectricalBoatEndPoint {

    @Autowired
    BoatService boatService;
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/save-electricalboat", method = RequestMethod.POST, consumes = "application/json")
    public boolean saveOneBoat(@RequestBody ElectricalBoat electricalBoat) {
     return    boatService.saveBoat(electricalBoat);

    }


}


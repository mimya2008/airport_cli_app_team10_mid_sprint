package com.keyin.controller;

import com.keyin.domain.Aircraft;
import com.keyin.domain.Airport;
import com.keyin.repository.AircraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/aircraft")
@CrossOrigin(origins = "*")
public class AircraftController {

    @Autowired
    private AircraftRepository aircraftRepository;

    @GetMapping
    public List<Aircraft> getAllAircraft() {
        return aircraftRepository.findAll();
    }

    @GetMapping("/{id}")
    public Aircraft getAircraftById(@PathVariable Long id) {
        return aircraftRepository.findById(id).orElse(null);
    }

    @GetMapping("/{id}/airports")
    public List<Airport> getAirportsByAircraft(@PathVariable Long id) {
        Aircraft aircraft = aircraftRepository.findById(id).orElse(null);
        return (aircraft != null) ? aircraft.getAirports() : List.of();
    }

}



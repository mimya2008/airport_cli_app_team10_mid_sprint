package com.keyin.controller;

import com.keyin.domain.City;
import com.keyin.domain.Airport;
import com.keyin.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/city")
public class CityController {

    @Autowired
    private CityRepository cityRepository;

    @GetMapping
    public List<City> getAllCitiesWithAirports() {
        return cityRepository.findAll();
    }

    @GetMapping("/{id}")
    public City getCityById(@PathVariable Long id) {
        return cityRepository.findById(id).orElse(null);
    }

    @GetMapping("/{id}/airports")
    public List<Airport> getAirportsByCity(@PathVariable Long id) {
        City city = cityRepository.findById(id).orElse(null);
        return (city != null) ? city.getAirports() : List.of();
    }

}


package com.keyin.controller;

import com.keyin.domain.Aircraft;
import com.keyin.domain.Airport;
import com.keyin.domain.Passenger;
import com.keyin.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/passenger")
@CrossOrigin(origins = "*")
public class PassengerController {

    @Autowired
    private PassengerRepository passengerRepository;

    // Get all passengers
    @GetMapping
    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }

    // Get a specific passenger by ID
    @GetMapping("/{id}")
    public Passenger getPassengerById(@PathVariable Long id) {
        return passengerRepository.findById(id).orElse(null);
    }

    // Get all aircraft flown by a specific passenger
    @GetMapping("/{id}/aircraft")
    public List<Aircraft> getAircraftByPassenger(@PathVariable Long id) {
        Passenger passenger = passengerRepository.findById(id).orElse(null);
        return (passenger != null && passenger.getAircraft() != null)
                ? List.copyOf(passenger.getAircraft())
                : List.of();
    }

    // Get all airports used by aircraft flown by the passenger
    @GetMapping("/{id}/airports")
    public List<Airport> getAirportsByPassenger(@PathVariable Long id) {
        Passenger passenger = passengerRepository.findById(id).orElse(null);
        if (passenger == null || passenger.getAircraft() == null) return List.of();

        return passenger.getAircraft().stream()
                .flatMap(ac -> ac.getAirports().stream())
                .distinct()
                .collect(Collectors.toList());
    }
}


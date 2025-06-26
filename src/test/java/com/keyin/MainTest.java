package com.keyin;

import com.keyin.domain.*;
import com.keyin.http.client.RESTClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Scanner;

import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link Main} CLI application.
 * These tests simulate user input and validate interactions with the {@link RESTClient}.
 */
public class MainTest {

    private RESTClient mockClient;
    private Scanner mockScanner;
    private Main app;

    /**
     * Setup mocks for RESTClient and Scanner before each test.
     * Instantiates the Main CLI with the mocked dependencies.
     */
    @BeforeEach
    public void setUp() {
        mockClient = mock(RESTClient.class);
        mockScanner = mock(Scanner.class);
        app = new Main(mockClient, mockScanner);
    }

    /**
     * Test displaying airports for each city (Option 1).
     */
    @Test
    public void testDisplayAirportsPerCity() {
        City city = new City(1L, "St. John's", "YYT", 120000);
        city.setAirports(List.of(new Airport(1L, "YYT International", "YYT")));

        when(mockClient.getCitiesWithAirports()).thenReturn(List.of(city));
        when(mockScanner.nextLine()).thenReturn("1", "0");

        app.run();

        verify(mockClient).getCitiesWithAirports();
    }


    /**
     * Test displaying aircraft per passenger (Option 2).
     */
    @Test
    public void testDisplayAircraftPerPassenger() {
        Passenger passenger = new Passenger(1L, "Alice", "Smith", "123456789");
        Aircraft aircraft = new Aircraft(1L, "Boeing 747", "Air Canada", 300);
        passenger.setAircraft(List.of(aircraft));

        when(mockClient.getPassengersWithAircraft()).thenReturn(List.of(passenger));
        when(mockScanner.nextLine()).thenReturn("2", "0");

        app.run();

        verify(mockClient).getPassengersWithAircraft();
    }

    /**
     * Test displaying airports per aircraft (Option 3).
     */
    @Test
    public void testDisplayAirportsPerAircraft() {
        Aircraft aircraft = new Aircraft(1L, "Airbus A320", "WestJet", 150);
        aircraft.setAirports(List.of(new Airport(1L, "Toronto Pearson", "YYZ")));

        when(mockClient.getAircraftWithAirports()).thenReturn(List.of(aircraft));
        when(mockScanner.nextLine()).thenReturn("3", "0");

        app.run();

        verify(mockClient).getAircraftWithAirports();
    }

    /**
     * Test displaying airports used by each passenger (Option 4).
     */
    @Test
    public void testDisplayAirportsUsedByPassenger() {
        Passenger passenger = new Passenger(1L, "Bob", "Johnson", "555-0100");
        Aircraft aircraft = new Aircraft(1L, "Cessna 172", "TestFly", 4);
        Airport airport = new Airport(1L, "Gander Airport", "YQX");
        aircraft.setAirports(List.of(airport));
        passenger.setAircraft(List.of(aircraft));

        when(mockClient.getPassengerAirportUsage()).thenReturn(List.of(passenger));
        when(mockScanner.nextLine()).thenReturn("4", "0");

        app.run();

        verify(mockClient).getPassengerAirportUsage();
    }

    /**
     * Test CLI behavior with invalid (non-numeric) input.
     */
    @Test
    public void testInvalidInputHandling() {
        when(mockScanner.nextLine()).thenReturn("abc", "0");

        app.run();

        verifyNoInteractions(mockClient);
    }

    /**
     * Test the application exit option (Option 0).
     */
    @Test
    public void testExitApplication() {
        when(mockScanner.nextLine()).thenReturn("0");

        app.run();

        verifyNoInteractions(mockClient);
    }



}

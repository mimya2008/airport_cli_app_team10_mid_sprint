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
     * Instantiates with the mocked dependencies.
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






}

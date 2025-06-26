package com.keyin.http.cli;

import com.keyin.domain.Airport;
import com.keyin.http.client.RESTClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class HTTPClientCLIApplicationTest {

    private HTTPClientCLIApplication cliApp;
    private RESTClient mockRestClient;

    @BeforeEach
    public void setUp() {
        mockRestClient = Mockito.mock(RESTClient.class);
        cliApp = new HTTPClientCLIApplication();
        cliApp.setRestClient(mockRestClient);
    }

    @Test
    public void testRun_withMockedAirportData() {
        // Arrange: Mock the user input and REST client response
        String simulatedInput = "http://localhost:8080\n";  // simulates user entering the URL
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        List<Airport> fakeAirports = Arrays.asList(
                new Airport(1L, "YYT International", "YYT"),
                new Airport(2L, "YYZ International", "YYZ")
        );

        when(mockRestClient.getAllAirports()).thenReturn(fakeAirports);

        // Act
        cliApp.run();

        // Assert
        verify(mockRestClient).setServerURL("http://localhost:8080");
        verify(mockRestClient).getAllAirports();
    }

    @Test
    public void testRun_withNoAirportData() {
        // Arrange: Simulate valid user input but no data returned
        String simulatedInput = "http://localhost:8080\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        when(mockRestClient.getAllAirports()).thenReturn(List.of());

        // Act
        cliApp.run();

        // Assert
        verify(mockRestClient).setServerURL("http://localhost:8080");
        verify(mockRestClient).getAllAirports();
    }
}

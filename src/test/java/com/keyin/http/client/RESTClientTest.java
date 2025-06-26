package com.keyin.http.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.keyin.domain.Airport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RESTClientTest {

    private RESTClient restClient;
    private HttpClient mockHttpClient;
    private ObjectMapper objectMapper;
    private HttpResponse<String> mockResponse;

    @BeforeEach
    public void setup() {
        mockHttpClient = mock(HttpClient.class);
        objectMapper = new ObjectMapper();
        mockResponse = mock(HttpResponse.class);

        restClient = new RESTClient(mockHttpClient, objectMapper);
        restClient.setServerURL("http://localhost:8080");
    }

    @Test
    public void testGetAllAirports_returnsAirportList() throws Exception {
        String jsonResponse = """
            [
              { "id": 1, "name": "St. John's International", "code": "YYT" },
              { "id": 2, "name": "Toronto Pearson", "code": "YYZ" }
            ]
        """;

        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn(jsonResponse);
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(mockResponse);

        List<Airport> result = restClient.getAllAirports();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("YYT", result.get(0).getCode());
        assertEquals("Toronto Pearson", result.get(1).getName());
    }

    @Test
    public void testGetAllAirports_handlesHttpError() throws Exception {
        when(mockResponse.statusCode()).thenReturn(500);
        when(mockResponse.body()).thenReturn("Internal Server Error");
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(mockResponse);

        List<Airport> result = restClient.getAllAirports();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}


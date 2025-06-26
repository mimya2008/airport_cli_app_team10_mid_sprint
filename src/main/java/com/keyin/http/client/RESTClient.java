package com.keyin.http.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.keyin.domain.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;

public class RESTClient {

    private final HttpClient client;
    private final ObjectMapper objectMapper;
    private String serverURL = "http://localhost:8080";

    // Default constructor
    public RESTClient() {
        this(HttpClient.newHttpClient(), new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false));
    }

    // Constructor for injecting mocks during testing
    public RESTClient(HttpClient client, ObjectMapper objectMapper) {
        this.client = client;
        this.objectMapper = objectMapper;
    }

    public void setServerURL(String serverURL) {
        this.serverURL = serverURL;
    }

    public String getServerURL() {
        return serverURL;
    }

    public List<Airport> getAllAirports() {
        return fetchList("/airport", new TypeReference<List<Airport>>() {});
    }

    public List<City> getCitiesWithAirports() {
        return fetchList("/city", new TypeReference<List<City>>() {});
    }

    public List<Passenger> getPassengersWithAircraft() {
        return fetchList("/passenger", new TypeReference<List<Passenger>>() {});
    }

    public List<Aircraft> getAircraftWithAirports() {
        return fetchList("/aircraft", new TypeReference<List<Aircraft>>() {});
    }

    public List<Passenger> getPassengerAirportUsage() {
        return getPassengersWithAircraft();
    }

    private <T> List<T> fetchList(String path, TypeReference<List<T>> typeRef) {
        try {
            String url = serverURL + path;
            String response = sendGetRequest(url);
            return objectMapper.readValue(response, typeRef);
        } catch (IOException | InterruptedException e) {
            System.err.println("Request failed for endpoint " + path + ": " + e.getMessage());
            return Collections.emptyList();
        }
    }

    private String sendGetRequest(String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            System.err.println("HTTP GET Error: " + response.statusCode() + " at URL: " + url);
        }

        return response.body();
    }
}

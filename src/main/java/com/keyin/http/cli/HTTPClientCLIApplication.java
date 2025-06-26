package com.keyin.http.cli;

import com.keyin.domain.Airport;
import com.keyin.http.client.RESTClient;

import java.util.List;
import java.util.Scanner;

public class HTTPClientCLIApplication {
    private RESTClient restClient = new RESTClient();

    public void setRestClient(RESTClient restClient) {
        this.restClient = restClient;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter API URL (e.g., http://localhost:8080/airport): ");
        String url = scanner.nextLine();
        restClient.setServerURL(url);

        System.out.println("\n==== Airport CLI Menu ====");
        List<Airport> airports = restClient.getAllAirports();
        airports.forEach(a -> System.out.println(a.getName() + " (" + a.getCode() + ")"));
    }

    public static void main(String[] args) {
        HTTPClientCLIApplication app = new HTTPClientCLIApplication();
        app.run();
    }
}



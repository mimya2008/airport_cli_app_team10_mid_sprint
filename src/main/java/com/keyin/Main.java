package com.keyin;

import com.keyin.domain.*;
import com.keyin.http.client.RESTClient;

import java.util.List;
import java.util.Scanner;

public class Main {

    private final RESTClient client;
    private final Scanner scanner;

    public Main(RESTClient client, Scanner scanner) {
        this.client = client;
        this.scanner = scanner;
    }

    public void run() {
        while (true) {
            System.out.println("\n===== Airport CLI =====");
            System.out.println("1. View airports in each city");
            System.out.println("2. View aircraft flown by each passenger");
            System.out.println("3. View airports used by each aircraft");
            System.out.println("4. View airports used by each passenger");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            String input = scanner.nextLine();
            int choice;

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1 -> {
                    List<City> cities = client.getCitiesWithAirports();
                    for (City city : cities) {
                        System.out.println("\nCity: " + city.getName());
                        List<Airport> airports = city.getAirports();
                        if (airports == null || airports.isEmpty()) {
                            System.out.println("  - No airports found.");
                        } else {
                            for (Airport airport : airports) {
                                System.out.println("  - Airport: " + airport.getName() + " (" + airport.getCode() + ")");
                            }
                        }
                    }
                }
                case 2 -> {
                    List<Passenger> passengers = client.getPassengersWithAircraft();
                    for (Passenger p : passengers) {
                        System.out.println("\nPassenger: " + p.getFirstName() + " " + p.getLastName());
                        List<Aircraft> aircraftList = p.getAircraft();
                        if (aircraftList == null || aircraftList.isEmpty()) {
                            System.out.println("  - No aircraft data available.");
                        } else {
                            for (Aircraft a : aircraftList) {
                                System.out.println("  - " + a.getType() + " (" + a.getAirlineName() + ")");
                            }
                        }
                    }
                }
                case 3 -> {
                    List<Aircraft> aircraftList = client.getAircraftWithAirports();
                    for (Aircraft a : aircraftList) {
                        System.out.println("\nAircraft: " + a.getType() + " by " + a.getAirlineName());
                        List<Airport> airports = a.getAirports();
                        if (airports == null || airports.isEmpty()) {
                            System.out.println("  - No airport found.");
                        } else {
                            for (Airport airport : airports) {
                                System.out.println("  - Airport: " + airport.getName() + " (" + airport.getCode() + ")");
                            }
                        }
                    }
                }
                case 4 -> {
                    List<Passenger> passengers = client.getPassengerAirportUsage();
                    for (Passenger p : passengers) {
                        System.out.println("\nPassenger: " + p.getFirstName() + " " + p.getLastName());
                        List<Aircraft> aircraftList = p.getAircraft();
                        if (aircraftList == null || aircraftList.isEmpty()) {
                            System.out.println("  - No aircraft flown.");
                        } else {
                            for (Aircraft ac : aircraftList) {
                                System.out.println("  - Aircraft: " + ac.getType());
                                List<Airport> airports = ac.getAirports();
                                if (airports == null || airports.isEmpty()) {
                                    System.out.println("      * No airport for this aircraft.");
                                } else {
                                    for (Airport airport : airports) {
                                        System.out.println("      * " + airport.getName() + " (" + airport.getCode() + ")");
                                    }
                                }
                            }
                        }
                    }
                }
                case 0 -> {
                    System.out.println("Exiting.");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    public static void main(String[] args) {
        RESTClient client = new RESTClient();
        Scanner scanner = new Scanner(System.in);
        Main app = new Main(client, scanner);
        app.run();
    }
}

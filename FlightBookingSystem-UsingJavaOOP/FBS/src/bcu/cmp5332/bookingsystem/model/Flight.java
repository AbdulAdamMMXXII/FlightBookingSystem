package bcu.cmp5332.bookingsystem.model;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Flight {
    
    private int id;
    private String flightNumber;
    private String origin;
    private String destination;
    private LocalDate departureDate;
    private int capacity;
    private double price;
    private boolean isRemoved = false;

    private final Set<Customer> passengers;

    public Flight(int id, String flightNumber, String origin, String destination, LocalDate departureDate, int capacity, double price) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
        this.capacity = capacity;
        this.price = price;
        
        
        passengers = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }
    
    public String getOrigin() {
        return origin;
    }
    
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

        public void setCapacity(int capacity) {
    	this.capacity = capacity;
    }
    
    public int getCapacity() {
    	return capacity;
    }
    
    public void setPrice(double price) {
    	this.price = price;
    }
    
    public double getPrice() {
    	return price;
    }
    public boolean isRemoved() {
    	return isRemoved;
    }
    public void setRemoved(boolean isRemoved) {
    	this.isRemoved = isRemoved;
    }
    public List<Customer> getPassengers() {
        return new ArrayList<>(passengers);
    }
	
    public String getDetailsShort() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "Flight #" + id + " - " + flightNumber + " - " + origin + " to " 
                + destination + " on " + departureDate.format(dtf) +  " - Capacity of seats:  " + capacity + " - price: £" + price;
    }

    public String getDetailsLong() {
        // TODO: implementation here
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/YYYY");
    	StringBuilder details = new StringBuilder();
    	details.append("Flight #").append(id).append("\n");
    	details.append("Flight No: ").append(flightNumber).append("\n");
    	details.append("Origin: ").append(origin).append("\n");
    	details.append("Destination: ").append(destination).append("\n");
    	details.append("Departure Date: ").append(departureDate.format(dtf)).append("\n");
        details.append("Capacity of Seats: ").append(capacity).append("\n");
    	details.append("Price: £").append(price).append("\n");
    	details.append("---------------------------\n");
    	details.append("Passengers: \n");
    	
    	if (passengers.isEmpty()) {
    		details.append("No passengers. \n");
    	} else {
    		for (Customer passenger : passengers) {
    			details.append("* Id: ").append(passenger.getId())
    			.append(" - ").append(passenger.getName())
    			.append(" - ").append(passenger.getPhone())
    			.append(" - ").append(passenger.getEmail())
    			.append("\n");
    		}
    	}
    	details.append(passengers.size()).append(" passenger(s)");
        return details.toString();
    }
    
    public void addPassenger(Customer passenger) throws FlightBookingSystemException {
    	//check if the flight is full before adding a passenger
    	if (passengers.size() >= capacity) {
    		throw new FlightBookingSystemException("This flight is full. Please try a different flight. ");
    	}
    	if (passengers.contains(passenger)) {
    		throw new FlightBookingSystemException("This passenger already booked on this flight.");
    	}
        passengers.add(passenger);
    }
    
    public void removePassenger(Customer passenger) throws FlightBookingSystemException {
    	if (!passengers.contains(passenger)) {
    		throw new FlightBookingSystemException("This passenger not found on this flight.");
    	}
    	passengers.remove(passenger);
    }
    //a method to calculate the increased price
    public double calculateIncreasedPrice(LocalDate systemDate) {
    	int daysToDeparture = (int) systemDate.until(departureDate).getDays();
    	double capacityRatio = (double) passengers.size() / capacity;
    	
    	double priceMultiplier = 1.0;
    	
    	//increase the price based on how many days left
    	if (daysToDeparture <= 10) {
    		priceMultiplier += 0.85; //85% increase for bookings within 10 days
    	} else if (daysToDeparture <= 30) {
    		priceMultiplier += 0.5; //50% price increase for bookings within 30 days
    	} else if (daysToDeparture <= 60) {
    		priceMultiplier += 0.25; //25% price increase for bookings within 60 days
    	}
    	
    	//increase price based on how many seats left
    	if (capacityRatio >= 0.8) {
    		priceMultiplier += 0.3; //30% price increase if 80% of seats are filled
    	} else if (capacityRatio >= 0.5) {
    		priceMultiplier += 0.15; //15% price increase if 50% of seats are filled
    	} else if (capacityRatio >= 0.2) {
    		priceMultiplier += 0.05; //5% price increase if 20% of seats are filled
    	}
    	
    	return price * priceMultiplier;
    }
}
package bcu.cmp5332.bookingsystem.model;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import java.time.LocalDate;
import java.util.*;

public class FlightBookingSystem {
    
    private final LocalDate systemDate = LocalDate.parse("2024-11-11");
    
    private final Map<Integer, Customer> customers = new TreeMap<>();
    private final Map<Integer, Flight> flights = new TreeMap<>();
    private final List<Booking> bookings = new ArrayList<>(); // list bookings


    public LocalDate getSystemDate() {
        return systemDate;
    }

    public List<Flight> getFlights() {
        List<Flight> out = new ArrayList<>();
        //exclude the removed flights when fetching the flights list
        for (Flight flight : flights.values()) {
        	if (!flight.isRemoved()) {
        		out.add(flight);
        	}
        }
        return Collections.unmodifiableList(out);
    }
    
    //a method to list only the future flights
    public List<Flight> getFutureFlights() {
        List<Flight> futureFlights = new ArrayList<>();
        //exclude the removed and departed flights when fetching the flights list
        for (Flight flight : flights.values()) {
        	if (!flight.isRemoved() && flight.getDepartureDate().isAfter(systemDate)) {
        		futureFlights.add(flight);
        	}
        }
        return Collections.unmodifiableList(futureFlights);
    }
    // a method to list customers
    public List<Customer> getCustomers() {
    	List<Customer> out = new ArrayList<>();
    	//exclude the removed customers when fetching the customers list
        for (Customer customer : customers.values()) {
        	if (!customer.isRemoved()) {
        		out.add(customer);
        	}
        }
    	return Collections.unmodifiableList(out);
    }
    
    //A method to manage the list of booking
    public List<Booking> getBookings(){
    	return Collections.unmodifiableList(bookings);
    }
    //a method
    public List<String> getFutureFlightWithIncreasedPricee(){
    	List<String> details = new ArrayList<>();
    	for (Flight flight : getFutureFlights()) {
    		double increasedPrice = flight.calculateIncreasedPrice(systemDate);
    		details.add(flight.getDetailsShort() + " - Increased price: £" + increasedPrice);
    	}
    	return details;
    }

    public Flight getFlightByID(int id) throws FlightBookingSystemException {
        if (!flights.containsKey(id)) {
            throw new FlightBookingSystemException("There is no flight with that ID.");
        }
        //check and confirm if the flight is removed
        Flight flight = flights.get(id);
        if (flight.isRemoved()) {
        	throw new FlightBookingSystemException("The flight with ID " + id + " has been removed. ");
        }
        return flight;
    }

    public Customer getCustomerByID(int id) throws FlightBookingSystemException {
        // TODO: implementation here
    	if (!customers.containsKey(id)) {
            throw new FlightBookingSystemException("There is no customer with that ID.");
        }
    	//check and confirm if the customer is removed
        Customer customer = customers.get(id);
        if (customer.isRemoved()) {
        	throw new FlightBookingSystemException("The customer with ID " + id + " has been removed. ");
        }
        return customer;
    }

    public void addFlight(Flight flight) throws FlightBookingSystemException {
        if (flights.containsKey(flight.getId())) {
            throw new IllegalArgumentException("Duplicate flight ID.");
        }
        for (Flight existing : flights.values()) {
            if (existing.getFlightNumber().equals(flight.getFlightNumber()) 
                && existing.getDepartureDate().isEqual(flight.getDepartureDate())) {
                throw new FlightBookingSystemException("There is a flight with same "
                        + "number and departure date in the system");
            }
        }
        flights.put(flight.getId(), flight);
    }

    public void addCustomer(Customer customer) throws FlightBookingSystemException {
        // TODO: implementation here
    	if (customers.containsKey(customer.getId())) {
            throw new IllegalArgumentException("Duplicate customer ID.");
        }
        for (Customer existing : customers.values()) {
            if (existing.getEmail().equalsIgnoreCase(customer.getEmail())
        		&& existing.getName().equals(customer.getName())) {
                throw new FlightBookingSystemException("There is a customer with same "
                        + "name and email in the system");
            }
        }
        customers.put(customer.getId(), customer);
    }
    // a method to add booking into the bookings
    public void addBooking(Booking booking) {
    	bookings.add(booking);
    }
  //allow modification of the bookings list within the fbs's class
    public void removeBooking(Booking booking) {
    	bookings.remove(booking);
    }

}

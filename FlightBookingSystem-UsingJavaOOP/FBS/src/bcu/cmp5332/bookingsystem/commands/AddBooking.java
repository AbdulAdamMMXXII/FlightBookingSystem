package bcu.cmp5332.bookingsystem.commands;

import java.time.LocalDate;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

public class AddBooking implements Command{
	
	private final int customerId;
	private final int flightId;
	private final LocalDate bookingDate;
	
	public AddBooking(int customerId, int flightId, LocalDate bookingDate) {
		this.customerId = customerId;
		this.flightId = flightId;
		this.bookingDate = bookingDate;
	}

	@Override
	public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        //get customer and flight by ID from the FBS to complete the AddBooking execution
		Customer customer = flightBookingSystem.getCustomerByID(customerId); 
        Flight flight = flightBookingSystem.getFlightByID(flightId); 
		
        //Generate and assign a booking ID
        int maxId = flightBookingSystem.getBookings().stream()
        		.mapToInt(Booking::getId)
        		.max().orElse(0);
        
        //calculate the dynamic price
        double increasedPrice = flight.calculateIncreasedPrice(flightBookingSystem.getSystemDate());
        
        //create and store booking to the fbs's booking list
        Booking booking = new Booking(++maxId, customer, flight, bookingDate, increasedPrice);
		customer.addBooking(booking);
		flight.addPassenger(customer);		
		flightBookingSystem.addBooking(booking); //Saves the entities into fbs

        System.out.println("Booking # " + booking.getId() + " added for customer: " + customer.getName() 
        					+ " on flight: " + flight.getFlightNumber() + " for date: " + bookingDate + " With price: £" + increasedPrice);
		
	}

}

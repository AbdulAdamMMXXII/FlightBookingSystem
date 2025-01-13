package bcu.cmp5332.bookingsystem.commands;

import java.io.IOException;

import bcu.cmp5332.bookingsystem.data.BookingDataManager;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

public class CancelBooking implements Command {
	private final int customerId;
	private final int flightId;
	
	public CancelBooking(int customerId, int flightId) {
		this.customerId = customerId;
		this.flightId = flightId;
	}

	@Override
	public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        //get customer and flight by ID from the FBS to complete the CancelBooking execution
		Customer customer = flightBookingSystem.getCustomerByID(customerId); 
        Flight flight = flightBookingSystem.getFlightByID(flightId); 
        
        Booking bookingToRemove = null;
        
        // find a booking to be cancelled
        for (Booking booking : customer.getBookings()) {
        	if (booking.getFlight().equals(flight)) {
        		bookingToRemove = booking;
        		break;
        	}
        }
		if (bookingToRemove == null) {
			throw new FlightBookingSystemException("No booking found with customer ID " 
					+ customerId + " and flight ID " + flightId);
		}
		//remove the booking from the customer and flight
        customer.removeBooking(flight);
        flight.removePassenger(customer);
        
        //remove the booking from fbs's booking list
        flightBookingSystem.removeBooking(bookingToRemove);
        
        //update the bookings.txt file
        BookingDataManager bookingDataManager = new BookingDataManager();
        try {
        	bookingDataManager.storeData(flightBookingSystem);
        } catch (IOException e) {
        	throw new FlightBookingSystemException("Error updating bookings.txt file: " + e.getMessage());
        }

       System.out.println("Booking was cancelled successfully.");
		
	}
	 
}

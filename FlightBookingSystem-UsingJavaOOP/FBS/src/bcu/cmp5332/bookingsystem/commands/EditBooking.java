package bcu.cmp5332.bookingsystem.commands;

import java.io.IOException;
import java.util.Scanner;

import bcu.cmp5332.bookingsystem.data.BookingDataManager;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

public class EditBooking implements Command {
	private final int bookingId;
	private final int flightId;
	
	public EditBooking(int bookingId, int flightId) {
		this.bookingId = bookingId;
		this.flightId = flightId;
	}
	
	@Override
	public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
		Scanner scanner = new Scanner(System.in);
		
		//find the booking first
		Booking bookingToEdit = null;
		for (Booking booking : flightBookingSystem.getBookings()) {
			if (booking.getId() == bookingId) {
				bookingToEdit = booking;
				break;
			}
		}
		
		if (bookingToEdit == null) {
			throw new FlightBookingSystemException("No booking found with ID: " + bookingId);
		}
		
		//impose rebooking fee
		double rebookFee = 12.99;
		bookingToEdit.setRebookFee(rebookFee);
		
		System.out.println("A rebooking fee of £" + rebookFee + " will apply. ");
		
		//prompt the user for new flight ID
		System.out.println("Enter new flight ID: ");
		int newFlightId = Integer.parseInt(scanner.nextLine());
		
		//check the new flight and ensure it's from the fbs's flights list
		Flight newFlight = flightBookingSystem.getFlightByID(newFlightId);
		
		//remove the old flight from the booking and replace it with new flight details
		bookingToEdit.getFlight().removePassenger(bookingToEdit.getCustomer());
		bookingToEdit.setFlight(newFlight);
		
		//add new flight to the booking
		newFlight.addPassenger(bookingToEdit.getCustomer());
		
		// update the changes in the bookings.txt file
		BookingDataManager bookingDataManager = new BookingDataManager();
		try {
			bookingDataManager.storeData(flightBookingSystem);
		} catch (IOException e) {
			throw new FlightBookingSystemException("Unable to updating bookings.txt: " + e.getMessage());
		}
		System.out.println("Booking updated successfully. Tootal cost: £" + bookingToEdit.getTotalPrice());
	}
	
}

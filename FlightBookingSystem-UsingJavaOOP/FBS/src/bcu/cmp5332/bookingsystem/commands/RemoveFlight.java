package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

public class RemoveFlight implements Command {
	private final int flightId;
	
	public RemoveFlight(int flightId) {
		this.flightId = flightId;
	}
	
	@Override
	public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
		Flight flight = flightBookingSystem.getFlightByID(flightId);
		flight.setRemoved(true); //Mark the flight as removed
		System.out.println("Flight " + flight.getFlightNumber() + " has been removed. ");
		
	}

}

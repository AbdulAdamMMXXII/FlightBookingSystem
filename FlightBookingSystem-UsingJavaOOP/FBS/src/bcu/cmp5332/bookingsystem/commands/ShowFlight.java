package bcu.cmp5332.bookingsystem.commands;

import java.util.List;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

public class ShowFlight implements Command {
	
	private final int flightId;
	
	public ShowFlight(int flightId) {
		this.flightId = flightId;
	}
	
	@Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        List<Flight> flights = flightBookingSystem.getFlights();
        for (Flight flight : flights) {
        	if(flight.getId() == flightId) {
        		System.out.println(flight.getDetailsLong());
        		return;
        	} 
        }
        System.out.println("Flight with ID " + flightId + " not found.");
    }

}

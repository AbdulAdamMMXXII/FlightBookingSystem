package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

public class RemoveCustomer implements Command {
private final int customerId;
	
	public RemoveCustomer(int customerId) {
		this.customerId = customerId;
	}
	
	@Override
	public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
		Customer customer = flightBookingSystem.getCustomerByID(customerId);
		customer.setRemoved(true); //mark the customer as removed
		System.out.println("Customer " + customer.getName() + " has been removed. ");
		
	}

}

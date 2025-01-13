package bcu.cmp5332.bookingsystem.commands;

import java.util.List;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

public class ShowCustomer implements Command {
	
	private final int customerId;
	
	public ShowCustomer(int customerId) {
		this.customerId = customerId;
	}
	
	@Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        
    	//Create customer list 
        List<Customer> customers = flightBookingSystem.getCustomers();
        //Loop through the customer list and for each customer list their details i.e id, email and phone number
        for (Customer customer : customers){
        	if (customer.getId() == customerId) {
        		System.out.println(customer.getDetailsLong());
        		return;
        	}
        }
        System.out.println("Customer with ID " + customerId + " not found. ");

    }

}

package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.util.List;

public class ListCustomer implements Command{

    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        
    	//Create customer list 
        List<Customer> customers = flightBookingSystem.getCustomers();
        //Loop through the customer list and for each customer list their details i.e id, email and phone number
        for (Customer customer : customers){
            System.out.println(customer.getDetailsShort());
        }
        System.out.println(customers.size() + " Customer(s)");

    }

}


package bcu.cmp5332.bookingsystem.model;

import java.time.LocalDate;

public class Booking {
    
	private int id;
    private Customer customer;
    private Flight flight;
    private LocalDate bookingDate;
    private double rebookFee;
    private double increasedPrice;
    

    public Booking(int id, Customer customer, Flight flight, LocalDate bookingDate, double increasedPrice) {
        // TODO: implementation here
    	this.id = id;
		this.customer = customer;
		this.flight = flight;
		this.bookingDate = bookingDate;
		this.increasedPrice = increasedPrice;

    	
        
    }

    // TODO: implementation of Getter and Setter methods
    public int getId() {
    	return id;
    }  
    public void setId(int id) {
    	this.id = id;
    }
    
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}


	public Flight getFlight() {
		return flight;
	}
	public void setFlight(Flight flight) {
		this.flight = flight;
	}


	public LocalDate getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(LocalDate bookingDate) {
		this.bookingDate = bookingDate;
	}  
	
	public double getRebookFee() {
		return rebookFee;
	}
	public void setRebookFee(double rebookFee) {
		this.rebookFee = rebookFee;
	}
	public double getTotalPrice() {
		return flight.getPrice() + rebookFee; //this adds the rebooking fee into the total price
	}
	
	public double getIncreasedPrice() {
		return increasedPrice; //get and store dynamic price when creating a new booking
	}
    
}

package bcu.cmp5332.bookingsystem.model;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Customer {
    
    private int id;
    private String name;
    private String phone;
    private String email; // new property for email
    private boolean isRemoved = false;
    private final List<Booking> bookings = new ArrayList<>();
    
    // TODO: implement constructor here
    public Customer(int id, String name, String phone, String email) {
    	this.id = id;
    	this.name = name;
    	this.phone = phone;
    	this.email = email;
    	
    }
    
    // TODO: implementation of Getter and Setter methods

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	// email property getter and setter
	public String getEmail(){
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
    public boolean isRemoved() {
    	return isRemoved;
    }
    public void setRemoved(boolean isRemoved) {
    	this.isRemoved = isRemoved;
    }
	
    // list booking
	public List<Booking> getBookings() {
        return new ArrayList<>(bookings);
    }
    
	public String getDetailsShort() { 
        return "Customer #" + id + name + " - " + phone + " - " + email;
    }
	 public String getDetailsLong() {
	     // TODO: implementation here
		 StringBuilder details = new StringBuilder();
		 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/YYYY");
		 
    	 details.append("Customer #").append(id).append("\n");
    	 details.append("Name: ").append(name).append("\n");
    	 details.append("Phone: ").append(phone).append("\n");
    	 details.append("Email: ").append(email).append("\n");
    	 details.append("---------------------------\n");
    	 details.append("Bookings: \n");
	    	
	    	if (bookings.isEmpty()) {
	    		details.append("No bookings. \n");
	    	} else {
	    		for (Booking booking : bookings) {
	    			Flight flight = booking.getFlight();
	    			details.append("* Booking date: ").append(booking.getBookingDate().format(dtf))
	    			.append(" for Flight #").append(flight.getId())
	    			.append(" - ").append(flight.getFlightNumber())
	    			.append(" - ").append(flight.getOrigin())
	    			.append(" to ").append(flight.getDestination())
	    			.append(" on ").append(flight.getDepartureDate().format(dtf))
	    			.append("\n");
	    		}
	    	}
	    	details.append(bookings.size()).append(" booking(s)");
	        return details.toString();
	    }

    public void addBooking(Booking booking) throws FlightBookingSystemException{
        // TODO: implementation here
    	for (Booking existingBooking : bookings) {
    		if (existingBooking.getFlight().getId() == booking.getFlight().getId()) {
    			throw new FlightBookingSystemException("This flight is already booked for this customer. "); 
    		}
    	}
    	bookings.add(booking);
    }
    
    // Cancel booking for flight
    public void removeBooking(Flight flight) throws FlightBookingSystemException {
    	Booking bookingToRemove = null;
    	
    	for (Booking booking : bookings) {
    		if (booking.getFlight().getId() == flight.getId()) {
    			bookingToRemove = booking;
    			break;
    		}
    	}
    	
    	// Remove booking 
    	if (bookingToRemove == null) {
    		throw new FlightBookingSystemException("No booking found for the specified flight.");
    	}
    	bookings.remove(bookingToRemove);
    }
}

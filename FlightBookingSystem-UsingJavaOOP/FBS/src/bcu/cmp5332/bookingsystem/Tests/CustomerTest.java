package bcu.cmp5332.bookingsystem.Tests;
 
import static org.junit.Assert.*;
 
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Test;
 
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
 
public class CustomerTest {
	Customer customer = new Customer(1, "Bill Young", "0789897898", "bill@yahoo");
	Flight flight = new Flight(3, "BM345", "Paris", "Italy", LocalDate.parse("2025-10-04"),50, 89.99);
	double increasedPrice = flight.calculateIncreasedPrice(LocalDate.now());
	Booking booking = new Booking(1, customer,flight, LocalDate.now(), increasedPrice);

	@Test
	public void testGetId() throws FlightBookingSystemException{
		assertEquals(1, customer.getId());
	}
	@Test
	public void testSetId() throws FlightBookingSystemException{
		customer.setId(2);
		assertEquals(2, customer.getId());
	}
	@Test
	public void testGetName() throws FlightBookingSystemException{
		assertEquals("Bill Young", customer.getName());
	}
	@Test
	public void testSetName() throws FlightBookingSystemException{
		customer.setName("lilly booth");
		assertEquals("lilly booth", customer.getName());
	}
	@Test
	public void testGetPhone() throws FlightBookingSystemException{
		assertEquals("0789897898", customer.getPhone());
	}
	@Test
	public void testSetPhone() throws FlightBookingSystemException{
		customer.setPhone("01234567890");
		assertEquals("01234567890", customer.getPhone());
	}
	@Test
	public void testGetEmail() throws FlightBookingSystemException{
		assertEquals("bill@yahoo", customer.getEmail());
	}
	@Test
	public void testSetEmail() throws FlightBookingSystemException{
		customer.setEmail("lilly@mail");
		assertEquals("lilly@mail", customer.getEmail());
	}
	@Test
	public void testGetDetailsShortl() throws FlightBookingSystemException{
		String getDetailsShort = "Customer #" + customer.getId()  + customer.getName() + " - " + customer.getPhone() + " - " + customer.getEmail();
		assertEquals(getDetailsShort, customer.getDetailsShort());
	}
	@Test
	public void testGetDetailsLongWithBooking() throws FlightBookingSystemException{
		customer.addBooking(booking); //a booking to match the method
		
		StringBuilder expectedDetails = new StringBuilder();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/YYYY");
		expectedDetails.append("Customer #").append(customer.getId()).append("\n")
						.append("Name: ").append(customer.getName()).append("\n")
						.append("Phone: ").append(customer.getPhone()).append("\n")
						.append("Email: ").append(customer.getEmail()).append("\n")
						.append("---------------------------\n")
						.append("Bookings: \n")
						.append("* Booking date: ").append(LocalDate.now().format(dtf))
						.append(" For Flight # ").append(flight.getId())
						.append(" - ").append(flight.getFlightNumber())
						.append(" - ").append(flight.getOrigin())
						.append(" to ").append(flight.getDestination())
						.append(" on ").append(flight.getDepartureDate())
						.append("\nPrice: £").append(booking.getTotalPrice()).append("\n")
						.append("1 Booking(s)");
		
		String actualDetails = customer.getDetailsLong();
		assertEquals(expectedDetails.toString(), actualDetails);
	}
	@Test
	public void testAddBooking() throws FlightBookingSystemException {
		customer.addBooking(booking);
		assertEquals(1,customer.getBookings().size());
		//assertEquals(booking, customer.getBookings());
	}
	@Test
	public void testCancelBooking() throws FlightBookingSystemException {
		customer.addBooking(booking);
		customer.removeBooking(flight);
		assertEquals(0, customer.getBookings().size());
	}
	@Test(expected = FlightBookingSystemException.class)
	public void testAddBookingException() throws Exception {
		customer.addBooking(booking);
		customer.addBooking(booking);
	}
	@Test(expected = FlightBookingSystemException.class)
	public void testRemoveBookingException() throws Exception {
		customer.removeBooking(flight);
	}


 
}
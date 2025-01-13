package bcu.cmp5332.bookingsystem.Tests;
 
import static org.junit.Assert.*;
 
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
 
import org.junit.Test;
 
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
 
public class FlightTest {
	
	Flight flight = new Flight (3, "BN345", "Paris", "Italy", LocalDate.parse("2025-04-09"), 200, 89.90);
	Customer customer = new Customer(5, "Emma Briggs", "07094425898", "emma@mali.uk");
	
	@Test
	public void testGetId() throws FlightBookingSystemException{
		assertEquals(3, flight.getId());
	}
	
	@Test
	public void testSetId() throws FlightBookingSystemException{
		flight.setId(1);
		assertEquals(1, flight.getId());
	}
	
	@Test
	public void testGetFlightNumber() throws FlightBookingSystemException{
		assertEquals("BN345", flight.getFlightNumber());
	}
	
	@Test
	public void testSetFlightNumber() throws FlightBookingSystemException{
		flight.setFlightNumber("FK45");
		assertEquals("FK45", flight.getFlightNumber());
	}
	
	@Test
	public void testGetOrgin() throws FlightBookingSystemException{
		assertEquals("Paris", flight.getOrigin());
	}
	
	@Test
	public void testSetOrgin() throws FlightBookingSystemException{
		flight.setOrigin("Turkey");
		assertEquals("Turkey", flight.getOrigin());
	}
	
	@Test
	public void testGetDestination() throws FlightBookingSystemException{
		assertEquals("Italy", flight.getDestination());
	}
	
	@Test
	public void testSetDestination() throws FlightBookingSystemException{
		flight.setDestination("Greece");
		assertEquals("Greece", flight.getDestination());
	}
	
	@Test
	public void testGetDepartureDate() throws FlightBookingSystemException{
		assertEquals(LocalDate.parse("2025-04-09"), flight.getDepartureDate());
	}
	
	@Test
	public void testSetDepartureDate() throws FlightBookingSystemException{
		flight.setDepartureDate(LocalDate.parse("2025-09-23"));
		assertEquals(LocalDate.parse("2025-09-23"), flight.getDepartureDate());
	}
	
	@Test
	public void testGetCapacity() throws FlightBookingSystemException{
		assertEquals(200, flight.getCapacity());
	}
	
	@Test
	public void testSetCapacity() throws FlightBookingSystemException{
		flight.setCapacity(34);
		assertEquals(34, flight.getCapacity());
	}
	
	
	// 0.001 handles precisios issues - doubles are rounded to the nearest 64 bit
	//allows room for if the value stored is different in its decimal i.e 89.9999 or 89.90001 the test will still pass - sees it as thesame value then
	@Test
	public void testGetPrice() throws FlightBookingSystemException{
		assertEquals(89.90, flight.getPrice(), 0.001);
	}
	@Test
	public void testSetPrice() throws FlightBookingSystemException{
		flight.setPrice(55);
		assertEquals(55.00, flight.getPrice(), 0.001);
	}
	
	@Test
	public void testGetDetailsShort() throws FlightBookingSystemException{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String getDetailsShort = "Flight #"  +  flight.getId() + " - " + flight.getFlightNumber() + " - " +
	flight.getOrigin() + " to " + flight.getDestination() + " on " + flight.getDepartureDate().format(dtf) +
	" - Capacity of seats:  " + flight.getCapacity() + " - price: " + flight.getPrice();
		assertEquals(getDetailsShort, flight.getDetailsShort());
	}
	
	@Test
	public void testGetDetailsLongWithPassenger() throws FlightBookingSystemException{
		Flight flight = new Flight(1, "FL123", "London", "New York", LocalDate.of(2025, 1, 10), 2, 500.00);
		Customer customer = new Customer(1, "John Smith", "07345678910", "john.smith@mail.com");
		flight.addPassenger(customer);
		String detailsLong = flight.getDetailsLong();
		assertTrue(detailsLong.contains("John Smith"));
		assertTrue(detailsLong.contains("1 passenger(s)"));
	}
	
	
	@Test
	public void testAddPasengers() throws FlightBookingSystemException{
		flight.addPassenger(customer);
		assertTrue(flight.getPassengers().contains(customer));
	}
	
	@Test
	public void testRemovePasengers() throws FlightBookingSystemException{
		flight.addPassenger(customer);
		flight.removePassenger(customer);
		assertFalse(flight.getPassengers().contains(customer));
	}
	
	@Test(expected = FlightBookingSystemException.class)
	public void testAddPassengerException() throws Exception{
		flight.addPassenger(customer);
		flight.addPassenger(customer);
	}
	
	@Test(expected = FlightBookingSystemException.class)
	public void testRemovePassengerException() throws Exception {
		Customer fakeCustomer = new Customer(5, "Toby Parker", "0703083348", "parker@mali.uk");
		flight.removePassenger(fakeCustomer);
	}
	
	
	
	
 
}
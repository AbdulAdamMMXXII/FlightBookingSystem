package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;

public class MainWindow extends JFrame implements ActionListener {
	
	/** 
	 * The MainWindow class is the main page that displays the graphical user interface for the flight booking system
	 * it has a multiple menus through the JMenubar function which include adminMenu, flightMenu,bookingMenu,customerMenu
	 * each one of these menus have an item attached which will be used to carry out program code 
	 * so when a user clicks on a specific row a list of item will be presented
	 * mouseEvent is used to identify when a user has clicked onto a specific row of the table
	 * the data from the row is then used to identify the flight or customer
	 * to then diplay a popup box containing all the details of that specific flight or customer 
	 * 
	 */

    private JMenuBar menuBar;
    private JMenu adminMenu;
    private JMenu flightsMenu;
    private JMenu bookingsMenu;
    private JMenu customersMenu;

    private JMenuItem adminExit;

    private JMenuItem flightsView;
    private JMenuItem flightsAdd;
    private JMenuItem flightsDel;
    
    private JMenuItem bookingsIssue;
    private JMenuItem bookingsUpdate;
    private JMenuItem bookingsCancel;

    private JMenuItem custView;
    private JMenuItem custAdd;
    private JMenuItem custDel;
    
    private JMenuItem listPassengers;
    private JMenuItem bookingCustomer;

    private FlightBookingSystem fbs;

    public MainWindow(FlightBookingSystem fbs) {

        initialize();
        this.fbs = fbs;
    }
    
    public FlightBookingSystem getFlightBookingSystem() {
        return fbs;
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {

        }

        setTitle("Flight Booking Management System");

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        //adding adminMenu menu and menu items
        adminMenu = new JMenu("Admin");
        menuBar.add(adminMenu);

        adminExit = new JMenuItem("Exit");
        adminMenu.add(adminExit);
        adminExit.addActionListener(this);

        // adding Flights menu and menu items
        flightsMenu = new JMenu("Flights");
        menuBar.add(flightsMenu);

        flightsView = new JMenuItem("View");
        flightsAdd = new JMenuItem("Add");
        flightsDel = new JMenuItem("Delete");
        flightsMenu.add(flightsView);
        flightsMenu.add(flightsAdd);
        flightsMenu.add(flightsDel);
        // adding action listener for Flights menu items
        for (int i = 0; i < flightsMenu.getItemCount(); i++) {
            flightsMenu.getItem(i).addActionListener(this);
        }
        
        // adding Bookings menu and menu items
        bookingsMenu = new JMenu("Bookings");
        menuBar.add(bookingsMenu);
        
        bookingsIssue = new JMenuItem("Issue");
        bookingsUpdate = new JMenuItem("Update");
        bookingsCancel = new JMenuItem("Cancel");
        bookingsMenu.add(bookingsIssue);
        bookingsMenu.add(bookingsUpdate);
        bookingsMenu.add(bookingsCancel);
        // adding action listener for Bookings menu items
        for (int i = 0; i < bookingsMenu.getItemCount(); i++) {
            bookingsMenu.getItem(i).addActionListener(this);
        }
        
        bookingsIssue.addActionListener(this);
        bookingsUpdate.addActionListener(this);
        bookingsCancel.addActionListener(this);


        // adding Customers menu and menu items
        customersMenu = new JMenu("Customers");
        menuBar.add(customersMenu);

        custView = new JMenuItem("View");
        custAdd = new JMenuItem("Add");
        custDel = new JMenuItem("Delete");

        customersMenu.add(custView);
        customersMenu.add(custAdd);
        customersMenu.add(custDel);
        // adding action listener for Customers menu items
        custView.addActionListener(this);
        custAdd.addActionListener(this);
        custDel.addActionListener(this);

        setSize(800, 500);

        setVisible(true);
        setAutoRequestFocus(true);
        toFront();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
/* Uncomment the following line to not terminate the console app when the window is closed */
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);        

    }	

/* Uncomment the following code to run the GUI version directly from the IDE */
    public static void main(String[] args) throws IOException, FlightBookingSystemException {
        FlightBookingSystem fbs = FlightBookingSystemData.load();
        new MainWindow(fbs);			
    }



    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == adminExit) {
            try {
                FlightBookingSystemData.store(fbs);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
            }
            System.exit(0);
        } else if (ae.getSource() == flightsView) {
            displayFlights();
            
        } else if (ae.getSource() == flightsAdd) {
            new AddFlightWindow(this);
            
        } else if (ae.getSource() == flightsDel) {
            new RemoveFlightWindow(this);
            
        } else if (ae.getSource() == bookingsIssue) {
        	new IssueBookingWindow(this);
            
            
        } else if (ae.getSource() == bookingsUpdate) {
            new UpdateBookingWindow(this);
            
        } else if (ae.getSource() == bookingsCancel) {
            new CancelBookingWindow(this);
            
        }else if (ae.getSource() == custView) {
        	displayCustomers();
            
        } else if (ae.getSource() == custAdd) {
            new AddCustomerWindow(this);
            
        } else if (ae.getSource() == custDel) {
            new RemoveCustomerWindow(this);
            
        } else if (ae.getSource() == listPassengers) {
        	displayFlightPassengers(null);
        } else if (ae.getSource() == bookingCustomer ) {
        	displayCustomerBookings(null);
        }
            
        
    }

    public void displayFlights() {
        List<Flight> flightsList = fbs.getFlights();
        // headers for the table
        String[] columns = new String[]{"Flight No", "Origin", "Destination", "Departure Date"};

        Object[][] data = new Object[flightsList.size()][6];
        for (int i = 0; i < flightsList.size(); i++) {
            Flight flight = flightsList.get(i);
            data[i][0] = flight.getFlightNumber();
            data[i][1] = flight.getOrigin();
            data[i][2] = flight.getDestination();
            data[i][3] = flight.getDepartureDate();
        }

        JTable table = new JTable(data, columns);
        table.addMouseListener(new MouseAdapter(){
        	public void mouseClicked(MouseEvent e) {
        		if (e.getClickCount() == 1) {
        			int row = table.getSelectedRow();
        			if (row >= 0) {
        				Flight selectedFlight = flightsList.get(row);
        				displayFlightPassengers(selectedFlight);
        				List<Customer> passengerList = selectedFlight.getPassengers();
        				System.out.println("Passenger list size for flight " + selectedFlight.getFlightNumber() + ": " + passengerList.size());
        			}
        		}
        	}
        });
        this.getContentPane().removeAll();
        this.getContentPane().add(new JScrollPane(table));
        this.revalidate();
    }	
    
    public void displayFlightPassengers(Flight flight) {
   	 if (flight == null) {
   	        // Handle error for null flight or show a message
   	        JOptionPane.showMessageDialog(this, "Flight data is missing.", "Error", JOptionPane.ERROR_MESSAGE);
   	        return;
   	    }

   	    List<Customer> passengerList = flight.getPassengers();
   	    //List<Customer> passengerList = flight.getPassengers();
   	    System.out.println("Passenger list size: " + passengerList.size()); // Debugging
   	    
   	    if (passengerList == null || passengerList.isEmpty()) {
   	        JOptionPane.showMessageDialog(this, "No other passengers found for this flight.", "No Passengers", JOptionPane.INFORMATION_MESSAGE);
   	        return;
   	    }
       // Debugging: Print passengers to the console
       
   	String[] columns = new String[]{"Name", "Phone", "Email"};
   	//List<Customer> passengerList = flight.getPassengers();

       Object[][] data = new Object[passengerList.size()][4];
       for (int i = 0; i < passengerList.size(); i++) {
           Customer customers = passengerList.get(i);
           data[i][0] = customers.getName();
           data[i][1] = customers.getPhone();
           data[i][2] = customers.getEmail();
       }

       JTable table = new JTable(data, columns);
       JScrollPane scrollPane = new JScrollPane(table);
       
       JFrame passengersListFrame = new JFrame("Passengers for Flight " + flight.getFlightNumber());
       passengersListFrame.add(scrollPane);
       passengersListFrame.setSize(600,400);
       passengersListFrame.setLocationRelativeTo(this);
       passengersListFrame.setVisible(true);
       
   }
   	
   
   
   public void displayCustomers() {
       List<Customer> customerList = fbs.getCustomers();
       // headers for the table
       String[] columns = new String[]{"Name", "Phone", "Email", "No. of Bookings"};

       Object[][] data = new Object[customerList.size()][4];
       for (int i = 0; i < customerList.size(); i++) {
           Customer customer = customerList.get(i);
           data[i][0] = customer.getName();
           data[i][1] = customer.getPhone();
           data[i][2] = customer.getEmail();
           data[i][3] = customer.getBookings().size();
       }

       JTable table = new JTable(data, columns);
       table.addMouseListener(new MouseAdapter(){
       	public void mouseClicked(MouseEvent e) {
       		if (e.getClickCount() == 1) {
       			int row = table.getSelectedRow();
       			if (row >= 0) {
       				Customer selectedCustomer = customerList.get(row);
       				displayCustomerBookings(selectedCustomer);
       				List<Booking> bookingList = selectedCustomer.getBookings();
       				System.out.println("Passenger list size for flight " + selectedCustomer.getName() + ": " + bookingList.size());
       			}
       		}
       	}
       });
       this.getContentPane().removeAll();
       this.getContentPane().add(new JScrollPane(table));
       this.revalidate();
   }
   
   public void displayCustomerBookings(Customer customerBooking) {
	   	 
	   if (customerBooking == null) {
	   	        // Handle error for null flight or show a message
	   	        JOptionPane.showMessageDialog(this, "Customer data is missing.", "Error", JOptionPane.ERROR_MESSAGE);
	   	        return;
	   	    }

	   	    List<Booking> bookingList = customerBooking.getBookings();
	   	    //List<Customer> passengerList = flight.getPassengers();
	   	    System.out.println("Passenger list size: " + bookingList.size()); // Debugging
	   	    
	   	    if (bookingList == null || bookingList.isEmpty()) {
	   	        JOptionPane.showMessageDialog(this, "No booking found for this flight.", "No bookings", JOptionPane.INFORMATION_MESSAGE);
	   	        return;
	   	    }
	   
       // headers for the table
       String[] columns = new String[]{"Customer Id", "Flight Id", "Booking Date"};

       Object[][] data = new Object[bookingList.size()][3];
       for (int i = 0; i < bookingList.size(); i++) {
           Booking booking  = bookingList.get(i);
           data[i][0] = booking.getCustomer().getId();
           data[i][1] = booking.getFlight().getId();
           data[i][2] = booking.getBookingDate();
       }
       
       JTable table = new JTable(data, columns);
       JScrollPane scrollPane = new JScrollPane(table);
       
       JFrame bookingListFrame = new JFrame("Bookings for Customer " + customerBooking.getName());
       bookingListFrame.add(scrollPane);
       bookingListFrame.setSize(600,400);
       bookingListFrame.setLocationRelativeTo(this);
       bookingListFrame.setVisible(true);
       
   }
   

}

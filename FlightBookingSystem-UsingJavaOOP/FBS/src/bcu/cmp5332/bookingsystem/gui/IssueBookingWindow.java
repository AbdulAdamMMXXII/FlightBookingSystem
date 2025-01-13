package bcu.cmp5332.bookingsystem.gui;


import bcu.cmp5332.bookingsystem.commands.AddBooking;
import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class IssueBookingWindow extends JFrame implements ActionListener {
	
	private MainWindow mw;
    private JTextField customerIdText = new JTextField();
    private JTextField flightIdText = new JTextField();
    private JTextField bookingDateText = new JTextField();
    
    private JButton addBtn = new JButton("Add");
    private JButton cancelBtn = new JButton("Cancel");

    public IssueBookingWindow(MainWindow mw) {
        this.mw = mw;
        initialize();
    }
    
    private void initialize() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {

        }

        setTitle("Add a New Flight");

        setSize(350, 220);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(5, 2));
        topPanel.add(new JLabel("Customer ID : "));
        topPanel.add(customerIdText);
        topPanel.add(new JLabel("Flight ID : "));
        topPanel.add(flightIdText);
        topPanel.add(bookingDateText);
        topPanel.add(new JLabel("Departure Date (YYYY-MM-DD) : "));
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 3));
        bottomPanel.add(new JLabel("     "));
        bottomPanel.add(addBtn);
        bottomPanel.add(cancelBtn);

        addBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        this.getContentPane().add(topPanel, BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(mw);

        setVisible(true);
      
    }
	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == addBtn) {
            issueBooking();
        } else if (ae.getSource() == cancelBtn) {
            this.setVisible(false);
        }
	}
	
	private void issueBooking() {
		try {
			int customerId = Integer.parseInt(customerIdText.getText());
	        int flightId = Integer.parseInt(flightIdText.getText());
	        LocalDate bookingDate = null;
	        
	        try {
	            bookingDate = LocalDate.parse(bookingDateText.getText());
	        }
	        catch (DateTimeParseException dtpe) {
	            throw new FlightBookingSystemException("Date must be in YYYY-DD-MM format");
	        }
	        // create and execute the AddFlight Command
	        Command addBooking = new AddBooking(customerId, flightId, bookingDate);
	        addBooking.execute(mw.getFlightBookingSystem());
	        // refresh the view with the list of flights
	        mw.displayFlights();
	        // hide (close) the AddFlightWindow
	        this.setVisible(false);
	    } catch (FlightBookingSystemException ex) {
	        JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
}

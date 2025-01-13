package bcu.cmp5332.bookingsystem.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.commands.RemoveFlight;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;

public class UpdateBookingWindow extends JFrame implements ActionListener {
	private MainWindow mw;
	 private JTextField bookingIdText = new JTextField();
    private JTextField flightIdText = new JTextField();
    
    private JButton addBtn = new JButton("Update");
    private JButton cancelBtn = new JButton("Cancel");

    public UpdateBookingWindow(MainWindow mw) {
        this.mw = mw;
        initialize();
    }
    
    private void initialize() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {

        }

        setTitle("RUpdate Booking");

        setSize(350, 220);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(5, 2));
        topPanel.add(new JLabel("Booking Id : "));
        topPanel.add(bookingIdText);
        topPanel.add(new JLabel("Flight Id : "));
        topPanel.add(flightIdText);
        
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
            updateBooking();
        } else if (ae.getSource() == cancelBtn) {
            this.setVisible(false);
        }

    }
    
    private void updateBooking() {
    	try {
    		int bookingId = Integer.parseInt(bookingIdText.getText());
    		int flightId = Integer.parseInt(flightIdText.getText());
    		Command updateBooking = new RemoveFlight(flightId);
    		updateBooking.execute(mw.getFlightBookingSystem());
    		JOptionPane.showMessageDialog(this, bookingId, " Has successfully been removed.", JOptionPane.INFORMATION_MESSAGE);
    		this.setVisible(false);
            // refresh the view with the list of flights
            mw.displayFlights();
    	} catch (NumberFormatException ex) {
    		JOptionPane.showMessageDialog(this, ex, "Please enter a number ", JOptionPane.ERROR_MESSAGE);
    	}catch (FlightBookingSystemException ex) {
            JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    		

        
    }

}

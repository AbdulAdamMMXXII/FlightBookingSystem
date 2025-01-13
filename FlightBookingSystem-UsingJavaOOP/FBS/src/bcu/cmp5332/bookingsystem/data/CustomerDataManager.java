package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class CustomerDataManager implements DataManager {

//implements add customer and get customer new in these 
//fbs.addcuystomer(
    private final String RESOURCE = "./resources/data/customers.txt";
    
    @Override
    public void loadData(FlightBookingSystem fbs) throws IOException, FlightBookingSystemException {
        // TODO: implementation here
         try (Scanner sc = new Scanner(new File(RESOURCE))) {
             int line_idx = 1;
             while (sc.hasNextLine()) {
                 String line = sc.nextLine();
                 String[] properties = line.split(SEPARATOR, -1);//separates string into parts
                 try {
                	 //assigns different parts of the string into different variables 
                     int id = Integer.parseInt(properties[0]);
                     String name = properties[1];
                     String phone = properties[2];
                     String email = properties[3];
                     boolean isRemoved = Boolean.parseBoolean(properties[4]);
                     
                     Customer customer = new Customer(id, name, phone, email);
                     customer.setRemoved(isRemoved); //load removed status
                     fbs.addCustomer(customer);  //add customer
                 } catch (NumberFormatException ex) {
                     throw new FlightBookingSystemException("Unable to parse book id " + properties[0] + " on line " + line_idx
                         + "\nError: " + ex);
                 }
                 line_idx++;
             }
         }
    }

    @Override
    public void storeData(FlightBookingSystem fbs) throws IOException {
        // TODO: implementation here
        try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
            for (Customer customer : fbs.getCustomers()) {
                out.print(customer.getId() + SEPARATOR);
                out.print(customer.getName()+ SEPARATOR);
                out.print(customer.getPhone() + SEPARATOR);
                out.print(customer.getEmail() + SEPARATOR);
                out.print(customer.isRemoved() + SEPARATOR);
                out.println();
            }
        }
    }
}

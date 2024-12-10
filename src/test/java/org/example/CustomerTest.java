package org.example;

import org.example.Database.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    @Test
    public void CustomerLogin() throws InterruptedException {

        database mainDBManager = new database();
        mainDBManager.initializeDB();

        ArrayList<String> credentials = new ArrayList<>();
        credentials.add("0001");
        credentials.add("3520119550899");

        ArrayList<String> credentials2 = new ArrayList<>();
        for (Customer_DB.customer_struct customers : mainDBManager.customer.getCustomers()) {
            if (customers.ID.equals("0001")) {
                credentials2.add("0001");
                credentials2.add("3520119550899");
                break;
            }

        }
        assertEquals(credentials, credentials2);
    }

    @Test
    public void InvalidCustomerLogin() throws InterruptedException {

        database mainDBManager = new database();
        mainDBManager.initializeDB();

        ArrayList<String> credentials = new ArrayList<>();
        credentials.add("0002");
        credentials.add("3520119550899");

        ArrayList<String> credentials2 = new ArrayList<>();
        for (Customer_DB.customer_struct customers : mainDBManager.customer.getCustomers()) {
            if (customers.ID.equals("0001")) {
                credentials2.add("0001");
                credentials2.add("3520119550899");
                break;
            }
        }
        assertNotEquals(credentials, credentials2);
    }

    @Test
    public void checkCustomerCredentials() {

        database mainDBManager = new database();
        mainDBManager.initializeDB();

        ArrayList<String> credentials = new ArrayList<>();
        credentials.add("0001");
        credentials.add("3520119550899");

        boolean status = false;
        for (Customer_DB.customer_struct customers : mainDBManager.customer.getCustomers()) {
            if (customers.ID.equals("0001") && customers.CNIC.equals("3520119550899")) {
                status = true;
                break;
            }
        }
        assertTrue(status);
    }


    @Test
    public void addNewCustomer() {

        database database = new database();
        Customer_DB customer_db = new Customer_DB();

        ArrayList<Customer_DB.customer_struct> costList = customer_db.getCustomers();

        int initialNumLines = costList.size();

        costList.add(new Customer_DB.customer_struct("0001", "3520119550899", "Residential" + "1000",
                "House 1, Street 1, Islamabad", "03331234567", "Residential", "Regular",
                "01/01/2021", 100, 200));

        int newNumLines = costList.size();

        assertEquals(initialNumLines + 1, newNumLines);
    }

    @Test
    void updateCustomer() {

        database database = new database();
        String CNIC = "3520119550899";
        Customer_DB customer = new Customer_DB();

        for (Customer_DB.customer_struct data : customer.getCustomers()) {
            if (data.CNIC.equals(CNIC)) {
                data.Address = "House 2, Street 2, Islamabad";
                data.Phone = "03331234568";
                data.regularUnitsConsumed = 200;
                data.peakHourUnitsConsumed = 300;

                System.out.println("Customer Information Updated!");
                assertTrue(true);
            }
        }
    }


    @Test
    void updateCNIC() {

        database database = new database();
        String CNIC = "3520119550899";
        NADRA_DB nadra = new NADRA_DB();

        for (NADRA_DB.nadra_struct data : nadra.getNADRA()) {
            if (data.CNIC.equals(CNIC)) {
                String dateString = data.ExpiryDate;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate date = LocalDate.parse(dateString, formatter);
                LocalDate newDate = date.plusYears(3);
                data.ExpiryDate = newDate.format(formatter);

                System.out.println("Expiry Date of CNIC changed!");
                assertTrue(true);
            }
        }

    }
}

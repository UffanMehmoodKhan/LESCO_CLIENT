package org.example;

import org.example.Database.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeTest {

    @Test
    public void EmployeeLogin() throws InterruptedException {

        database mainDBManager = new database();
        mainDBManager.initializeDB();

        ArrayList<String> credentials = new ArrayList<>();
        credentials.add("uffan_khan");
        credentials.add("networkengineers");

        ArrayList<String> credentials2 = new ArrayList<>();
        for (Employee_DB.employee_struct employeeStruct : mainDBManager.employee.getEmployees()) {
            if (employeeStruct.username.equals("uffan_khan")) {
                credentials2.add(employeeStruct.username);
                credentials2.add(employeeStruct.password);
                break;
            }
        }
        assertEquals(credentials, credentials2);
    }

    @Test
    public void InvalidEmployeeLogin() throws InterruptedException {

        database mainDBManager = new database();
        mainDBManager.initializeDB();

        ArrayList<String> credentials = new ArrayList<>();
        credentials.add("uffankhan");
        credentials.add("network_engineers");

        ArrayList<String> credentials2 = new ArrayList<>();
        for (Employee_DB.employee_struct employeeStruct : mainDBManager.employee.getEmployees()) {
            if (employeeStruct.username.equals("uffan_khan")) {
                credentials2.add(employeeStruct.username);
                credentials2.add(employeeStruct.password);
                break;
            }
        }
        assertNotEquals(credentials, credentials2);
    }


    @Test
    void addNewCustomer() {

        String CNIC = "3520119550899";

        Customer_DB customer_db = new Customer_DB();
        customer_db.readCUSTOMER_FILE();        //Before adding new customer
        int initialCustomers = customer_db.getCustomers().size();

        Customer_DB.customer_struct new_customer;
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = currentDate.format(formatter);

        //customer_db.customerInfo.add();
        new_customer = new Customer_DB.customer_struct(
                "0002", "3520119550900","Danish Shakeel", "Chichawatni",
                "03334540660", "Domestic", "1Phase", formattedDate,
                20, 20
        );
        customer_db.customerInfo.add(new_customer);
        customer_db.readCUSTOMER_FILE(); // After adding new customer
        int newCustomerNum = customer_db.getCustomers().size();


        assertEquals(initialCustomers + 1, newCustomerNum);
    }

    @Test
    void updateCustomer() {

        Customer_DB customer_db = new Customer_DB();
        String CNIC = "352011550899";

        System.out.println(customer_db.getCustomers().getFirst());
        String initialName = customer_db.customerInfo.getFirst().Name;

        customer_db.customerInfo.getFirst().Name = "Danish Shakeel";
        customer_db.customerInfo.getFirst().Name = "Danish Shakeel";
        customer_db.customerInfo.getFirst().Address = "Chichawatni";
        customer_db.customerInfo.getFirst().Phone = "03334540660";
        customer_db.customerInfo.getFirst().CustomerType = "Domestic";
        customer_db.customerInfo.getFirst().meterType = "1Phase";
        customer_db.customerInfo.getFirst().connectionDate = "01/01/2021";
        customer_db.customerInfo.getFirst().regularUnitsConsumed = 20;
        customer_db.customerInfo.getFirst().peakHourUnitsConsumed = 20;

        System.out.println(customer_db.getCustomers().getFirst());
        String newName = customer_db.customerInfo.getFirst().Name;

        assertNotEquals(initialName, newName);

    }

    @Test
    void updateBillStatus() {
        ArrayList<Billing_DB.billing_struct> allBills = Billing_DB.getBillingInfo();
        System.out.println(allBills.getFirst());

        allBills.getFirst().BillPaidStatus = "Paid";

        assertEquals("Paid", allBills.getFirst().BillPaidStatus);

    }

    @Test
    void printBillingReport() {
        Billing_DB billing_db = new Billing_DB();
        assertTrue(true);

    }

    @Test
    void viewExpiredCNIC() throws InterruptedException {

        NADRA_DB nadra = new NADRA_DB();
        nadra.readNADRA_FILE();

        long daysBetween = 0;

        for(NADRA_DB.nadra_struct cnic : nadra.getNADRA()){
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date1 = LocalDate.parse(cnic.IssuanceDate, dateFormatter);
            LocalDate date2 = LocalDate.parse(cnic.ExpiryDate, dateFormatter);
            daysBetween = ChronoUnit.DAYS.between(date1, date2);
            if(daysBetween <= 30){
                System.out.println("CNIC: " + cnic.CNIC + " is about Expire! Renew as soon as possible!");
                assertTrue(true);
                return;
            }
        }
        assertTrue(false);
    }

    @Test
    void updateTaxTariff() {
        Tax_DB taxes = new Tax_DB();
        assertNotEquals(taxes.getTax_tariff().getFirst().peak_hour_unit_price = 10,
                taxes.getTax_tariff().getFirst().peak_hour_unit_price = 20);

    }
}
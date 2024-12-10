package org.example.Session;

import org.example.Database.*;
import org.example.Screens.CustomerBillDetailsScreen;
import org.example.UI.CustomerUI;
import org.example.UI.UserInterface;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Customer implements Session {

    database database;
    UserInterface userInterface;

    public Customer(database mainDBManager) throws InterruptedException {
        System.out.print("Customer Session created");
        userInterface = new CustomerUI();
        this.database = mainDBManager;
        this.StartSession();
    }

    @Override
    public void StartSession() throws InterruptedException {

        ArrayList<String> credentials = userInterface.LoginScreen();
        boolean status = Login(credentials);    //Check given admin login credentials
        if (status) {
            System.out.println("Customer Login Successful");
            //Thread.sleep(3000);

            //Main Screen
            int choice = userInterface.InfoTerminal();

            if (choice == 1) {
                credentials.add(userInterface.CustomerBillTerminal().getFirst());
                viewBill(credentials);
            }
            else if (choice == 2) {
                updateCNIC(credentials.get(1));
            }

            else{
                // NotImplemented
            }

        }
        else{
            System.out.println("Invalid Login");
            throw new InterruptedException();
        }
    }

    //Login
    @Override
    public boolean Login(ArrayList<String> credentials){
        return checkloginCredential(credentials);
    }

    @Override
    public boolean checkloginCredential(ArrayList<String> credentials) {

        String username = credentials.getFirst();
        String password = credentials.getLast();


        for (Customer_DB.customer_struct customerStruct: database.customer.getCustomers()) {
            if (customerStruct.ID.equals(username) && customerStruct.CNIC.equals(password)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addNewCustomer(String CNIC) {
        // NotImplemented
    }

    @Override
    public void updateCustomer(String CNIC) {
        // NotImplemented
    }

    public void viewBill(ArrayList<String> data) throws InterruptedException {
        String ID = data.get(0);
        String CNIC = data.get(1);
        String meterType = data.get(2);

        ArrayList<String> billDetails = new ArrayList<>();

        Tax_DB.tax_tariff_struct D_1 = database.tax.getTax_tariff().getFirst();
        Tax_DB.tax_tariff_struct C_1 = database.tax.getTax_tariff().get(1);
        Tax_DB.tax_tariff_struct D_3 = database.tax.getTax_tariff().get(2);
        Tax_DB.tax_tariff_struct C_3 = database.tax.getTax_tariff().getLast();

        for (Billing_DB.billing_struct bills : Billing_DB.getBillingInfo()) {
            if (bills.ID.equals(ID)) {
                bills.totalBillingAmount = (Integer.parseInt(bills.currentMeterReading) * bills.costOfElectricity) + bills.fixedCharges;
                for (Customer_DB.customer_struct customer : database.customer.getCustomers()) {
                    if (customer.CNIC.equals(CNIC)) {
                        billDetails.add("ID: " + customer.ID);
                        billDetails.add("Name: " + customer.Name);
                        billDetails.add("Phone: " + customer.Phone);
                        billDetails.add("Address: " + customer.Address);
                        billDetails.add("Customer Type: " + customer.CustomerType);
                        billDetails.add("Meter Type: " + customer.meterType);

                        if (customer.CustomerType.equals("Domestic")) {
                            if (customer.meterType.equals("1Phase")) {
                                billDetails.add("Regular Unit Price: Rs" + D_1.peak_hour_unit_price);
                                billDetails.add("Tax Percentage Cost: " + (bills.totalBillingAmount / (Float.parseFloat(String.valueOf(D_1.tax_percentage)))));
                                billDetails.add("Fixed Charges: Rs" + D_1.fixed_charges);
                            } else if (meterType.equals("3Phase")) {
                                billDetails.add("Regular Unit Price: Rs" + D_3.peak_hour_unit_price);
                                billDetails.add("Tax Percentage Cost: Rs" + (bills.totalBillingAmount / ((Float.parseFloat(String.valueOf(D_3.tax_percentage))) / 100)));
                                billDetails.add("Fixed Charges: Rs" + D_3.fixed_charges);
                                billDetails.add("Peak Hour Price: Rs" + D_3.peak_hour_unit_price);
                            }
                        } else if (customer.CustomerType.equals("Commercial")) {
                            if (meterType.equals("1Phase")) {
                                billDetails.add("Regular Unit Price: Rs" + C_1.peak_hour_unit_price);
                                billDetails.add("Tax Percentage: " + (bills.totalBillingAmount / (Float.parseFloat(String.valueOf(C_1.tax_percentage)) / 100)));
                                billDetails.add("Fixed Charges: Rs" + C_1.fixed_charges);
                            } else if (meterType.equals("3Phase")) {
                                billDetails.add("Regular Unit Price: Rs" + C_3.peak_hour_unit_price);
                                billDetails.add("Tax Percentage: %" + (bills.totalBillingAmount / (Float.parseFloat(String.valueOf(C_3.tax_percentage)) / 100)));
                                billDetails.add("Fixed Charges: Rs" + C_3.fixed_charges);
                                billDetails.add("Peak Hour Price: Rs" + C_3.peak_hour_unit_price);
                            }
                        }
                        billDetails.add("Total Amount: " + bills.totalBillingAmount);
                        billDetails.add("Due Date: " + bills.dueDate);
                        new CustomerBillDetailsScreen(billDetails);
                        return;
                    }
                }
            }
        }

        System.out.println("Something is broken!");
        Thread.sleep(1000);
    }

    public void updateCNIC(String CNIC) {
        for (NADRA_DB.nadra_struct data : database.nadra.getNADRA()) {
            if (data.CNIC.equals(CNIC)) {
                String dateString = data.ExpiryDate;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate date = LocalDate.parse(dateString, formatter);
                LocalDate newDate = date.plusYears(3);
                data.ExpiryDate = newDate.format(formatter);

                System.out.println("Expiry Date of CNIC changed!");
                JOptionPane.showMessageDialog(null, "CNIC has been updated to " + data.ExpiryDate, "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
        System.out.println("CNIC NOT FOUND!");
    }
}

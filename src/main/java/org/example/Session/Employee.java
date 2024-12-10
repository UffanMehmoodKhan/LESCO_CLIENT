package org.example.Session;

import org.example.Database.*;
import org.example.Screens.UpdateTariffScreen;
import org.example.UI.EmployeeUI;
import org.example.UI.UserInterface;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

public class Employee implements Session{

    database database;
    UserInterface userInterface;
    public Employee(database mainDBManager) throws InterruptedException {
        System.out.print("Employee Session created");
        userInterface = new EmployeeUI();
        this.database = mainDBManager;
        this.StartSession();
    }

    @Override
    public void StartSession() throws InterruptedException {
        boolean status = Login(userInterface.LoginScreen());    //Check given admin login credentials
        if (status) {
            System.out.println("Employee Login Successful");

            // Employee Terminal
            int choice = userInterface.InfoTerminal();

            // Add Customer
            if (choice == 1) {
                ArrayList<Customer_DB.customer_struct> fullDetails = database.customer.customerInfo;
                int result = userInterface.EmployeeInfoTerminal(fullDetails);

//                if (employee_choice == 1) {
//                    String customer_CNIC = userInterface.CustomerAddTerminal(); // CNIC of new potential meter
//                    boolean valid_customer = (database.customer.tripleCheck(customer_CNIC) && database.nadra.isNADRA(customer_CNIC)); // Check Valid CNIC
//                    if (valid_customer) {   // If valid CNIC
//                        addNewCustomer(customer_CNIC);  // Add New Customer
//                    }
//                    else{
//                        System.out.println("\nInvalid CNIC OR Maximum 3 limit exceeded.\n");
//                    }
//
//                }
//                else if (employee_choice == 2) {
//                    String customer_CNIC = userInterface.CustomerUpdateTerminal();
//                    boolean valid_customer = database.nadra.isNADRA(customer_CNIC);
//                    if (valid_customer) {
//                        updateCustomer(customer_CNIC);
//                    }
//                    else{
//                        System.out.println("\nInvalid CNIC.\n");
//
//                    }
//                }
            }

            // Update Customer
            else if (choice == 2) {
                //int employee_choice = userInterface.BillingCustomerTerminal();
                ArrayList<Billing_DB.billing_struct> fullDetails = Billing_DB.getBillingInfo();
                int result = this.userInterface.viewBillingReport(fullDetails);
//                if(employee_choice == 1){
//                    generateBillingReport();
//                }
//                else if(employee_choice == 2){
//                    String update_CNIC = userInterface.updateBillingTerminal();
//                    boolean valid_CNIC =  database.nadra.isNADRA(update_CNIC);
//                    if (valid_CNIC) {
//                        this.updateBillStatus(update_CNIC);
//                    }
//                    else{
//                        System.out.println("\nInvalid CNIC\n");
//                    }
//
//                }
//                else if(employee_choice == 3){
//                    this.printBillingReport(userInterface.viewBillingReport());
//                }
//                else if(employee_choice == 4){
//                    this.userInterface.BillingCNICTerminal();
//                    this.viewExpiredCNIC();
//                }
            }
            else if(choice == 3){
                updateTaxTariff();

            }
        }

        else{
            System.out.println("Invalid Login or Operation");
            throw new InterruptedException();
        }
    }

    //Login
    @Override
    public boolean Login(ArrayList<String> credentials){
        return checkloginCredential(credentials);
    }

    //Check Login Credentials
    @Override
    public boolean checkloginCredential(ArrayList<String> credentials) {

        for (Employee_DB.employee_struct employeeStruct: database.employee.getEmployees()){
            if (employeeStruct.username.equals(credentials.get(0)) && employeeStruct.password.equals(credentials.get(1))){
                return true;
            }
        }
        return false;
    }

    @Override
    public void addNewCustomer(String CNIC){

        Scanner sc = new Scanner(System.in);
        Customer_DB.customer_struct new_customer;
        for(Customer_DB.customer_struct customer : database.customer.getCustomers()){
            if(customer.CNIC.equals(CNIC)){

                LocalDate currentDate = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String formattedDate = currentDate.format(formatter);

                int regularUnitsConsumed = 0, peakHourUnitsConsumed = 0;

                System.out.println("\n Enter Customer Type [Domestic | Commercial]: "); String CustomerType = sc.nextLine();
                System.out.println("\n Enter Meter Type [1Phase | 3Phase]: "); String meter_type = sc.nextLine();
//                if(meter_type.equals("3Phase")){
//                    System.out.println(database.tax.getTax_tariff().getFirst().toString());
//                    peakHourUnitsConsumed = CustomerType.equals("Domestic") ? 12 : 25;
//                    regularUnitsConsumed = CustomerType.equals("Domestic") ? 5 : 15;
//                }
//                else{
//                    regularUnitsConsumed = CustomerType.equals("Domestic") ? 12 : 15;
//                }

                new_customer = new Customer_DB.customer_struct(
                        customer.ID, customer.CNIC, customer.Name, customer.Address,
                        customer.Phone, CustomerType, meter_type, formattedDate,
                        regularUnitsConsumed, peakHourUnitsConsumed
                );
                database.customer.getCustomers().add(new_customer);
                return;
            }
        }

    }

    @Override
    public void updateCustomer(String CNIC) throws InterruptedException {

        int choice, counter, CNIC_num = 0;
        Scanner sc = new Scanner(System.in);

        for (Customer_DB.customer_struct customer : database.customer.getCustomers()){
            if(customer.CNIC.equals(CNIC)){
                CNIC_num++;
                System.out.println("[" + CNIC_num + "] " + customer.toString());
            }
        }
        System.out.println("\nCNIC has " + CNIC_num + " meters attached: ");
        System.out.println("Enter which meter to edit: "); choice = sc.nextInt();

        counter = 1;
        for (Customer_DB.customer_struct customer : database.customer.getCustomers()){
            if(customer.CNIC.equals(CNIC) && choice == counter++){
                System.out.println("Change Address"); customer.Address = sc.nextLine();
                System.out.println("Change Phone"); customer.Phone = sc.nextLine();
                System.out.println("Change Customer Type [Domestic | Commercial]"); customer.CustomerType = sc.nextLine();
                System.out.println("Change Meter Type: "); customer.meterType = sc.nextLine();

                if(customer.meterType.equals("3Phase")){
                    customer.peakHourUnitsConsumed = customer.CustomerType.equals("Domestic") ? 12 : 25;
                    customer.regularUnitsConsumed = customer.CustomerType.equals("Domestic") ? 5 : 15;
                }
                else{
                    customer.regularUnitsConsumed = customer.CustomerType.equals("Domestic") ? 12 : 15;
                }
                System.out.println("Updated");
                return;
            }
        }

    }

    private void generateBillingReport() throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        String ID;
        System.out.println("\nEnter ID to view Bill: "); ID = sc.nextLine();

        for(Billing_DB.billing_struct bill : Billing_DB.getBillingInfo()){
            if (bill.ID.equals(ID)){
                System.out.println(bill.toString());
            }
        }
    }

    public void updateBillStatus(String CNIC) throws InterruptedException {

        String ID = database.customer.returnID(CNIC);
        if(ID == null){
            System.out.println("ID NOT FOUND!");
            return;
        }
        for(Billing_DB.billing_struct bill : Billing_DB.getBillingInfo()){
            if(bill.ID.equals(ID) && bill.BillPaidStatus.equals("Unpaid")){
                LocalDate currentDate = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String formattedDate = currentDate.format(formatter);
                bill.BillPaidStatus = "Paid";
                bill.BillPaymentDate = formattedDate;

                System.out.println("Bill Status Updated!");

                return;
            }
        }
    }

    public void printBillingReport(int choice) throws InterruptedException {

        int bill_num = 0;
        if (choice == 1){
            for (Billing_DB.billing_struct bill : Billing_DB.getBillingInfo()){
                if (bill.BillPaidStatus.equals("Paid")){
                   bill_num++;
                }
            }
            System.out.println("\nAmount of Paid Bills: " + bill_num);
        }
        if (choice == 2){
            for (Billing_DB.billing_struct bill : Billing_DB.getBillingInfo()){
                if (bill.BillPaidStatus.equals("Unpaid")){
                    bill_num++;
                }
            }
            System.out.println("\nAmount of Unpaid Bills: " + bill_num);
        }
    }

    public void viewExpiredCNIC(){

        for(NADRA_DB.nadra_struct cnic : database.nadra.getNADRA()){
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date1 = LocalDate.parse(cnic.IssuanceDate, dateFormatter);
            LocalDate date2 = LocalDate.parse(cnic.ExpiryDate, dateFormatter);
            long daysBetween = ChronoUnit.DAYS.between(date1, date2);

            if(daysBetween <= 30){
                System.out.println("CNIC: " + cnic.CNIC + " is about Expire! Renew as soon as possible!");
            }
        }
    }

    public void updateTaxTariff() throws InterruptedException {
        ArrayList<Tax_DB.tax_tariff_struct> fullTax = getTaxTariffStructs();

        UpdateTariffScreen updateTariffScreen = new UpdateTariffScreen(fullTax, () -> {
            // Save the updated data back to the database
            Tax_DB.tax_tariff = fullTax;
        });
        updateTariffScreen.setVisible(true);
    }

    private ArrayList<Tax_DB.tax_tariff_struct> getTaxTariffStructs() {
        ArrayList<Tax_DB.tax_tariff_struct> fullTax = new ArrayList<>();

        Tax_DB.tax_tariff_struct D_1 = database.tax.getTax_tariff().get(0);
        Tax_DB.tax_tariff_struct C_1 = database.tax.getTax_tariff().get(1);
        Tax_DB.tax_tariff_struct D_3 = database.tax.getTax_tariff().get(2);
        Tax_DB.tax_tariff_struct C_3 = database.tax.getTax_tariff().get(3);

        fullTax.add(D_1);
        fullTax.add(C_1);
        fullTax.add(D_3);
        fullTax.add(C_3);
        return fullTax;
    }


}
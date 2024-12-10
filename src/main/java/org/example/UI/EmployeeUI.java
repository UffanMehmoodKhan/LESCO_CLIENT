package org.example.UI;

import org.example.Database.Billing_DB;
import org.example.Database.Customer_DB;
import org.example.Database.Tax_DB;
import org.example.Screens.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class EmployeeUI implements UserInterface {

    public EmployeeUI() {
        super();
    }

    @Override
    public ArrayList<String> LoginScreen() {

        CountDownLatch latch = new CountDownLatch(1);
        Login EmployeeLogin = new Login(latch);
        ArrayList<String> credentials = EmployeeLogin.createLogin("employee");

        try {
            latch.await(); // Wait for the user to enter credentials
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return credentials;
    }

    @Override
    public int InfoTerminal() {
        CountDownLatch latch = new CountDownLatch(1);
        EmployeeTerminal infoTerminalScreen = new EmployeeTerminal(latch);

        try {
            latch.await(); // Wait for the user to make a choice
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int choice = infoTerminalScreen.getChoice();
        System.out.println("Choice: " + choice);
        return choice;
    }

    @Override
    public int EmployeeInfoTerminal() {
        return 0;
    }

    public int EmployeeInfoTerminal(ArrayList<Customer_DB.customer_struct> fullDetails) {
        new CustomerInfoScreen(fullDetails);
        return 0;
    }

    @Override
    public String CustomerAddTerminal() {

        Scanner sc = new Scanner(System.in);
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("LESCO Employee Terminal [Add Customer]\n\n");
        String CNIC;
        System.out.println("Enter New Customer Details: \n\n");
        System.out.println("NOTE: Only 3 Meters can be attached to one CNIC");
        System.out.println("Enter CNIC of new meter: "); CNIC = sc.nextLine();

        return CNIC;

    }

    @Override
    public String CustomerUpdateTerminal() {

        Scanner sc = new Scanner(System.in);
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("LESCO Employee Terminal [Update Customer]\n\n");
        String CNIC;
        System.out.println("Enter Update Customer Details: \n\n");
        System.out.println("Enter CNIC of Customer to Update: "); CNIC = sc.nextLine();

        return CNIC;
    }

    @Override
    public int BillingCustomerTerminal(){

        Scanner sc = new Scanner(System.in);
        int choice;
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("LESCO Employee Terminal [Update Customer Billing]\n\n");

        System.out.println("[1] View Bill of ID");
        System.out.println("[2] Update Bill Paid Status: ");
        System.out.println("[3] View Bill Report: ");
        System.out.println("[4] View CNIC Expiry Report: \n");
        System.out.println("Enter Choice: "); choice = sc.nextInt();

        if(choice != 1 && choice != 2 && choice != 3 && choice != 4){
            return this.BillingCustomerTerminal();
        }
        return choice;

    }

    @Override
    public String updateBillingTerminal(){
        Scanner sc = new Scanner(System.in);
        String CNIC;
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("LESCO Employee Terminal [Update Customer Billing]\n\n");

        System.out.println("Enter CNIC to Update: "); CNIC = sc.nextLine();

        return CNIC;
    }

    @Override
    public int viewBillingReport(ArrayList<Billing_DB.billing_struct> fullDetails){
        new BillingInfoScreen(fullDetails);

        return 0;
    }

    @Override
    public void BillingCNICTerminal(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("LESCO Employee Terminal [View Customer CNIC Expiry]\n\n");
    }

    @Override
    public void updateTariffTerminal() {

    }

    @Override
    public ArrayList<String> CustomerBillTerminal() {
        return null;
    }

    @Override
    public String updateCNICTerminal() {
        return "";
    }

    @Override
    public void updateTariffTerminal(ArrayList<Tax_DB.tax_tariff_struct> fullDetails) {
        UpdateTariffScreen updateTariffScreen = new UpdateTariffScreen(fullDetails, new Runnable() {
            @Override
            public void run() {
                // Save the updated data back to the database
                Tax_DB.tax_tariff = fullDetails;
            }
        });
        updateTariffScreen.setVisible(true);
    }
}

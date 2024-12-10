package org.example.UI;

import org.example.Database.Billing_DB;
import org.example.Database.Customer_DB;
import org.example.Database.Tax_DB;
import org.example.Screens.CustomerBillTerminal;
import org.example.Screens.CustomerTerminal;
import org.example.Screens.Login;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class CustomerUI implements UserInterface {

    public CustomerUI() {
        super();
    }

    @Override
    public ArrayList<String> LoginScreen() {
        CountDownLatch latch = new CountDownLatch(1);
        Login CustomerLogin = new Login(latch);
        ArrayList<String> credentials = CustomerLogin.createLogin("customer");

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
        CustomerTerminal customerTerminal = new CustomerTerminal(latch);

        try {
            latch.await(); // Wait for the user to make a choice
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

       int choice = customerTerminal.getChoice();

        System.out.println("Choice: " + choice);
        return choice;
    }

    @Override
    public int EmployeeInfoTerminal() {
        return 0;
    }

    @Override
    public ArrayList<String> CustomerBillTerminal() {
        CountDownLatch latch = new CountDownLatch(1);
        CustomerBillTerminal customerBillTerminal = new CustomerBillTerminal(latch);

        try {
            latch.await(); // Wait for the user to make a choice
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ArrayList<String> data = new ArrayList<>();
        data.add(customerBillTerminal.getChoice());
        return data;
    }

    @Override
    public String updateCNICTerminal() {
        String CNIC;

        Scanner sc = new Scanner(System.in);

        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("LESCO Customer Terminal [Update CNIC]\n\n");
        System.out.println("Enter CNIC: ");
        CNIC = sc.nextLine();

        return CNIC;
    }

    @Override
    public void updateTariffTerminal(ArrayList<Tax_DB.tax_tariff_struct> fullDetails) {

    }

    @Override
    public int EmployeeInfoTerminal(ArrayList<Customer_DB.customer_struct> fullDetails) {
        return 0;
    }

    @Override
    public String CustomerAddTerminal() {
        return "";
    }

    @Override
    public String CustomerUpdateTerminal() {
        return "";
    }

    @Override
    public int BillingCustomerTerminal() {
        return 0;
    }

    @Override
    public String updateBillingTerminal() {
        return "";
    }

    @Override
    public int viewBillingReport(ArrayList<Billing_DB.billing_struct> fullDetails) {
        return 0;
    }

    @Override
    public void BillingCNICTerminal() {
    }

    @Override
    public void updateTariffTerminal() {
    }
}
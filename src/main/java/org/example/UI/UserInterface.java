package org.example.UI;

import org.example.Database.Billing_DB;
import org.example.Database.Customer_DB;
import org.example.Database.Tax_DB;

import java.util.ArrayList;

public interface UserInterface {

    //Screen
    ArrayList<String> LoginScreen();        //Login
    int InfoTerminal();                     //Terminal [Add/Update]

    int EmployeeInfoTerminal();

    // Employee Screen
    int EmployeeInfoTerminal(ArrayList<Customer_DB.customer_struct> fullDetails);
    // [i] Add/Update Customer
    String CustomerAddTerminal();
    String CustomerUpdateTerminal();

    int BillingCustomerTerminal();

    String updateBillingTerminal();
    int viewBillingReport(ArrayList<Billing_DB.billing_struct> fullDetails);

    void BillingCNICTerminal();

    void updateTariffTerminal();

    ArrayList<String> CustomerBillTerminal();

    String updateCNICTerminal();

    void updateTariffTerminal(ArrayList<Tax_DB.tax_tariff_struct> fullDetails);

    // [ii] View Billing Info and Paid/Unpaid Bills


}

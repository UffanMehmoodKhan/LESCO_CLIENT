package org.example.Database;

import java.io.Serializable;
import java.util.ArrayList;

public class database implements Serializable {

    public Employee_DB employee;
    public Customer_DB customer;
    public Billing_DB billing;
    public NADRA_DB nadra;
    public Tax_DB tax;

    ArrayList<ArrayList<?>> collections;

    public database(){
        //Constructor

    }

    @SuppressWarnings("unchecked")
    public void initializeDB(){
        System.out.println("Initializing Databases...\n");


        employee = new Employee_DB();
        customer = new Customer_DB();
        billing = new Billing_DB();
        nadra = new NADRA_DB();
        tax = new Tax_DB();

        employee.employees = (ArrayList<Employee_DB.employee_struct>) collections.getFirst();
        customer.customerInfo = (ArrayList<Customer_DB.customer_struct>) collections.get(1);
        Billing_DB.BillingInfo = (ArrayList<Billing_DB.billing_struct>) collections.get(2);
        nadra.NADRA = (ArrayList<NADRA_DB.nadra_struct>) collections.get(3);
        Tax_DB.tax_tariff = (ArrayList<Tax_DB.tax_tariff_struct>) collections.get(4);
    }

    public void deinitializeDB(){
//        System.out.println("De-initializing Databases...\n");
//        //admin.deinitializeDB();
//        employee.deinitializeDB();
//        customer.deinitializeDB();
//        billing.deinitializeDB();
//        nadra.deinitializeDB();
//        tax.deinitializeDB();

    }

    public void closeDB(){
        System.out.println("Closing Databases...\n");
        // Write all current data onto each respect
        // ive database
    }

    public void setUpDB(ArrayList<ArrayList<?>> database) {
        collections = database;
        this.initializeDB();
    }
}



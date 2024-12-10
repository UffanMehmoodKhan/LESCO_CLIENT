package org.example.Session;


import java.util.ArrayList;

public interface Session {

    //Main Session
    void StartSession() throws InterruptedException;

    //Login
    boolean Login(ArrayList<String> credentials);
    boolean checkloginCredential(ArrayList<String> credentials);


    //View Reports

    //Add Data
    void addNewCustomer(String CNIC);

    //Update Data
    void updateCustomer(String CNIC) throws InterruptedException;

    //Logout

}


package org.example.Database;

import java.io.*;
import java.util.ArrayList;

public class  Admin_DB extends database implements Serializable{

    private final String ADMIN_FILE = "org/example/Database/AdminControl.csv";
    ArrayList<admin_struct> admin;

    // Data Structure for handling admin user_name and password
    public static class admin_struct implements Serializable{
        public String username;
        public String password;

        admin_struct(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        public String toString() {
            return this.username + "," + this.password;
        }
    }

    // Admin_DB Constructor
    public Admin_DB() {
        System.out.println("\nIn admin Database");
        admin = new ArrayList<admin_struct>();
        setADMIN_FILE();
        readADMIN_FILE();

    }

    // Set data from Admin class
    public void setADMIN_FILE() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(ADMIN_FILE));
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                admin_struct new_admin = new admin_struct(values[0], values[1]);
                admin.add(new_admin);
                // Add node to a data structure like a list
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void readADMIN_FILE() {
        for (admin_struct adminStruct : admin) {
            System.out.println(adminStruct.username + "," + adminStruct.password);
        }
    }

    public ArrayList<admin_struct> getAdmin(){
        return admin;
    }

    public void deinitializeDB(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ADMIN_FILE))) {
            for (int i = 0; i < admin.size(); i++) {
                writer.write(admin.get(i).toString());

                // Add a new line except for the last element
                if (i < admin.size() - 1) {
                    writer.newLine();
                }
            }

            System.out.println("Data successfully written to " + ADMIN_FILE);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
package org.example.Database;

import java.io.*;
import java.util.ArrayList;

public class Customer_DB extends database implements Serializable{

    @Serial
    private static final long serialVersionUID = 4L;

    protected final String CUSTOMER_FILE = "org/example/Database/CustomerInfo.csv";
    public ArrayList<customer_struct> customerInfo;

    public static class customer_struct implements Serializable{
        public String ID;
        public String CNIC;
        public String Name;
        public String Address;
        public String Phone;
        public String CustomerType;
        public String meterType;
        public String connectionDate;
        public int regularUnitsConsumed;
        public int peakHourUnitsConsumed;

        public customer_struct(String ID, String CNIC, String Name, String Address,
                               String Phone, String CustomerType, String meterType,
                               String connectionDate, int regularUnitsConsumed, int peakHourUnitsConsumed) {

            this.ID = ID; this.CNIC = CNIC;
            this.Name = Name; this.Address = Address;
            this.Phone = Phone; this.CustomerType = CustomerType;
            this.meterType = meterType; this.connectionDate = connectionDate;
            this.regularUnitsConsumed = regularUnitsConsumed;
            this.peakHourUnitsConsumed = peakHourUnitsConsumed;
        }
        @Override
        public String toString() {
            return  this.ID + "," + this.CNIC + "," +
                    this.Name + "," + this.Address + "," +
                    this.Phone + "," + this.CustomerType + "," +
                    this.meterType + "," + this.connectionDate + "," +
                    this.regularUnitsConsumed + "," + this.peakHourUnitsConsumed;
        }
    }

    public Customer_DB() {
        System.out.println("\nThis is Customer Database");
        customerInfo = new ArrayList<customer_struct>();
        //setCUSTOMER_FILE(CUSTOMER_FILE);
        // readCUSTOMER_FILE();

    }

//    public void setCUSTOMER_FILE(String file) {
//        try {
//            BufferedReader br = new BufferedReader(new FileReader(file));
//            String line;
//            while ((line = br.readLine()) != null) {
//                String[] values = line.split(",");
//                customer_struct new_customer = new customer_struct(
//                        values[0], values[1], values[2],
//                        values[3], values[4], values[5],
//                        values[6], values[7], Integer.parseInt(values[8]),
//                        Integer.parseInt(values[9]));
//                customerInfo.add(new_customer);
//                // Add node to a data structure like a list
//            }
//            br.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public void readCUSTOMER_FILE() {
        for(customer_struct c : customerInfo) {
            System.out.println(c.ID + " " + c.CNIC + " " +
                    c.Name + " " + c.Address + " " +
                    c.Phone + " " + c.CustomerType + " " +
                    c.meterType + " " + c.connectionDate + " " +
                    c.regularUnitsConsumed + " " + c.peakHourUnitsConsumed);
        }
    }

    public ArrayList<customer_struct> getCustomers() {
        return customerInfo;
    }

    public boolean tripleCheck(String CNIC){
        int repeatNum = 0;
        for(customer_struct c : customerInfo) {
            if(c.CNIC.equals(CNIC)) {
                repeatNum++;
            }
        }
        return repeatNum <= 3;
    }

    public String returnID(String CNIC){
        for(customer_struct c : customerInfo) {
            if(c.CNIC.equals(CNIC)) {
                return c.ID;
            }
        }
        return null;
    }

    public void deinitializeDB(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CUSTOMER_FILE))) {
            for(int i = 0; i < customerInfo.size(); i++) {
                writer.write(customerInfo.get(i).toString());

                // Add a new line except for the last element
                if (i < customerInfo.size() - 1) {
                    writer.newLine();
                }
            }

            System.out.println("Data successfully written to " + CUSTOMER_FILE);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

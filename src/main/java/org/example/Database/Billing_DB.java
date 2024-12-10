package org.example.Database;

import java.io.*;
import java.util.ArrayList;

public class Billing_DB extends database implements Serializable{

    @Serial
    private static final long serialVersionUID = 3L;

    protected final String BILLING_FILE = "org/example/Database/BillingInfo.csv";
    public static ArrayList<billing_struct> BillingInfo;

    public static class billing_struct implements Serializable{
        public String ID;
        public String Month;
        public String currentMeterReading;
        public String currentMeterPeak;
        public String ReadingEntryDate;
        public int costOfElectricity;
        public int amountSalesTax;
        public int fixedCharges;
        public int totalBillingAmount;
        public String dueDate;
        public String BillPaidStatus;
        public String BillPaymentDate;

        public billing_struct(String ID, String Month, String currentMeterReading,
                              String currentMeterPeak, String ReadingEntryDate, int costOfElectricity,
                              int amountSalesTax, int fixedCharges, int totalBillingAmount,
                              String dueDate, String BillPaidStatus, String BillPaymentDate){

            this.ID = ID; this.Month = Month; this.currentMeterReading = currentMeterReading;
            this.currentMeterPeak = currentMeterPeak; this.ReadingEntryDate = ReadingEntryDate;
            this.costOfElectricity = costOfElectricity; this.amountSalesTax = amountSalesTax;
            this.fixedCharges = fixedCharges; this.totalBillingAmount = totalBillingAmount;
            this.dueDate = dueDate; this.BillPaidStatus = BillPaidStatus;
            this.BillPaymentDate = BillPaymentDate;

        }

        @Override
        public String toString(){
            return this.ID
                    + "," + this.Month
                    + "," + this.currentMeterReading
                    + "," + this.currentMeterPeak
                    + "," + this.ReadingEntryDate
                    + "," + this.costOfElectricity
                    + "," + this.amountSalesTax
                    + "," + this.fixedCharges
                    + "," + this.totalBillingAmount
                    + "," + this.dueDate
                    + "," + this.BillPaidStatus
                    + "," + this.BillPaymentDate;
        }
    }

    public Billing_DB(){
        System.out.println("\nIn Billing Info Database");
        BillingInfo = new ArrayList<billing_struct>();
        //setBILLING_FILE(BILLING_FILE);
        //readBILLING_FILE();

    }

//    public void setBILLING_FILE(String file){
//
//        try {
//            BufferedReader br = new BufferedReader(new FileReader(file));
//            String line;
//            while ((line = br.readLine()) != null) {
//                String[] values = line.split(",");
//                billing_struct new_bill = new billing_struct(values[0], values[1], values[2], values[3], values[4],
//                        Integer.parseInt(values[5]), Integer.parseInt(values[6]), Integer.parseInt(values[7]), Integer.parseInt(values[8]),
//                        values[9], values[10], values[11]);
//                BillingInfo.add(new_bill);
//                // Add node to a data structure like a list
//            }
//            br.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//    }

    public void readBILLING_FILE(){
        for(billing_struct billing : BillingInfo){
            System.out.println(billing.ID
                    + " " + billing.Month
                    + " " + billing.currentMeterReading
                    + " " + billing.currentMeterPeak
                    + " " + billing.ReadingEntryDate
                    + " " + billing.costOfElectricity
                    + " " + billing.amountSalesTax
                    + " " + billing.fixedCharges
                    + " " + billing.totalBillingAmount
                    + " " + billing.dueDate
                    + " " + billing.BillPaidStatus
                    + " " + billing.BillPaymentDate);
        }
    }

    public static ArrayList<billing_struct> getBillingInfo() {
        System.out.println("getBillingInfo called, returning " + BillingInfo.size() + " entries.");
        return BillingInfo;
    }

    public String returnID(String CNIC){
        for(billing_struct bill : BillingInfo){
            if(bill.ID.equals(CNIC)){
                return bill.ID;
            }
        }
        return null;
    }

    public void deinitializeDB(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BILLING_FILE))) {
            for (int i = 0; i < BillingInfo.size(); i++) {
                writer.write(BillingInfo.get(i).toString());

                // Add a new line except for the last element
                if (i < BillingInfo.size() - 1) {
                    writer.newLine();
                }
            }

            System.out.println("Data successfully written to " + BILLING_FILE);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

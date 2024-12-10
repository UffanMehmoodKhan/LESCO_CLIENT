package org.example.Database;

import java.io.*;
import java.util.ArrayList;

public class NADRA_DB extends database implements Serializable{

    @Serial
    private static final long serialVersionUID = 5L;

    public ArrayList<nadra_struct> NADRA;
    protected final String NADRA_FILE = "org/example/Database/NADRA.csv";

    public static class nadra_struct implements Serializable{
        public String CNIC;
        public String IssuanceDate;
        public String ExpiryDate;

        nadra_struct(String CNIC, String IssuanceDate, String ExpiryDate) {
            this.CNIC = CNIC;
            this.IssuanceDate = IssuanceDate;
            this.ExpiryDate = ExpiryDate;
        }

        @Override
        public String toString() {
            return this.CNIC
                    + "," + this.IssuanceDate
                    + "," + this.ExpiryDate;
        }
    }

    public NADRA_DB() {
        System.out.println("\n\nIn NADRA DB");
        NADRA = new ArrayList<nadra_struct>();
        //setNADRA_FILE(NADRA_FILE);
        readNADRA_FILE();
    }

//    public void setNADRA_FILE(String NADRA_FILE) {
//        try {
//            BufferedReader br = new BufferedReader(new FileReader(NADRA_FILE));
//            String line;
//            while ((line = br.readLine()) != null) {
//                String[] values = line.split(",");
//                nadra_struct nadra_data = new nadra_struct(values[0],values[1],values[2]);
//                NADRA.add(nadra_data);
//                // Add node to a data structure like a list
//            }
//            br.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public void readNADRA_FILE() {

        for (nadra_struct nadra_data : NADRA) {
            System.out.println(nadra_data.CNIC
                    + " " + nadra_data.IssuanceDate
                    + " " + nadra_data.ExpiryDate);
        }
    }

    public boolean isNADRA(String cnic) {
        for (nadra_struct nadra_data : NADRA) {
            if (nadra_data.CNIC.equals(cnic)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<nadra_struct> getNADRA() {
        return NADRA;
    }

    public void deinitializeDB(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NADRA_FILE))) {
            for(int i = 0; i < NADRA.size(); i++) {
                writer.write(NADRA.get(i).toString());

                // Add a new line except for the last element
                if (i < NADRA.size() - 1) {
                    writer.newLine();
                }
            }

            System.out.println("Data successfully written to " + NADRA_FILE);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

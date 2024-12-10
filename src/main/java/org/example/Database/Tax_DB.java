package org.example.Database;

import java.io.*;
import java.util.ArrayList;

public class Tax_DB extends database implements Serializable{

    @Serial
    private static final long serialVersionUID = 6L;

    protected final String TAX_FILE = "org/example/Database/TariffTaxInfo.csv";
    public static ArrayList<tax_tariff_struct> tax_tariff;

    // Tax-Tariff Data Structure
    public static class tax_tariff_struct implements Serializable{
        public String meter_type;
        public int regular_unit_price;
        public int peak_hour_unit_price;
        public int tax_percentage;
        public int fixed_charges;

        public tax_tariff_struct(String meter_type, int regular_unit_price, int peak_hour_unit_price, int tax_percentage, int fixed_charges){
            this.meter_type = meter_type;
            this.regular_unit_price = regular_unit_price;
            this.peak_hour_unit_price = peak_hour_unit_price;
            this.tax_percentage = tax_percentage;
            this.fixed_charges = fixed_charges;
        }

        @Override
        public String toString() {
            return this.meter_type
                    + "," + this.regular_unit_price
                    + "," + this.peak_hour_unit_price
                    + "," + this.tax_percentage
                    + "," + this.fixed_charges;
        }
    }

    public Tax_DB() {
        System.out.println("\n\nIn TAX Tariff DB");
        tax_tariff = new ArrayList<>();
        //setTAX_FILE(TAX_FILE);
        //readTAX_FILE();
    }

//    public void setTAX_FILE(String file) {
//        try {
//            BufferedReader br = new BufferedReader(new FileReader(file));
//            String line;
//            while ((line = br.readLine()) != null) {
//                String[] values = line.split(",");
//                tax_tariff_struct tax_data = new tax_tariff_struct(values[0], Integer.parseInt(values[1]),
//                        Integer.parseInt(values[2]), Integer.parseInt(values[3]), Integer.parseInt(values[4]));
//                tax_tariff.add(tax_data);
//                // Add node to a data structure like a list
//            }
//            br.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public void readTAX_FILE() {
        for (tax_tariff_struct tax_data : tax_tariff) {
            System.out.println(tax_data.meter_type
                    + " " + tax_data.regular_unit_price
                    + " " + tax_data.peak_hour_unit_price
                    + " " + tax_data.tax_percentage
                    + " " + tax_data.fixed_charges);
        }

    }

    public ArrayList<tax_tariff_struct> getTax_tariff() {
        return tax_tariff;
    }

    public void deinitializeDB(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TAX_FILE))) {
            for (int i = 0; i < tax_tariff.size(); i++) {
                writer.write(tax_tariff.get(i).toString());

                // Add a new line except for the last element
                if (i < tax_tariff.size() - 1) {
                    writer.newLine();
                }
            }

            System.out.println("Data successfully written to " + TAX_FILE);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

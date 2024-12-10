package org.example.Database;
import java.io.*;
import java.util.ArrayList;

public class Employee_DB extends database implements Serializable{

    @Serial
    private static final long serialVersionUID = 2L;

    private final static String EMPLOYEE_FILE = "org/example/Database/EmployeesData.csv";
    ArrayList<employee_struct> employees;

    public static class employee_struct implements Serializable{
        public String username;
        public String password;

        employee_struct(String username, String password){
            this.username = username; this.password = password;
        }

        @Override
        public String toString(){
            return this.username + "," + this.password;
        }
    }

    public Employee_DB(){
        System.out.println("\nEmployee Database");
        employees = new ArrayList<>();
        //setEMPLOYEE_FILE(EMPLOYEE_FILE);
        readEMPLOYEE_FILE();

    }

//    public void setEMPLOYEE_FILE(String file){
//        try {
//            BufferedReader br = new BufferedReader(new FileReader(file));
//            String line;
//            while ((line = br.readLine()) != null) {
//                String[] values = line.split(",");
//                employee_struct new_emp = new employee_struct(values[0],values[1]);
//                employees.add(new_emp);
//                // Add node to a data structure like a list
//            }
//            br.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public void readEMPLOYEE_FILE(){
        for(employee_struct emp : employees){
            System.out.println(emp.username + " " + emp.password);
        }
    }

    public ArrayList<employee_struct> getEmployees(){
        return employees;
    }

    public void deinitializeDB(){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(EMPLOYEE_FILE));
            for(int i = 0; i < employees.size(); i++) {
                writer.write(employees.get(i).toString());

                // Add a new line except for the last element
                if (i < employees.size() - 1) {
                    writer.newLine();
                }
            }

            System.out.println("Data successfully written to " + EMPLOYEE_FILE);
            writer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}

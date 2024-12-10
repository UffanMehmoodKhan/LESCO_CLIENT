//package Session;
//
//import Database.*;
//import UI.*;
//import jdk.jshell.spi.ExecutionControl;
//
//import java.util.ArrayList;
//
//public class Admin implements Session {
//
//    database database;
//    UserInterface userInterface;
//
//    public Admin(database mainDBManager) throws InterruptedException {
//        System.out.print("Admin Session created\n");
//        userInterface = new AdminUI();
//        this.database = mainDBManager;
//        this.StartSession();
//    }
//
//    @Override
//    public void StartSession() throws InterruptedException {
//
//        boolean status = Login(userInterface.LoginScreen());    //Check given admin login credentials
//        if (status) {
//            System.out.println("Admin Login Successful");
//            Thread.sleep(3000);
//
//        }
//        else{
//            System.out.println("Invalid Login");
//            throw new InterruptedException();
//        }
//    }
//
//    //Login
//    @Override
//    public boolean Login(ArrayList<String> credentials){
//        return checkloginCredential(credentials);
//    }
//
//    @Override
//    public boolean checkloginCredential(ArrayList<String> credentials) {
//        String username = credentials.getFirst();
//        String password = credentials.getLast();
//
//        for(Admin_DB.admin_struct adminStruct : database.admin.getAdmin()) {
//            if(adminStruct.username.equals(username) && adminStruct.password.equals(password)) {
//                return true;
//            }
//        }
//        return false;
//    }
//}

//package UI;
//
//import jdk.jshell.spi.ExecutionControl;
//
//import java.util.ArrayList;
//import java.util.Scanner;
//
//public class AdminUI implements UserInterface{
//
//    public AdminUI(){
//        super();
//    }
//
//    @Override
//    public ArrayList<String> LoginScreen(){
//
//        ArrayList<String> credentials = new ArrayList<>();
//        String username, password;
//
//        Scanner sc = new Scanner(System.in);
//
//        System.out.print("\033[H\033[2J");
//        System.out.flush();
//        System.out.println("LESCO Admin Login\n\n");
//        System.out.println("Enter username"); username = sc.nextLine();
//        System.out.println("Enter password"); password = sc.nextLine();
//        sc.close();
//
//        credentials.add(username); credentials.add(password);
//
//        return credentials;
//    }
//
//    @Override
//    public int InfoTerminal(){
//        return 0;
//    }
//
//
//}

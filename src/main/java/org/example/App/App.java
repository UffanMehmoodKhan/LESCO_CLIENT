package org.example.App;

import org.example.Database.*;
import org.example.Session.*;

import java.io.*;
import java.net.Socket;

public class App {

    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.out.println("\nUsage: java App -user_type\n");
            return;
        }

        String user = args[0];
        for (String arg : args) {
            System.out.println(arg);
        }

        // Establish client connection and perform its functions
        establishClientConnection(args[0]);

    }

    private static void establishClientConnection(String user) {
        try {

            // Initialize the dbManager object and start connect to server
            database mainDBManager = new database();

            Client client = new Client();

            client.startConnection("127.0.0.1", 8080, user);
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

//            switch (user) {
//                case "-e" -> {
//                    try {
//                        new Employee(mainDBManager);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//                case "-c" -> {
//                    try {
//                        new Customer(mainDBManager);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            }

            System.out.println("Session Closed");
            mainDBManager.deinitializeDB();

            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                client.sendMessage(userInput);
            }
            client.stopConnection();
        } catch (IOException e) {
            System.out.println("No Connection Established!");
            e.getCause();
        }
    }
}
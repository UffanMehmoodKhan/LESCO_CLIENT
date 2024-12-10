package org.example.App;

import org.example.Database.database;
import org.example.Packet;
import org.example.Session.Customer;
import org.example.Session.Employee;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Client extends Thread {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    @SuppressWarnings("unchecked")
    public  void startConnection(String ip, int port, String user) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        database collections = new database();

        new Thread(() -> {
            try {

                ObjectInputStream inStream = new ObjectInputStream(clientSocket.getInputStream());
                Packet packet = (Packet) inStream.readObject();
                collections.setUpDB(packet.getDatabase());

                String response;

                switch (user) {
                    case "-e" -> {
                        try {
                            new Employee(collections);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    case "-c" -> {
                        try {
                            new Customer(collections);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }


                while ((response = in.readLine()) != null) {
                    System.out.println("Server: " + response);
                }


            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void sendMessage(String msg) {
        out.println(msg);
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

//    public static void main(String[] args) throws IOException {
//        Client client = new Client();
//        client.startConnection("127.0.0.1", 8080);
//        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
//        String userInput;
//        while ((userInput = stdIn.readLine()) != null) {
//            client.sendMessage(userInput);
//        }
//        client.stopConnection();
//    }
}
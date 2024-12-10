package org.example.Screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class Login extends JFrame {

    private JTextField userText;
    private JPasswordField passwordText;
    private final ArrayList<String> credentials;
    private final CountDownLatch latch;
    private boolean isFrameVisible = false;

    public Login(CountDownLatch latch) {
        credentials = new ArrayList<>();
        this.latch = latch;
    }

    public ArrayList<String> createLogin(String title) {
        if (isFrameVisible) {
            return null;
        }

        // Create a new JFrame
        JFrame frame = new JFrame(title);

        // Create a new JPanel
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Create a new JLabel for username
        JLabel userLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(userLabel, gbc);

        // Create a new JTextField for username
        userText = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(userText, gbc);

        // Create a new JLabel for password
        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);

        // Create a new JPasswordField for password
        passwordText = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(passwordText, gbc);

        // Create a new JButton for login
        JButton loginButton = new JButton("Login");
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(loginButton, gbc);

        // Add action listener to the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                credentials.clear();
                credentials.add(userText.getText());
                credentials.add(new String(passwordText.getPassword()));
                // Close the frame after getting the credentials
                frame.dispose();
                isFrameVisible = false;
                latch.countDown(); // Signal that the credentials are ready
            }
        });

        // Add the panel to the frame
        frame.add(panel);

        // Set the size of the frame
        frame.setSize(400, 400);

        // Center the frame on the screen
        frame.setLocationRelativeTo(null);

        // Set the frame to be visible
        frame.setVisible(true);

        // Set the default close operation of the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        isFrameVisible = true;
        return credentials;
    }
}
package org.example.Screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;

public class CustomerTerminal extends JFrame {

    private int choice;
    private final CountDownLatch latch;

    public CustomerTerminal(CountDownLatch latch) {
        this.latch = latch;
        this.choice = 0; // Initialize choice to 0
        createCustomerTerminal("Customer Terminal");
    }

    public void createCustomerTerminal(String title) {
        // Create a new JFrame
        JFrame frame = new JFrame(title);

        // Create a new JPanel
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Create buttons
        JButton viewBillButton = new JButton("View Bill");
        JButton updateCNICButton = new JButton("Update CNIC");

        // Add action listeners to buttons
        viewBillButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice = 1;
                frame.dispose();
                latch.countDown(); // Signal that the choice is made
            }
        });

        updateCNICButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice = 2;
                frame.dispose();
                latch.countDown(); // Signal that the choice is made
            }
        });

        // Add buttons to panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(viewBillButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(updateCNICButton, gbc);

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
    }

    public int getChoice() {
        return choice;
    }
}
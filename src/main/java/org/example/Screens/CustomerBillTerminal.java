package org.example.Screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;

public class CustomerBillTerminal extends JFrame {

    private String choice;
    private final CountDownLatch latch;

    public CustomerBillTerminal(CountDownLatch latch) {
        this.latch = latch;
        createCustomerBillTerminal("Customer Bill Terminal");
    }

    public void createCustomerBillTerminal(String title) {
        // Create a new JFrame
        JFrame frame = new JFrame(title);

        // Create a new JPanel
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Create buttons
        JButton onePhaseButton = new JButton("1Phase");
        JButton threePhaseButton = new JButton("3Phase");

        // Add action listeners to buttons
        onePhaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice = "1Phase";
                frame.dispose();
                latch.countDown(); // Signal that the choice is made
            }
        });

        threePhaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice = "3Phase";
                frame.dispose();
                latch.countDown(); // Signal that the choice is made
            }
        });

        // Add buttons to panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(onePhaseButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(threePhaseButton, gbc);

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

    public String getChoice() {
        return choice;
    }
}
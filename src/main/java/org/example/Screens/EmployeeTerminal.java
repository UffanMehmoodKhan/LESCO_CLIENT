package org.example.Screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;

public class EmployeeTerminal extends JFrame {

    private int choice;
    private final CountDownLatch latch;

    public EmployeeTerminal(CountDownLatch latch) {
        this.latch = latch;
        createInfoTerminalScreen("LESCO Employee Terminal");
    }

    public void createInfoTerminalScreen(String title) {
        // Create a new JFrame
        JFrame frame = new JFrame(title);

        // Create a new JPanel
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Create buttons
        JButton customerInfoButton = new JButton("Customer Information");
        JButton billingInfoButton = new JButton("Billing Information");
        JButton updateTariffButton = new JButton("Update Tax-Tariff");
        JButton viewCNICExpiryButton = new JButton("View CNIC Expiry Report");

        // Add action listeners to buttons
        customerInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice = 1;
                frame.dispose();
                latch.countDown(); // Signal that the choice is made
            }
        });

        billingInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice = 2;
                frame.dispose();
                latch.countDown(); // Signal that the choice is made
            }
        });

        updateTariffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice = 3;
                frame.dispose();
                latch.countDown(); // Signal that the choice is made
            }
        });

        viewCNICExpiryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "2 CNICs are still unchecked and have to be renewed.");
            }
        });

        // Add buttons to panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(customerInfoButton, gbc);

        gbc.gridy = 1;
        panel.add(billingInfoButton, gbc);

        gbc.gridy = 2;
        panel.add(updateTariffButton, gbc);

        gbc.gridy = 3;
        panel.add(viewCNICExpiryButton, gbc);

        // Add panel to frame
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public int getChoice() {
        return choice;
    }
}
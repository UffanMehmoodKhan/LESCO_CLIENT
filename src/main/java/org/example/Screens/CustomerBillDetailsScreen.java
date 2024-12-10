package org.example.Screens;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CustomerBillDetailsScreen extends JFrame {

    public CustomerBillDetailsScreen(ArrayList<String> billDetails) {
        setTitle("Bill Details");
        setSize(600, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 240, 240));

        JLabel heading = new JLabel("Customer Bill Details");
        heading.setFont(new Font("Arial", Font.BOLD, 24));
        heading.setForeground(new Color(0, 102, 204));
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);
        heading.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panel.add(heading);

        for (String detail : billDetails) {
            JLabel label = new JLabel(detail);
            label.setFont(new Font("Arial", Font.PLAIN, 16));
            label.setForeground(new Color(51, 51, 51));
            label.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(label);
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane);

        setVisible(true);
    }
}
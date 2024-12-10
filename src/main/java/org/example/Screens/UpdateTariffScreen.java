package org.example.Screens;

import org.example.Database.Tax_DB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UpdateTariffScreen extends JFrame {

    private DefaultTableModel tableModel;
    private final ArrayList<Tax_DB.tax_tariff_struct> fullDetails;
    private final Runnable onSaveCallback;

    public UpdateTariffScreen(ArrayList<Tax_DB.tax_tariff_struct> fullDetails, Runnable onSaveCallback) {
        this.fullDetails = fullDetails;
        this.onSaveCallback = onSaveCallback;
        createUpdateTariffScreen();
    }

    private void createUpdateTariffScreen() {
        setTitle("LESCO Employee Terminal [Update Tax-Tariff Data]");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columnNames = {"Phase", "Regular Price", "Peak Hour Price", "Tax Percentage", "Fixed Charges"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);

        // Populate the table with fullDetails data
        for (Tax_DB.tax_tariff_struct detail : fullDetails) {
            Object[] rowData = {
                    detail.meter_type,
                    detail.regular_unit_price,
                    detail.peak_hour_unit_price,
                    detail.tax_percentage,
                    detail.fixed_charges
            };
            tableModel.addRow(rowData);
        }

        // Add an empty row for new data entry
        tableModel.addRow(new Object[]{"", "", "", "", ""});

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Create a panel for the Save button
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        buttonPanel.add(saveButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listener to the Save button
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveTableData();
                if (onSaveCallback != null) {
                    onSaveCallback.run();
                }
                dispose();
            }
        });

        setVisible(true);
    }

    private void saveTableData() {
        fullDetails.clear();
        for (int i = 0; i < tableModel.getRowCount() - 1; i++) { // Exclude the last empty row
            String phase = (String) tableModel.getValueAt(i, 0);
            int regularPrice = Integer.parseInt(tableModel.getValueAt(i, 1).toString());
            int peakHourPrice = Integer.parseInt(tableModel.getValueAt(i, 2).toString());
            int taxPercentage = Integer.parseInt(tableModel.getValueAt(i, 3).toString());
            int fixedCharges = Integer.parseInt(tableModel.getValueAt(i, 4).toString());

            Tax_DB.tax_tariff_struct detail = new Tax_DB.tax_tariff_struct(phase, regularPrice, peakHourPrice, taxPercentage, fixedCharges);
            fullDetails.add(detail);
        }
        JOptionPane.showMessageDialog(this, "Data saved successfully!");
        Tax_DB.tax_tariff = fullDetails;
    }
}
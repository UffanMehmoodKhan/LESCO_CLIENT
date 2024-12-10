package org.example.Screens;

import org.example.Database.Billing_DB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BillingInfoScreen extends JFrame {

    private final DefaultTableModel tableModel;
    private final JTable table;
    private final ArrayList<Billing_DB.billing_struct> fullDetails;
    private final TableRowSorter<DefaultTableModel> rowSorter;

    public BillingInfoScreen(ArrayList<Billing_DB.billing_struct> fullDetails) {
        this.fullDetails = fullDetails;
        setTitle("Billing Information");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columnNames = {"ID", "Payment_Month", "currentReading", "currentPeak", "ReadingEntryDate", "costofElec", "SalesTax", "fixedCharges", "totalAmount", "dueDate", "BillPaidStatus", "BillPaymentDate", "Add", "Delete"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        rowSorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(rowSorter);
        populateTable();

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel topPanel = new JPanel(new BorderLayout());
        JTextField searchField = new JTextField();
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                searchTable(searchField.getText());
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                searchTable(searchField.getText());
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                searchTable(searchField.getText());
            }
        });
        topPanel.add(new JLabel("Search: "), BorderLayout.WEST);
        topPanel.add(searchField, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(event -> saveData());
        buttonPanel.add(saveButton);
        add(buttonPanel, BorderLayout.SOUTH);

        addButtonListeners();

        setVisible(true);
    }

    private void populateTable() {
        for (Billing_DB.billing_struct billing : fullDetails) {
            Object[] rowData = {
                    billing.ID, billing.Month, billing.currentMeterReading, billing.currentMeterPeak, billing.ReadingEntryDate, billing.costOfElectricity, billing.amountSalesTax, billing.fixedCharges, billing.totalBillingAmount, billing.dueDate, billing.BillPaidStatus, billing.BillPaymentDate, "Add", "Delete"
            };
            tableModel.addRow(rowData);
        }
    }

    private void addRow() {
        Object[] newRow = {"", "", "", "", "", 0, 0, 0, 0, "", "", "", "Add", "Delete"};
        tableModel.addRow(newRow);
    }

    private void deleteRow(int row) {
        tableModel.removeRow(row);
    }

    private void saveData() {
        fullDetails.clear();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String ID = (String) tableModel.getValueAt(i, 0);
            String Month = (String) tableModel.getValueAt(i, 1);
            String currentMeterReading = (String) tableModel.getValueAt(i, 2);
            String currentMeterPeak = (String) tableModel.getValueAt(i, 3);
            String ReadingEntryDate = (String) tableModel.getValueAt(i, 4);
            int costOfElectricity = Integer.parseInt(tableModel.getValueAt(i, 5).toString());
            int amountSalesTax = Integer.parseInt(tableModel.getValueAt(i, 6).toString());
            int fixedCharges = Integer.parseInt(tableModel.getValueAt(i, 7).toString());
            int totalBillingAmount = Integer.parseInt(tableModel.getValueAt(i, 8).toString());
            String dueDate = (String) tableModel.getValueAt(i, 9);
            String BillPaidStatus = (String) tableModel.getValueAt(i, 10);
            String BillPaymentDate = (String) tableModel.getValueAt(i, 11);

            Billing_DB.billing_struct billing = new Billing_DB.billing_struct(ID, Month, currentMeterReading, currentMeterPeak, ReadingEntryDate, costOfElectricity, amountSalesTax, fixedCharges, totalBillingAmount, dueDate, BillPaidStatus, BillPaymentDate);
            fullDetails.add(billing);
        }
        // Update the database
        // Billing_DB.deinitializeDB();
        dispose(); // Close the window
    }

    private void addButtonListeners() {
        table.getColumn("Add").setCellRenderer(new ButtonRenderer());
        table.getColumn("Add").setCellEditor(new ButtonEditor(new JCheckBox(), this::addRow));

        table.getColumn("Delete").setCellRenderer(new ButtonRenderer());
        table.getColumn("Delete").setCellEditor(new ButtonEditor(new JCheckBox(), () -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                deleteRow(row);
            }
        }));
    }

    private void searchTable(String query) {
        if (query.trim().length() == 0) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + query));
        }
    }

    public static void main(String[] args) {
        ArrayList<Billing_DB.billing_struct> fullDetails = new ArrayList<>();
        fullDetails.add(new Billing_DB.billing_struct("0001", "September", "1200", "2400", "18/09/2024", 500, 17500, 50, 600050, "25/09/2024", "Unpaid", "18/09/2024"));
        new BillingInfoScreen(fullDetails);
    }
}

class ButtonRenderer extends JButton implements TableCellRenderer {

    public ButtonRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setText((value == null) ? "" : value.toString());
        return this;
    }
}

class ButtonEditor extends DefaultCellEditor {
    private final JButton button;
    private String label;
    private boolean isPushed;
    private final Runnable action;

    public ButtonEditor(JCheckBox checkBox, Runnable action) {
        super(checkBox);
        this.action = action;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            action.run();
        }
        isPushed = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}
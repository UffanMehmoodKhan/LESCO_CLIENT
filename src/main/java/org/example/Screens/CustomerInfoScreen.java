package org.example.Screens;

import org.example.Database.Customer_DB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;

public class CustomerInfoScreen extends JFrame {

    private final DefaultTableModel tableModel;
    private final JTable table;
    private final ArrayList<Customer_DB.customer_struct> fullDetails;
    private final TableRowSorter<DefaultTableModel> rowSorter;

    public CustomerInfoScreen(ArrayList<Customer_DB.customer_struct> fullDetails) {
        this.fullDetails = fullDetails;
        setTitle("Customer Information");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columnNames = {"Customer_ID", "CNIC", "Cust_Name", "Address", "Phone_Num", "MeterType", "Phase", "Conn_Date", "Regular_Meter_Consumed", "peakHoursCons", "Add", "Delete"};
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
        for (Customer_DB.customer_struct customer : fullDetails) {
            Object[] rowData = {
                    customer.ID, customer.CNIC, customer.Name, customer.Address, customer.Phone, customer.CustomerType, customer.meterType, customer.connectionDate, customer.regularUnitsConsumed, customer.peakHourUnitsConsumed, "Add", "Delete"
            };
            tableModel.addRow(rowData);
        }
    }

    private void addRow() {
        Object[] newRow = {"", "", "", "", "", "", "", "", 0, 0, "Add", "Delete"};
        tableModel.addRow(newRow);
    }

    private void deleteRow(int row) {
        tableModel.removeRow(row);
    }

    private void saveData() {
        fullDetails.clear();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String ID = (String) tableModel.getValueAt(i, 0);
            String CNIC = (String) tableModel.getValueAt(i, 1);
            String Name = (String) tableModel.getValueAt(i, 2);
            String Address = (String) tableModel.getValueAt(i, 3);
            String Phone = (String) tableModel.getValueAt(i, 4);
            String CustomerType = (String) tableModel.getValueAt(i, 5);
            String meterType = (String) tableModel.getValueAt(i, 6);
            String connectionDate = (String) tableModel.getValueAt(i, 7);
            int regularUnitsConsumed = Integer.parseInt(tableModel.getValueAt(i, 8).toString());
            int peakHourUnitsConsumed = Integer.parseInt(tableModel.getValueAt(i, 9).toString());

            Customer_DB.customer_struct customer = new Customer_DB.customer_struct(ID, CNIC, Name, Address, Phone, CustomerType, meterType, connectionDate, regularUnitsConsumed, peakHourUnitsConsumed);
            fullDetails.add(customer);
        }
        // Update the database
        //Customer_DB.deinitializeDB();
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

}


//class ButtonRenderer extends JButton implements TableCellRenderer {
//
//    public ButtonRenderer() {
//        setOpaque(true);
//    }
//
//    @Override
//    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//        setText((value == null) ? "" : value.toString());
//        return this;
//    }
//}
//
//class ButtonEditor extends DefaultCellEditor {
//    private final JButton button;
//    private String label;
//    private boolean isPushed;
//    private final Runnable action;
//
//    public ButtonEditor(JCheckBox checkBox, Runnable action) {
//        super(checkBox);
//        this.action = action;
//        button = new JButton();
//        button.setOpaque(true);
//        button.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                fireEditingStopped();
//            }
//        });
//    }
//
//    @Override
//    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
//        label = (value == null) ? "" : value.toString();
//        button.setText(label);
//        isPushed = true;
//        return button;
//    }
//
//    @Override
//    public Object getCellEditorValue() {
//        if (isPushed) {
//            action.run();
//        }
//        isPushed = false;
//        return label;
//    }
//
//    @Override
//    public boolean stopCellEditing() {
//        isPushed = false;
//        return super.stopCellEditing();
//    }
//
//    @Override
//    protected void fireEditingStopped() {
//        super.fireEditingStopped();
//    }
//}
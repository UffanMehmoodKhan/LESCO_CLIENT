//package Screens;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
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
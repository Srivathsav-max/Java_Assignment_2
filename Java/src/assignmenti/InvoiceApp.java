package assignmenti;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class InvoiceApp extends JFrame {
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField partNumberField, partDescriptionField, quantityField, priceField;
    private JButton addButton, updateButton, deleteButton;
    
    
    private List<Invoice> invoiceList = new ArrayList<>();
    

    public InvoiceApp() {
        setTitle("Java Assignment Question 1.i");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Part Number");
        tableModel.addColumn("Part Description");
        tableModel.addColumn("Quantity");
        tableModel.addColumn("Price Per Item");
        tableModel.addColumn("Invoice Value");

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2));
        inputPanel.add(new JLabel("Part Number:"));
        partNumberField = new JTextField();
        inputPanel.add(partNumberField);
        inputPanel.add(new JLabel("Part Description:"));
        partDescriptionField = new JTextField();
        inputPanel.add(partDescriptionField);
        inputPanel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        inputPanel.add(quantityField);
        inputPanel.add(new JLabel("Price Per Item:"));
        priceField = new JTextField();
        inputPanel.add(priceField);

        addButton = new JButton("Add");
        addButton.addActionListener(e -> addInvoice());
        updateButton = new JButton("Update");
        updateButton.addActionListener(e -> updateInvoice());
        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> deleteInvoice());

        inputPanel.add(addButton);
        inputPanel.add(updateButton);
        inputPanel.add(deleteButton);
        add(inputPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && table.getSelectedRow() >= 0) {
                    int selectedRow = table.getSelectedRow();
                    partNumberField.setText((String) tableModel.getValueAt(selectedRow, 0));
                    partDescriptionField.setText((String) tableModel.getValueAt(selectedRow, 1));
                    quantityField.setText(tableModel.getValueAt(selectedRow, 2).toString());
                    priceField.setText(tableModel.getValueAt(selectedRow, 3).toString());
                }
            }
        });

        

        // Part A: Sort by partDescription
        JButton sortPartDescriptionButton = new JButton("Sort by Part Description");
        sortPartDescriptionButton.addActionListener(e -> sortAndDisplayByPartDescription());
        inputPanel.add(sortPartDescriptionButton);

        // Part B: Sort by pricePerItem
        JButton sortPriceButton = new JButton("Sort by Price Per Item");
        sortPriceButton.addActionListener(e -> sortAndDisplayByPricePerItem());
        inputPanel.add(sortPriceButton);

        // Part C: Sort by quantity
        JButton sortQuantityButton = new JButton("Sort by Quantity");
        sortQuantityButton.addActionListener(e -> sortAndDisplayByQuantity());
        inputPanel.add(sortQuantityButton);

        // Part D: Sort by Invoice value
        JButton sortValueButton = new JButton("Sort by Invoice Value");
        sortValueButton.addActionListener(e -> sortAndDisplayByValue());
        inputPanel.add(sortValueButton);

        // Part E: Invoices within the range $200 to $500
        JButton invoicesInRangeButton = new JButton("Invoices in Range $200-$500");
        invoicesInRangeButton.addActionListener(e -> displayInvoicesInRange());
        inputPanel.add(invoicesInRangeButton);

        // Part F: Find Invoice with 'Saw' in partDescription
        JButton findSawButton = new JButton("Find Invoice with 'Saw'");
        findSawButton.addActionListener(e -> findInvoiceWithSaw());
        inputPanel.add(findSawButton);

        setVisible(true);
    }

    private void addInvoice() {
        try {
            String partNumber = partNumberField.getText();
            String partDescription = partDescriptionField.getText();
            int quantity = Integer.parseInt(quantityField.getText());
            double pricePerItem = Double.parseDouble(priceField.getText());

            Invoice invoice = new Invoice(partNumber, partDescription, quantity, pricePerItem);
            invoiceList.add(invoice);
            tableModel.addRow(new Object[]{partNumber, partDescription, quantity, pricePerItem});

            clearFields();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Quantity and Price must be numbers.");
        }
    }

    private void updateInvoice() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            try {
                String partNumber = partNumberField.getText();
                String partDescription = partDescriptionField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                double pricePerItem = Double.parseDouble(priceField.getText());

                Invoice updatedInvoice = new Invoice(partNumber, partDescription, quantity, pricePerItem);
                invoiceList.set(selectedRow, updatedInvoice);

                tableModel.setValueAt(partNumber, selectedRow, 0);
                tableModel.setValueAt(partDescription, selectedRow, 1);
                tableModel.setValueAt(quantity, selectedRow, 2);
                tableModel.setValueAt(pricePerItem, selectedRow, 3);

                clearFields();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Quantity and Price must be numbers.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select an invoice to update.");
        }
    }

    private void deleteInvoice() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String partNumber = (String) tableModel.getValueAt(selectedRow, 0);
            String partDescription = (String) tableModel.getValueAt(selectedRow, 1);

            // Find the matching invoice in the list and remove it
            Optional<Invoice> optionalInvoice = invoiceList.stream()
                    .filter(invoice -> invoice.getPartNumber().equals(partNumber) && invoice.getPartDescription().equals(partDescription))
                    .findAny();

            if (optionalInvoice.isPresent()) {
                Invoice invoiceToRemove = optionalInvoice.get();
                invoiceList.remove(invoiceToRemove);
            }

            tableModel.removeRow(selectedRow);
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Select an invoice to delete.");
        }
    }

    private void clearFields() {
        partNumberField.setText("");
        partDescriptionField.setText("");
        quantityField.setText("");
        priceField.setText("");
    }

    private void sortAndDisplayByPartDescription() {
        List<Invoice> sortedList = invoiceList.stream()
                .sorted(Comparator.comparing(Invoice::getPartDescription))
                .collect(Collectors.toList());

        displayInTable(sortedList);
    }

    private void sortAndDisplayByPricePerItem() {
        List<Invoice> sortedList = invoiceList.stream()
                .sorted(Comparator.comparingDouble(Invoice::getPricePerItem))
                .collect(Collectors.toList());

        displayInTable(sortedList);
    }

    private void sortAndDisplayByQuantity() {
        List<Invoice> sortedList = invoiceList.stream()
                .sorted(Comparator.comparingInt(Invoice::getQuantity))
                .collect(Collectors.toList());

        displayInTable(sortedList);
    }

    private void sortAndDisplayByValue() {
        List<Invoice> sortedList = invoiceList.stream()
                .sorted(Comparator.comparing(Invoice::getPaymentAmount))
                .collect(Collectors.toList());

        displayInTable(sortedList);
    }

    private void displayInvoicesInRange() {
        List<Invoice> filteredList = invoiceList.stream()
                .filter(invoice -> invoice.getPaymentAmount() >= 200 && invoice.getPaymentAmount() <= 500)
                .collect(Collectors.toList());

        displayInTable(filteredList);
    }

    private void findInvoiceWithSaw() {
        List<Invoice> matchingInvoices = invoiceList.stream()
                .filter(invoice -> invoice.getPartDescription().toLowerCase().contains("saw"))
                .collect(Collectors.toList());

        if (!matchingInvoices.isEmpty()) {
            displayInTable(matchingInvoices);
        } else {
            JOptionPane.showMessageDialog(this, "No invoices with 'Saw' found.");
        }
    }


    private void displayInTable(List<Invoice> invoices) {
        tableModel.setRowCount(0); // Clear the table

        for (Invoice invoice : invoices) {
        	double invoiceValue = invoice.getPaymentAmount();
            tableModel.addRow(new Object[]{invoice.getPartNumber(), invoice.getPartDescription(), invoice.getQuantity(), invoice.getPricePerItem(), invoiceValue});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InvoiceApp());
    }
}


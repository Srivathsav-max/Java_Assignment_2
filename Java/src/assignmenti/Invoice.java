package assignmenti;

import java.util.*;
import java.util.stream.Collectors;

public class Invoice {
    private final String partNumber;
    private final String partDescription;
    private final int quantity;
    private final double pricePerItem;

    public Invoice(String partNumber, String partDescription, int quantity, double pricePerItem) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity must be >= 0");
        }
        if (pricePerItem < 0.0) {
            throw new IllegalArgumentException("Price per item must be >= 0");
        }

        this.quantity = quantity;
        this.partNumber = partNumber;
        this.partDescription = partDescription;
        this.pricePerItem = pricePerItem;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public String getPartDescription() {
        return partDescription;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPricePerItem() {
        return pricePerItem;
    }

    @Override
    public String toString() {
        return String.format("%s:%n%s: %s (%s)%n%s: %d%n%s: $%,.2f", "invoice", "part number", getPartNumber(), getPartDescription(),
                "quantity", getQuantity(), "price per item", getPricePerItem());
    }

    public double getPaymentAmount() {
        return getQuantity() * getPricePerItem();
    }

    public static void main(String[] args) {
        List<Invoice> invoiceList = new ArrayList<>(Arrays.asList(
            new Invoice("1", "Toothbrush", 1, 10),
            new Invoice("2", "Ketchup", 1, 12.2),
            new Invoice("3", "Chocolate", 1, 12.9),
            new Invoice("4", "Saw", 1, 14.2) // Updated 'Saw' for clarity
        ));

        // Part A: Sort by partDescription
        List<Invoice> sortedListByDescription = invoiceList.stream()
                .sorted(Comparator.comparing(Invoice::getPartDescription))
                .collect(Collectors.toList());

        System.out.println("Part A: Sorted by partDescription");
        sortedListByDescription.forEach(System.out::println);

        // Part B: Sort by pricePerItem
        List<Invoice> sortedListByPrice = invoiceList.stream()
                .sorted(Comparator.comparingDouble(Invoice::getPricePerItem))
                .collect(Collectors.toList());

        System.out.println("\nPart B: Sorted by pricePerItem");
        sortedListByPrice.forEach(System.out::println);

        // Part C: Map to partDescription and quantity, then sort by quantity
        List<Map.Entry<String, Integer>> partDescriptionQuantityList = invoiceList.stream()
                .map(invoice -> new AbstractMap.SimpleEntry<>(invoice.getPartDescription(), invoice.getQuantity()))
                .sorted(Comparator.comparingInt(Map.Entry::getValue))
                .collect(Collectors.toList());

        System.out.println("\nPart C: Mapped to partDescription and sorted by quantity");
        partDescriptionQuantityList.forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));

        // Part D: Map to partDescription and value, then order by value
        List<Map.Entry<String, Double>> partDescriptionValueList = invoiceList.stream()
                .map(invoice -> new AbstractMap.SimpleEntry<>(invoice.getPartDescription(), invoice.getPaymentAmount()))
                .sorted(Comparator.comparingDouble(Map.Entry::getValue))
                .collect(Collectors.toList());

        System.out.println("\nPart D: Mapped to partDescription and sorted by value");
        partDescriptionValueList.forEach(entry -> System.out.println(entry.getKey() + ": $" + entry.getValue()));

        // Part E: Select invoices within a specific value range
        List<Invoice> invoicesInRange = invoiceList.stream()
                .filter(invoice -> invoice.getPaymentAmount() >= 200 && invoice.getPaymentAmount() <= 500)
                .sorted(Comparator.comparingDouble(Invoice::getPaymentAmount))
                .collect(Collectors.toList());

        System.out.println("\nPart E: Invoices within the range $200 to $500");
        invoicesInRange.forEach(System.out::println);

        // Part F: Find an Invoice with 'Saw' in partDescription
        Optional<Invoice> sawInvoice = invoiceList.stream()
                .filter(invoice -> invoice.getPartDescription().contains("Saw"))
                .findAny();

        System.out.println("\nPart F: An Invoice with 'Saw' in partDescription");
        sawInvoice.ifPresent(System.out::println);
    }
}
	

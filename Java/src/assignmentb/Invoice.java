package assignmentb;

public class Invoice implements Payable {
    private final String partNumber;
    private final String partDescription;
    private final int quantity;
    private final double pricePerItem;

    // Constructor
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

    // Get part number
    public String getPartNumber() {
        return partNumber;
    }

    // Get description
    public String getPartDescription() {
        return partDescription;
    }

    // Get quantity
    public int getQuantity() {
        return quantity;
    }

    // Get price per item
    public double getPricePerItem() {
        return pricePerItem;
    }

    // Return String representation of Invoice object
    @Override
    public String toString() {
        return String.format("%s:%n%s: %s (%s)%n%s: %d%n%s: $%,.2f",
                "Invoice", "Part Number", getPartNumber(), getPartDescription(),
                "Quantity", getQuantity(), "Price Per Item", getPricePerItem());
    }

    // Method required to carry out the contract with the Payable interface
    @Override
    public double getPaymentAmount() {
        return getQuantity() * getPricePerItem(); // Calculate total cost
    }
}


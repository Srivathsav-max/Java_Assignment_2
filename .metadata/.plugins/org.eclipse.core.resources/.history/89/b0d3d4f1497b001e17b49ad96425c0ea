package assignmentb;

public class PayableInterfaceTest {
    public static void main(String[] args) {
        // Create a four-element Payable array
        Payable[] payableObjects = new Payable[] {
            new Invoice("01234", "seat", 2, 375.00),
            new Invoice("56789", "tire", 4, 79.95),
            new SalariedEmployee("John", "Smith", "111-11-1111", 800.00),
            new SalariedEmployee("Lisa", "Barnes", "888-88-8888", 1200.00)
        };

        System.out.println("Invoices and Employees processed polymorphically:");

        // Generically process each element in the array payableObjects
        for (Payable currentPayable : payableObjects) {
            // Output currentPayable and its appropriate payment amount
            System.out.printf("%n%s %npayment due: $%,.2f%n",
                currentPayable.toString(),
                currentPayable.getPaymentAmount()
            );
        }
    }
}

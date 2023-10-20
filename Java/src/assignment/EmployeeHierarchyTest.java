package assignment;

public class EmployeeHierarchyTest {
    public static void main(String[] args) {
        // Create a CommissionEmployee
        CommissionEmployee commissionEmployee = new CommissionEmployee(
            "Java", "Test", "123-45-6789", 5000.0, 0.1
        );

        // Create a BasePlusCommissionEmployee
        BasePlusCommissionEmployee basePlusCommissionEmployee = new BasePlusCommissionEmployee(
            "Assignment", "Hie", "987-65-4321", 8000.0, 0.12, 1500.0
        );

        // Display information for CommissionEmployee
        System.out.println("Commission Employee:");
        System.out.println(commissionEmployee);
        System.out.println("Earnings: " + commissionEmployee.earnings());
        System.out.println();

        // Display information for BasePlusCommissionEmployee
        System.out.println("Base Plus Commission Employee:");
        System.out.println(basePlusCommissionEmployee);
        System.out.println("Earnings: " + basePlusCommissionEmployee.earnings());
    }
}


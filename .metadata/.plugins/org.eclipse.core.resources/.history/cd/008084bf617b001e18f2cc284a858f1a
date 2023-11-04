package assignmentc;
import java.util.Scanner;



class ValidationException extends Exception
{
 public ValidationException(String message) 
 {
     // call the constructor of Exception class

     super(message);
   }
}

//create a Phone class

class Phone
{
 // create two string variables name, serialNumber

 String name, serialNumber;

 // create constructor -- initializes the name and serialNumber

 public Phone(String nm, String snum)
 {
     name = nm;
     serialNumber = snum;
 }

 // create validate() method and throwing ValidationException

 public void validate()throws ValidationException
 {  
     // if name is empty then throw a ValidationException

     if(name.isEmpty())  
         throw new ValidationException("Name is null");

     // if serialNumber is empty then throw a ValidationException

     else if(serialNumber.isEmpty())
         throw new ValidationException("Serial Number is null");

     // if serialNumber is not exactly 16 characters then throw a ValidationException

     else if(serialNumber.length()!=16)
         throw new ValidationException("Serial Number is not 16 characters");

     // otherwise, if name is not empty, serialNumber is not empty, 
     // and serialNumber is exactly 16 characters then display message

     else if(!name.isEmpty() && !serialNumber.isEmpty() && serialNumber.length()==16)
         System.out.println("Phone Class constructed successfully");  
}  
}

//create a class YourName_Q3



public class AssignC {
    public static void main(String[] args) {
        // Create a Scanner object for user input
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();

        // Close the scanner when you're done with it
        scanner.close();

        // Create a Phone object with the input values
        Phone obj1 = new Phone(name, phoneNumber);

        // Try block
        try {
            // Call the validate method using obj1
            obj1.validate();
            System.out.println("Phone number is valid.");
        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        }
    }
}

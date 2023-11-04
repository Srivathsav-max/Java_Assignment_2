package assignmentc;

class SomeClass {
    public SomeClass() throws Exception {
        throw new Exception("Constructor of SomeClass failed.");
    }
}

public class ExceptionHandlingExample {
    public static void main(String[] args) {
        try {
            SomeClass s = new SomeClass();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

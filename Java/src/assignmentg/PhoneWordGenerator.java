package assignmentg;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PhoneWordGenerator extends JFrame {
    private JTextField input;
    private JLabel prompt;

    public PhoneWordGenerator() {
        super("Phone Word Generator");

        // Initialize the GUI components
        input = new JTextField(15);
        input.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                generateWords(); // Calculate and display character sequences
            }
        });

        prompt = new JLabel("Enter a phone number (use digits 2-9 only):");

        // Set up the container and layout
        Container container = getContentPane();
        container.setLayout(new FlowLayout());
        container.add(prompt);
        container.add(input);

        // Set the initial window size and visibility
        setSize(300, 100);
        setVisible(true);
    }

    // Generate and display letter combinations
    private void generateWords() {
        // Define the mapping of digits to letters
        String[] letters = {
            "0", "1", "ABC", "DEF", "GHI", "JKL", "MNO", "PRS", "TUV", "WXY"
        };

        long phoneNumber = Long.parseLong(input.getText());
        int[] digits = new int[7];

        // Extract the digits from the phone number
        for (int i = 6; i >= 0; i--) {
            digits[i] = (int) (phoneNumber % 10);
            phoneNumber /= 10;
        }

        PrintStream output = null;

        try {
            output = new PrintStream(new FileOutputStream("phone_words.txt"));
        } catch (IOException exception) {
            JOptionPane.showMessageDialog(null, exception.toString(), "Exception", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        input.setText("Please wait...");

        int[] loop = new int[7];

        // Output all possible combinations of letters
        generateWordsRecursively(0, loop, digits, letters, output);

        input.setText("Done");

        output.close(); // Close the output stream
    }

    private void generateWordsRecursively(int index, int[] loop, int[] digits, String[] letters, PrintStream output) {
        if (index == 7) {
            for (int i = 0; i < 7; i++) {
                output.print(letters[digits[i]].charAt(loop[i]));
            }
            output.println();
            return;
        }

        int digit = digits[index];
        if (digit >= 2 && digit <= 9) {
            String digitLetters = letters[digit];
            for (loop[index] = 0; loop[index] < digitLetters.length(); loop[index]++) {
                generateWordsRecursively(index + 1, loop, digits, letters, output);
            }
        }
    }

    public static void main(String args[]) {
        PhoneWordGenerator application = new PhoneWordGenerator();
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

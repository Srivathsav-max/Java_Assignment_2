package assignmentg;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Phone extends JFrame {
    private JTextField input;
    private JLabel prompt;

    public Phone() {
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
        String[][] letters = {
            {""}, {""}, {"A", "B", "C"}, {"D", "E", "F"},
            {"G", "H", "I"}, {"J", "K", "L"}, {"M", "N", "O"},
            {"P", "R", "S"}, {"T", "U", "V"}, {"W", "X", "Y"}
        };

        long phoneNumber = Long.parseLong(input.getText());
        int digits[] = new int[7];
        
        // Extract the digits from the phone number
        for (int i = 6; i >= 0; i--) {
            digits[i] = (int)(phoneNumber % 10);
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

        int loop[] = new int[7];

        // Output all possible combinations of letters
        for (loop[0] = 0; loop[0] <= 2; loop[0]++)
            for (loop[1] = 0; loop[1] <= 2; loop[1]++)
                for (loop[2] = 0; loop[2] <= 2; loop[2]++)
                    for (loop[3] = 0; loop[3] <= 2; loop[3]++)
                        for (loop[4] = 0; loop[4] <= 2; loop[4]++)
                            for (loop[5] = 0; loop[5] <= 2; loop[5]++)
                                for (loop[6] = 0; loop[6] <= 2; loop[6]++) {
                                    for (int i = 6; i >= 0; i--)
                                        output.print(letters[digits[i]][loop[i]);
                                    output.println();
                                }

        input.setText("Done");

        output.close(); // Close the output stream
    }

    public static void main(String args[]) {
        PhoneWordGenerator application = new PhoneWordGenerator();
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

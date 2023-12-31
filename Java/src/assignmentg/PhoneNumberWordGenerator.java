package assignmentg;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Formatter;

public class PhoneNumberWordGenerator {

    private static final String[] letters = {
        "", "", "ABC", "DEF", "GHI", "JKL", "MNO", "PRS", "TUV", "WXY"
    };

    public static void generateWords(String phoneNumber) {
        try (Formatter output = new Formatter(new FileOutputStream("phone_words_TeamE.txt"))) {
            generateWordsRecursively(phoneNumber, 0, "", output);
        } catch (FileNotFoundException e) {
            System.err.println("Error creating or writing to the file: " + e.getMessage());
        }
    }

    private static void generateWordsRecursively(String phoneNumber, int index, String currentWord, Formatter output) {
        if (index == phoneNumber.length()) {
            output.format("%s%n", currentWord);
            return;
        }

        int digit = Character.getNumericValue(phoneNumber.charAt(index));
        if (digit >= 2 && digit <= 9) {
            String digitLetters = letters[digit];
            for (int i = 0; i < digitLetters.length(); i++) {
                generateWordsRecursively(phoneNumber, index + 1, currentWord + digitLetters.charAt(i), output);
            }
        }
    }

    public static void main(String[] args) {
        String phoneNumber = "7382273"; // Replace this with the desired phone number
        generateWords(phoneNumber);
        System.out.println("Words generated and saved to 'phone_words_TeamE.txt'.");
    }
}


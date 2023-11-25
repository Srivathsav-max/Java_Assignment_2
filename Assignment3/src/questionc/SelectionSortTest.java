package questionc;

import java.security.SecureRandom;
import java.util.Arrays;

public class SelectionSortTest {
    // sort array using selection sort
    public static void selectionSort(int[] data) {
        // loop over data.length - 1 elements
        for (int i = 0; i < data.length - 1; i++) {
            int smallest = i; // first index of the remaining array

            // loop to find the index of the smallest element
            for (int index = i + 1; index < data.length; index++) {
                if (data[index] < data[smallest]) {
                    smallest = index;
                }
            }
            swap(data, i, smallest);
            printPass(data, i, smallest); // output pass of the algorithm
        }
    }

    private static void swap(int[] data, int first, int second) {
        int temporary = data[first]; // store first in temporary
        data[first] = data[second]; // replace first with second
        data[second] = temporary; // put temporary in second
    }

    // print a pass of the algorithm
    private static void printPass(int[] data, int pass, int index) {
        System.out.printf("after pass %2d: ", pass);

        // output elements until the selected item
        for (int i = 0; i < index; i++) {
            System.out.printf("%d ", data[i]);
        }

        System.out.printf("%d* ", data[index]); // indicate swap

        // finish outputting the array
        for (int i = index + 1; i < data.length; i++) {
            System.out.printf("%d ", data[i]);
        }

        System.out.printf("%n "); // for alignment

        // indicate the amount of the array that’s sorted
        for (int j = 0; j < pass; j++) {
            System.out.print("-- ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        SecureRandom generator = new SecureRandom();

        // create an unordered array of 10 random ints
        int[] data = generator.ints(10, 10, 91).toArray();

        System.out.printf("Unsorted array: %s%n%n", Arrays.toString(data));
        selectionSort(data);
        System.out.printf("%nSorted array: %s%n", Arrays.toString(data));
    }
}

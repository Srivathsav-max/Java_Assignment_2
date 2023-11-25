import com.deitel.datastructures.Queue;
import com.deitel.datastructures.List;

import java.util.NoSuchElementException;

public class QueueTest {
    public static void main(String[] args) {
        Queue<Integer> queue = new Queue<>();

        queue.enqueue(-1);
        queue.print();

        queue.enqueue(0);
        queue.print();

        queue.enqueue(1);
        queue.print();

        queue.enqueue(5);
        queue.print();

        boolean continueLoop = true;

        while (continueLoop) {
            try {
                int removedItem = queue.dequeue(); // remove head element
                System.out.printf("%n%d dequeued%n", removedItem);
            } catch (NoSuchElementException noSuchElementException) {
                continueLoop = false;
                noSuchElementException.printStackTrace();
            }
        }
    }
}

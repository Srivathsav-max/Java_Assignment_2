package questiong;

public class UnsynchronizedBuffer implements Buffer {
    private int buffer = -1; // shared by producer and consumer threads
    private boolean occupied = false; // whether buffer is occupied

    // place value into buffer
    public synchronized void blockingPut(int value) throws InterruptedException {
        // while there is data in the buffer, wait
        while (occupied) {
            wait();
        }

        buffer = value;
        occupied = true;
        System.out.printf("Producer writes\t%2d%n", value);

        // notify waiting threads that the buffer is now occupied
        notifyAll();
    }

    // return value from buffer
    public synchronized int blockingGet() throws InterruptedException {
        // while the buffer is empty, wait
        while (!occupied) {
            wait();
        }

        int readValue = buffer;
        occupied = false;
        System.out.printf("Consumer reads\t%2d%n", readValue);

        // notify waiting threads that the buffer is now empty
        notifyAll();

        return readValue;
    }
}

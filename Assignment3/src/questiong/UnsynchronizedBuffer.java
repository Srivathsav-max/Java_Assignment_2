package questiong;

public class UnsynchronizedBuffer implements Buffer {
	private int buffer = -1; 

 // place value into buffer
	@Override
	public void blockingPut(int value) throws InterruptedException {
		System.out.printf("Producer writes\t%2d", value);
		buffer = value;
} 

 // return value from buffer
	@Override
	public int blockingGet() throws InterruptedException {
		System.out.printf("Consumer reads\t%2d", buffer);
		return buffer;
 
 } } 

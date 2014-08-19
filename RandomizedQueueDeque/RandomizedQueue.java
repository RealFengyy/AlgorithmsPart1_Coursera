import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;


public class RandomizedQueue<Item> implements Iterable<Item> {
	
	/* instance variables */
	private int len;
	private Item[] rQueueArray;
	//int queueSize;
	
	
	/* methods */
	public RandomizedQueue() {
		// construct an empty randomized queue
		len = 0;
		// queueSize = 2;
		rQueueArray = (Item[]) new Object[2];
		
	}
	
	
	public boolean isEmpty() {
		// is the queue empty?
		return len == 0;
	}
	
	public int size() {
		// return the number of items on the queue
		return len;
		
	}
	public void enqueue(Item item) {
		// add the item
		if (item == null) {
			throw new NullPointerException();
		}
		if (len == rQueueArray.length) {
			resize(2 * rQueueArray.length);
		} 
		rQueueArray[len] = item;
		len++;
	}
	
	private void resize(int newSize) {
		assert newSize >= len;
		Item[] newRQueueArray = (Item[]) new Object[newSize];
		for (int i = 0; i < len; i++) {
			newRQueueArray[i] = rQueueArray[i];
		}
		rQueueArray = newRQueueArray;
		// queueSize = newSize;
	}
	
	
	public Item dequeue() {
		// delete and return a random item
		if (len < 1) {throw new NoSuchElementException(); }
		Item swapTemp = rQueueArray[len - 1];
		int index = StdRandom.uniform(len);
		Item retItem = rQueueArray[index];
		rQueueArray[index] = swapTemp;
		
		// avoid loitering
		rQueueArray[len - 1] = null;
		len--;
		
		// shrink the arraysize
		if (len > 0 && len == rQueueArray.length / 4) {
			resize(rQueueArray.length / 2);
		}
		return retItem;	
	}
	
	
	public Item sample() {
		// return (but do not delete) a random item
		if (len == 0) {
			throw new NoSuchElementException();
		}
		int index = StdRandom.uniform(len);
		return rQueueArray[index];
	}
	
	
	public Iterator<Item> iterator() {
		// return an independent iterator over items in random order
		return new randomizedQueueIterator();
	}
	
	private class randomizedQueueIterator implements Iterator<Item> {
		
		
		private int index;
		private Item[] rQACopy;
		private randomizedQueueIterator() {
			index = 0;
			rQACopy = rQueueArray.clone();
			//StdRandom.shuffle((Item[])rQACopy, 0, len - 1);
		}
		
		public boolean hasNext() {
			
			return index >= 0 && index <len;
		}
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
		public Item next() {
			if (hasNext() == false) {
				throw new NoSuchElementException();
			}	
			
			int swapIndex = StdRandom.uniform(len - index) + index;
			Item tempSwap = rQACopy[swapIndex];
			rQACopy[swapIndex] = rQACopy[index];
			rQACopy[index] = tempSwap;
			
			return rQACopy[index++];
 
		}
	}
	
	
	public static void main(String[] args) {
		// unit testing
		RandomizedQueue<Double> rq = new RandomizedQueue<Double>();
		
		// enque
		for (int i = 0; i < 10; i++) {
			rq.enqueue(i / 1.0);
		}
		
		// iterator test
		for (double num1 : rq) {
			StdOut.println("sample #1 " + num1);
	
			for (double num2 : rq) {
				StdOut.println("sample #2 " +num2);
			}
		}		
	}
}
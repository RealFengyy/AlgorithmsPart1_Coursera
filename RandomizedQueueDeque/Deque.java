import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item> {
	
	// inner class Node
	private class Node {
		private Item item;
		private Node prev;
		private Node next;
	}
	
	
	public Deque() {          
		// construct an empty deque
		first = null;
		last = null;	
		len = 0;
	}
   
	public boolean isEmpty() {                 
   		// is the deque empty?
		return len == 0;
	}
   
   
	public int size() {
		// return the number of items on the deque
		return len;
	}
   
   
	public void addFirst(Item item) {
   		// insert the item at the front
		if (item == null) {
			throw new NullPointerException();
		}
		if (len == 0) {
			first = new Node();
			last = first;
			first.item = item;
			first.prev = null;
			first.next = null;
		} else {
			Node temp = first;
			first = new Node();
			first.item = item;
			first.prev = null;
			first.next = temp;
			temp.prev = first;
		}
		len++;
	}
   
   
	public void addLast(Item item) {      
		// insert the item at the end
		if (item == null) {
			throw new NullPointerException();
		}
		if (len == 0) {
			last = new Node();
			first = last;
			last.item = item;
			last.prev = null;
			last.next = null;
		} else {
			Node temp = last;
			last = new Node();
			last.item = item;
			last.next = null;
			last.prev = temp;
			temp.next = last;
		}
		len++;
	}
   
   
	public Item removeFirst() {        
   		// delete and return the item at the front
		if (len == 0) {
			throw new NoSuchElementException("Deque is empty!");
		} 
		Item retItem;
		retItem = first.item;
		if (len < 2 ) {
			first = null;
			last = null;
		} else {
			first.next.prev = null;
			first = first.next;
		}
		len--;
	    return retItem;
	}
   
	public Item removeLast() {                
		// delete and return the item at the end
		if (len == 0) {
			throw new NoSuchElementException("Deque is empty!");
		}
		Item retItem;
		retItem = last.item;
		if (len < 2) {
			last = null;
			first = null;
		} else {
			last.prev.next = null;
			last = last.prev;
		}
		len--;
		return retItem;
	}
	
	
   	public Iterator<Item> iterator() {
	   // return an iterator over items in order from front to end
	   return new DequeIterator();
   	}
   
   	private class DequeIterator implements Iterator<Item> {
   		
   		private Node current = first;
   		public boolean hasNext() {
   			
   			// the current is the item to be returned
   			return current != null;
   		}
   		
   		public Item next() {
   			if (hasNext() == false) {
   				throw new NoSuchElementException();
   			}
   			Item tempItem = current.item;
   			current = current.next;
   			return tempItem;
   		}
   		
   		
   		public void remove() { 
   			throw new UnsupportedOperationException("DequeIterator doesn't support remove()!");
   		}
   	}
   
   	public static void main(String[] args) {
	   // unit testing
   		Deque<Integer> intDeque = new Deque<Integer>();
   		
   		// test empty
   		//StdOut.println(intDeque.removeFirst());
   		
   		for(int i = 0; i < 10; i++) {
   			intDeque.addFirst(i);
   			intDeque.addLast(i - 20);
   		}
   		
   		for (int readInt : intDeque) {
   			StdOut.println(readInt);
   		}
   	}
   	
   	// instance variables 
   	private Node first;
   	private Node last;
   	private int len;
}
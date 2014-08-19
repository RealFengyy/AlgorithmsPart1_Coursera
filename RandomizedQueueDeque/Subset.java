
public class Subset {

		public static void main(String[] args) {
			
			int k = Integer.parseInt(args[0]);
			
			RandomizedQueue<String> rQueue = new RandomizedQueue<String>();
			
			while(StdIn.isEmpty() == false) {
				String word = StdIn.readString();
				rQueue.enqueue(word);
			}
			
			if (k == 0) {
				return ;
			}
			
			for (int i = 0; i < k; i++) {
				String word = rQueue.dequeue();
				StdOut.println(word);
			}
			
		}
}

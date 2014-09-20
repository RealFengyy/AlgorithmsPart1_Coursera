


public class Solver {
	
	private boolean gIsSolvable;
	private MinPQ<Node> puzzlePQ;
	private MinPQ<Node> twinPuzzlePQ;
	private Node goalNode;
	
	
	private class Node implements Comparable<Node> {
		Board prev;
		Board cur;
		int move;
		Node prevNode;
		
		private Node(Board curB, Board prevB, int numMov, Node prevN) {
			prev = prevB;
			cur = curB;
			move = numMov;
			prevNode = prevN;
		}


		public int compareTo(Node nodeB) {
			return cur.manhattan() + move - (nodeB.cur.manhattan() + nodeB.move);
		}
	}
	
	
    public Solver(Board initial) {
    	
    	gIsSolvable = false;
    	goalNode = null;
    	// find a solution to the initial board (using the A* algorithm)
    	int globalMove = 0;
    	puzzlePQ = new MinPQ<Node>();     // PQ for the board
    	twinPuzzlePQ = new MinPQ<Node>(); // PQ for the twin of board
    	// initial board
    	Node initNode = new Node(initial, null, globalMove, null);
    	Node twinInitNode = new Node(initial.twin(), null, globalMove, null);
    	puzzlePQ.insert(initNode);
    	Node curNode = puzzlePQ.delMin();
    	twinPuzzlePQ.insert(twinInitNode);
    	Node curTwinNode = twinPuzzlePQ.delMin();
    	
    	while (false == curNode.cur.isGoal() && false == curTwinNode.cur.isGoal()) {
    		for (Board temp : curNode.cur.neighbors()) {
    			if (false == temp.equals(curNode.prev)) {
    				Node neighborNode = new Node(temp, curNode.cur, curNode.move + 1, curNode);
    				puzzlePQ.insert(neighborNode);
    			}
    		}
    		curNode = puzzlePQ.delMin();
    		for (Board temp : curTwinNode.cur.neighbors()) {
    			if (false == temp.equals(curTwinNode.prev)) {
    				twinPuzzlePQ.insert(new Node(temp, curTwinNode.cur, curTwinNode.move + 1, curTwinNode));
    			}
    		}
    		curTwinNode = twinPuzzlePQ.delMin();
    	}
    	if (true == curNode.cur.isGoal()) {
    		assert(false == curTwinNode.cur.isGoal());
    		gIsSolvable = true;
    		goalNode = curNode;  		 		
    	} 	
    }
    
    
    public boolean isSolvable() {
    	// is the initial board solvable?
    	return gIsSolvable;
    }
    
    
    public int moves() {
    	// min number of moves to solve initial board; -1 if no solution
    	if (true == gIsSolvable) {
    		assert(null != goalNode);
    		return goalNode.move;
    	}
    	return -1;
    }
    
    
    public Iterable<Board> solution() {
    	// sequence of boards in a shortest solution; null if no solution
    	if (true == gIsSolvable) {
    		assert(null != goalNode);
    		Stack<Board> traceStack = new Stack<Board>();
    		traceStack.push(goalNode.cur);
    		if (null == goalNode.prevNode) {
    			return traceStack;
    		}
    		Node prevNode = goalNode.prevNode;
    		while(null != prevNode) {
    			traceStack.push(prevNode.cur);
    			prevNode = prevNode.prevNode;
    		}
    		return traceStack;    		
    	}
    	return null;
    }
    
    
    
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        // solve the puzzle
        Solver solver = new Solver(initial);
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
    
}



public class Board {
	
	private char[][] curBoard;
	private int gHammingDist;
	private int gManhattanDist;
	
	
    public Board(int[][] blocks) {          
    	// construct a board from an N-by-N array of blocks
        // (where blocks[i][j] = block in row i, column j)
    	if (null == blocks) throw new NullPointerException();
    	assert(blocks.length <= 128 && blocks.length >= 2);
    	assert(blocks.length == blocks[0].length);
    	
    	gHammingDist = -1;
    	gManhattanDist = -1;
    	
    	curBoard = new char[blocks.length][blocks.length];
    	for(int i = 0; i < blocks.length; i++) {
    		for (int j = 0; j < blocks.length; j++) {
    			curBoard[i][j] = (char)blocks[i][j];
    		}
    	}

    }
    	
    
    private int[][] copyBlocks(char[][] fromBlocks) {
    	assert(null != fromBlocks);
    	int[][] toBlocks = new int[fromBlocks.length][fromBlocks.length];
    	for (int i = 0; i < fromBlocks.length; i++) {
    		for (int j = 0; j < fromBlocks.length; j++) {
    			toBlocks[i][j] = (int)fromBlocks[i][j];
    		}
    	}
    	return toBlocks;
    }
    
	
    public int dimension() {
    	// board dimension N
    	return curBoard.length;
    }
    
    
    public int hamming() {
    	// number of blocks out of place
    	if (null == curBoard) throw new NullPointerException();
    	
    	if (-1 == gHammingDist) {	
	    	int hammingDist = 0;
	    	for (int i = 0; i < curBoard.length; i++) {
	    		for (int j = 0; j < curBoard.length; j++) {		
	    			if (0 != curBoard[i][j] && curBoard[i][j] != curBoard.length * i + j + 1) {
	    				hammingDist++;
	    			}  
	    		}
	    	}
	    	gHammingDist = hammingDist;
    	} 
    	return gHammingDist;
    }
    
    
    public int manhattan() {
    	// sum of Manhattan distances between blocks and goal
    	if (null == curBoard) throw new NullPointerException();
    	int tempVal = 0;
    	if (-1 == gManhattanDist) {
	    	int manhattanDist = 0;
	    	for (int i = 0; i < curBoard.length; i++) {
	    		for (int j = 0; j < curBoard.length; j++) {
	    			if (0 != curBoard[i][j]) {
	    				tempVal = curBoard[i][j] - 1;
	    				manhattanDist += Math.abs(tempVal / curBoard.length - i) + \
	    				Math.abs(tempVal % curBoard.length - j);
	    			}
	    			
	    		}
	    	}
	    	gManhattanDist = manhattanDist;
    	}
   	
    	return gManhattanDist;
    }
    
    
    public boolean isGoal() {
    	// is this board the goal board?
    	return 0 == hamming() && 0 == manhattan();
    }
    
    
    public Board twin() {
    	// a board obtained by exchanging two adjacent blocks in the same row
    	if (null == curBoard) throw new NullPointerException();
    	int [][] twinBlocks = new int[curBoard.length][curBoard.length];
    	for (int i = 0; i < curBoard.length; i++) {
    		for (int j = 0; j < curBoard.length; j++) {
    			twinBlocks[i][j] = curBoard[i][j];
    		}
    	}
      	int temp = 0;
    	if (0 == twinBlocks[0][0] || 0 == twinBlocks[0][1]) {
    		// swap the next row
    		temp = twinBlocks[1][0];
    		twinBlocks[1][0] = twinBlocks[1][1];
    		twinBlocks[1][1] = temp;
    	} else {
    		temp = twinBlocks[0][0];
    		twinBlocks[0][0] = twinBlocks[0][1];
    		twinBlocks[0][1] = temp;
    	}
    	return new Board(twinBlocks);
    
    }
    
    
    
    public boolean equals(Object y) {
    	// does this board equal y?
    	if (this == y) return true;
    	if (null == y) return false;
    	if (this.getClass() != y.getClass()) return false;
    	Board yy = (Board)y;
    	if (yy.curBoard.length != yy.curBoard.length || yy.curBoard.length != curBoard.length) return false;
    	for (int i = 0; i < yy.curBoard.length; i++) {
    		for (int j = 0; j < yy.curBoard.length; j++) {
    			if (yy.curBoard[i][j] != curBoard[i][j]) {
    				return false;
    			}
    		}
    	}
    	return true;
    }
    
    
    public Iterable<Board> neighbors() {
    	// all neighboring boards
    	Queue<Board> neighborQ = new Queue<Board>();
    	
    	int iZero = 0;
    	int jZero = 0;
    	
    	outerloop:
    	for (int i = 0; i < curBoard.length; i++) {
    		for (int j = 0; j < curBoard.length; j++) {
    			if (0 == curBoard[i][j]) {
    				iZero = i;
    				jZero = j;
    				break outerloop;
    			}
    		}
    	}
     	Board upperMove = null;
    	// move from upper loc available
    	if (0 != iZero) {
    		int[][] upperBlocks = copyBlocks(curBoard);
    		upperBlocks[iZero][jZero] = upperBlocks[iZero - 1][jZero];
    		upperBlocks[iZero - 1][jZero] = 0;
    		upperMove = new Board(upperBlocks);

    	}
    	Board lowerMove = null;
    	// move from lower loc available
    	if (curBoard.length - 1 != iZero) {
    		int[][] lowerBlocks = copyBlocks(curBoard);
    		lowerBlocks[iZero][jZero] = lowerBlocks[iZero + 1][jZero];
    		lowerBlocks[iZero + 1][jZero] = 0;
    		lowerMove = new Board(lowerBlocks);
    	}
    	
    	Board leftMove = null;
    	// move from left loc available
    	if (0 != jZero) {
    		int[][] leftBlocks = copyBlocks(curBoard);
    		leftBlocks[iZero][jZero] = leftBlocks[iZero][jZero - 1];
    		leftBlocks[iZero][jZero - 1] = 0;
    		leftMove = new Board(leftBlocks);
    		
    	}
    	Board rightMove = null;
    	// move from right loc available
    	if (curBoard.length - 1 != jZero) {
    		int[][] rightBlocks = copyBlocks(curBoard);
    		rightBlocks[iZero][jZero] = rightBlocks[iZero][jZero + 1];
    		rightBlocks[iZero][jZero + 1] = 0;
    		rightMove = new Board(rightBlocks);
    	}
    	if (null != upperMove) neighborQ.enqueue(upperMove);
    	if (null != lowerMove) neighborQ.enqueue(lowerMove);
    	if (null != leftMove) neighborQ.enqueue(leftMove);
    	if (null != rightMove) neighborQ.enqueue(rightMove);
    	return neighborQ;
    }
    
    
    public String toString() {
    	// string representation of the board (in the output format specified below)
        StringBuilder s = new StringBuilder();
        s.append(curBoard.length + "\n");
        for (int i = 0; i < curBoard.length; i++) {
            for (int j = 0; j < curBoard.length; j++) {
                s.append(String.format("%2d ", (int)curBoard[i][j]));
            }
            s.append("\n");
        }
    	return s.toString();
    }
}


/*
 * percolation class, abstract the percolation process
 */

public class Percolation {

	// instance variables
	private int size; // size of the sites, plus 2 virtual sites
	private int gridLen; // length of the square
	private boolean openTable[][]; // indicates whether a site is open
	private WeightedQuickUnionUF unionTable;  //union object with 2 virtual sites
	private WeightedQuickUnionUF unionTableBW; //union object witho only virtual site at the top, preventing backwash
	
	/*
	 * converts 2D coordinates to 1D index
	 */
	private int matrix2Ind(int row, int col) {
		
		return (row - 1) * gridLen + (col - 1);
	}
	
	private boolean isValidIndex(int i, int j) {
		
		if(i < 1 || i > gridLen || j < 1 || j > gridLen) {
			throw new IndexOutOfBoundsException();
		}
		return true;
	}
	
	
	/*
	 * constructor
	 */
	public Percolation(int N) {
	// create N-by-N grid, with all sites blocked
		if (N <= 0) {
			throw new IllegalArgumentException();
		}
		gridLen = N;
		size = N * N + 2;
		//StdOut.println(size);
		unionTable = new WeightedQuickUnionUF(size);
		unionTableBW = new WeightedQuickUnionUF(size - 1);
		openTable = new boolean[gridLen + 1][gridLen + 1];
	
		for(int i = 0; i < gridLen; i++) {
			unionTable.union(i, size - 2); // virtual site at the top
			unionTableBW.union(i, size - 2);
			unionTable.union(size - 3 - i, size - 1); // virtual site at the botton
			//statTable[i] = true;
			//unionTable.union()
		}
		for(int i = 0; i < gridLen + 1; i++) {
			for(int j = 0; j < gridLen + 1; j++) {
				openTable[i][j] = false;
			}
		}
	}
	
	
	/*
	 * open a site indicated by the coordinate
	 */
	public void open(int i, int j) {
		// open site (row i, column j) if it is not already
		isValidIndex(i, j);
		int ind = matrix2Ind(i, j);
		if(!isOpen(i,j)) {
			openTable[i][j] = true;
		}
		if(gridLen == 1) {
			return;
		}
		else {
			
			if(i != 1) {
				if(isOpen(i - 1, j)) {// && !unionTable.connected(ind, ind - gridLen)) {
					unionTable.union(ind, ind - gridLen);
					unionTableBW.union(ind, ind - gridLen);
				}
			}
			if(i != gridLen) {
				if(isOpen(i + 1, j)) {// && !unionTable.connected(ind, ind + gridLen)) {
					unionTable.union(ind, ind + gridLen);
					unionTableBW.union(ind, ind + gridLen);
				}
			}
			if(j != 1) {
				if(isOpen(i, j - 1)) {// && !unionTable.connected(ind, ind - 1)) {
					unionTable.union(ind, ind - 1);
					unionTableBW.union(ind, ind - 1);
				}
			}
			if(j != gridLen) {
				if(isOpen(i, j + 1)) {//&& !unionTable.connected(ind, ind + 1)) {
					unionTable.union(ind, ind + 1);
					unionTableBW.union(ind, ind + 1);
				}
			}
		}
   }
	
   /*
    * judge whether a site is open
    */
   public boolean isOpen(int i, int j) {
	   // is site (row i, column j) open?
	   isValidIndex(i, j);
	   return openTable[i][j];
   }
   
   
   /*
    * judge whether a site is connected to the virtual top site
    */
   public boolean isFull(int i, int j) {
	   // is site (row i, column j) full?
	   isValidIndex(i, j);
	   int oneDInd = matrix2Ind(i, j);
	   //boolean stat = openTable[i][j];
	   return isOpen(i,j) && unionTableBW.connected(oneDInd, size - 2);
   }
   
   /*
    * judge whether a union percolates
    */
   public boolean percolates() {
	   // does the system percolate?
	   if(gridLen != 1) {
		   if (unionTable.connected(size - 2, size - 1)) {
			   return true;
		   }
		   else 
			   return false;
	   }
	   else  return isOpen(1,1);
   }

   
}

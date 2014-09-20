
/*
 * class for percolation statistic computation
 */
public class PercolationStats {
	// instance variables
	private int N; // grid size
	private int T; // number of tests to perform
	private double[] thData; // holds threhold data
		
  /*
   * constructor
   */
   public PercolationStats(int N, int T) {
	   // perform T independent computational experiments on an N-by-N grid
	   if(N <= 0 || T <= 0) {
		   throw new IllegalArgumentException();
	   }
	   //this.N = N;
	   this.T = T;
	   this.N = N;
	   thData = new double[T];
	   mcSimulation();
   }
   
   
   /*
    * get the average threshold
    */
   public double mean() {
	   double sum = 0.0;
	   for(int i = 0; i < thData.length; i++) {
		   sum += thData[i];	   
	   }
	   return sum/thData.length;
   }
   
   
   /*
    * get the standard deviation
    */
   public double stddev() {
	   // sample standard deviation of percolation threshold
	   double mean = this.mean();
	   double sum = 0.0;
	   for(int i = 0; i< thData.length; i++) sum += (thData[i] - mean) * (thData[i] - mean);
	   return Math.sqrt(sum / (thData.length - 1));
   }
   
   
   /*
    * get the lower bound of the 95% confidence interval
    */
   public double confidenceLo() {
	   // returns lower bound of the 95% confidence interval
	   double mean = this.mean();
	   double sd = this.stddev();
	   return mean - 1.96 * sd / Math.sqrt(thData.length);
	   //return cLow;
   }
   
   
   /*
    * get the upper bound of the 95% confidence intervel
    */
   public double confidenceHi() {
	   // returns upper bound of the 95% confidence interval
	   double mean = this.mean();
	   double sd = this.stddev();
	   return mean + 1.96 * sd / Math.sqrt(thData.length);
   }
   
   
   private void mcSimulation() {
	   Percolation testPDriver;
	   // T simulations
	   for(int i = 0; i < T; i++) {
		   testPDriver = new Percolation(N);
		   int openSite = 0;
		   while(!(openSite == N * N || testPDriver.percolates())) {
			   int row = StdRandom.uniform(1, N + 1);
			   int col = StdRandom.uniform(1, N + 1);
			   if(!testPDriver.isOpen(row, col)) {
				   testPDriver.open(row, col);
				   openSite++;
			   }
		   }
		   if(openSite != N * N) {
			   thData[i] = ((double)(openSite))/(N * N);
		   } 
	   }
   }
   
   
   /*
    * test driver, parse command line argument and print the ouput to the standard ouput
    */
   public static void main(String[] args) {
	   // test client, described below
	   int gridSize = Integer.parseInt(args[0]);
	   int testNum = Integer.parseInt(args[1]);
	   PercolationStats pStat = new PercolationStats(gridSize, testNum);
	   pStat.mcSimulation();
	   double mean = pStat.mean();
	   double sd = pStat.stddev();
	   double cLow = pStat.confidenceLo();
	   double cHigh = pStat.confidenceHi();
	   StdOut.println("mean                    = " + mean);
	   StdOut.println("stddev                  = " + sd);
	   StdOut.println("95% confidence interval = " + cLow + ", " + cHigh);
   }
   
   
}

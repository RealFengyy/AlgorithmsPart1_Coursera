import java.util.Scanner;
import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;


public class Fast {

	
	public static void main(String[] args) throws FileNotFoundException{
		// set the window
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
	
		if (null != args) {
	
			Scanner in = new Scanner(new File(args[0]));
			int N = in.nextInt();
			Point pointArr[] = new Point[N];
			Point pointArrCopy[] = new Point[N];
			int i = 0;
			while (in.hasNextLine() && in.hasNextInt()) {
				int x = in.nextInt();
				int y = in.nextInt();
				pointArr[i] = new Point(x, y);
				pointArrCopy[i] = new Point(x, y);
 				i++;
			}
			assert (i == N);
			in.close();

			double pvSlopeArr[] = new double[pointArr.length];	
			doDrawPoints(pointArr);
			doFast(pointArr, pointArrCopy, pvSlopeArr);
	
		}
		
	}
	
	
	
	/*
	public static void main(String[] args) {
		 StdDraw.setXscale(0, 32768);
		 StdDraw.setYscale(0, 32768);
		 
		 int N = StdIn.readInt();
		 Point pointArr[] = new Point[N];
			Point pointArrCopy[] = new Point[N];
			int i = 0;
			while (StdIn.hasNextLine() && StdIn.hasNextChar()) {
				int x = StdIn.readInt();
				int y = StdIn.readInt();
				pointArr[i] = new Point(x, y);
				pointArrCopy[i] = new Point(x, y);
				i++;
			}
			double pvSlopeArr[] = new double[pointArr.length];	
			doDrawPoints(pointArr);
			doFast(pointArr, pointArrCopy, pvSlopeArr);
		 
	}
	*/
	
	
	
	


	
	
	
	private static void doFast(Point[] ptArr, Point ptArrCopy[], double[] sArr) {
		assert(ptArr.length == sArr.length);
		for (int i = 0; i < ptArrCopy.length; i++) {
			// for each point in the ptArr, select as pivot point
			Point pvt =ptArrCopy[i];
			// generate the slope array with pvt as the pivot, sort the ptArr accordingly
			Arrays.sort(ptArr, pvt.SLOPE_ORDER);
			for (int j = 0; j < sArr.length; j++) {
				sArr[j] = pvt.slopeTo(ptArr[j]);
			}
			// iterate through the sArr array, found the consecutive entries with same slope (other than -infinity)
			// get the start and end index
			int start = 0; 
			int end = 0;
			int thisRun = 1;
			int lastRun = 1;
			
			for (int k = 1; k < sArr.length; k++) {
				if (sArr[k] > Double.NEGATIVE_INFINITY) {
					
					if (sArr[k - 1] == sArr[k]) {
						lastRun = thisRun++;
						if (thisRun >= 3 && k == sArr.length - 1) {
							start = k - thisRun + 1;
							end = k;
						    // StdOut.println("start: " + start + ", end: " + end + ", slope: " + sArr[start]);
							Arrays.sort(ptArr, start, end + 1);
							if (pvt.compareTo(ptArr[start]) < 0) {
								drawLinesNPrint(ptArr, pvt, start, end);
							}
						}
						
					} else {
						lastRun = thisRun;
						thisRun = 1;
						if (lastRun >= 3) {
							// get the region, update start and end, 
							start = k - lastRun;
							end = k - 1;
							Arrays.sort(ptArr, start, end + 1);
							if (pvt.compareTo(ptArr[start]) < 0) {
								// iff pvt is the smallest point, draw
								drawLinesNPrint(ptArr, pvt, start, end);
							}		
						}
					}
				}							
			}		
		}
	}
	
	
	private static void drawLinesNPrint(Point[] ptArr, Point pvt, int start, int end) {
		
		String out = pvt + " -> ";		
		for(int i = start; i < end; i++) {
			
			out = out + ptArr[i] + " -> ";
		}
		out = out + ptArr[end];
		pvt.drawTo(ptArr[end]);
		StdOut.println(out);
	}
	
	
	private static void doDrawPoints(Point[] ptArr) {
		
		for (Point pt : ptArr) {
			pt.draw();
		}
	}
	
}	

	


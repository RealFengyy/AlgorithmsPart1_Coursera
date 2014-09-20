import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class Brute {
	
	public static void main (String[] args) throws FileNotFoundException{

		/* for debug in eclipse */		
		if (null != args) {
			Scanner in = new Scanner(new File(args[0]));
			int N = in.nextInt();	
			Point pointArr[] = new Point[N];
			int i = 0;
			// StdOut.println("# of pts:" + pointArr.length);
			while (in.hasNextLine() && in.hasNextInt()) {
				int x = in.nextInt();
				int y = in.nextInt();
				pointArr[i] = new Point(x, y);
				i++;
			}
			assert (i == N);
	
			Arrays.sort(pointArr);
            
			// set window
			StdDraw.setXscale(0, 32768);
			StdDraw.setYscale(0, 32768);
			
			for (int i1 = 0; i1 < pointArr.length; i1++) {
				Point p = pointArr[i1];
				p.draw();
				for (int j = i1 + 1; j < pointArr.length; j++) {
					Point q = pointArr[j];
					for (int k = j + 1; k < pointArr.length; k++) {
						Point r = pointArr[k];						
						if (p.slopeTo(q) == q.slopeTo(r)) {
							for (int l = k + 1; l < pointArr.length; l++) {
								Point s = pointArr[l];
								if (p.slopeTo(q) == q.slopeTo(r) &&
									q.slopeTo(r) == r.slopeTo(s)) {
									StdOut.println(p + " -> " + q + " -> " + r + " -> " + s);
									p.drawTo(s);
								}
							}
						}
					}
				}
			}			
			in.close();
			return;		
		}  
	}


}

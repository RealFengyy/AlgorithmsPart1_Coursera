import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new SlopeComparator();       // YOUR DEFINITION HERE

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
    	if (that == null) {
    		throw new NullPointerException();
    	}
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
    	
    	if (that == null) {
    		throw new NullPointerException();
    	}
    	
    	double slope = 0.0;
    	double deltaY = that.y - this.y;
    	double deltaX = that.x - this.x;
    	
    	
    	if (deltaX == 0.0 && deltaY == 0.0) {
    		slope = Double.NEGATIVE_INFINITY;
    	} else if (deltaX == 0) {
    		slope = Double.POSITIVE_INFINITY;
    	} else if (deltaY == 0) {
    		slope = +0.0;
    	} else {
    		slope = deltaY / deltaX;
    	}
    	return slope;
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
    	
        /* YOUR CODE HERE */
    	if (that == null) {
    		throw new NullPointerException();
    	}
    	
    	int retVal = 0;
    	if (this.y < that.y || (this.y == that.y && this.x < that.x)) {
    		retVal = -1;
    	}
    	else if (this.y == that.y && this.x == that.x) {
    		retVal = 0;
    	} else {
    		retVal = 1;
    	}
    	return retVal;
    	
    }

    private class SlopeComparator implements Comparator<Point> {
    	public int compare(Point a, Point b) {
    		
    		// return (int) (Point.this.slopeTo(a) - Point.this.slopeTo(b));
    		if (a == null || b == null) { throw new NullPointerException(); }
    		double sa = Point.this.slopeTo(a);
    		double sb = Point.this.slopeTo(b);
    		if (sa == sb) {
    			return 0;
    		} else if (sa < sb) {
    			return -1;
    		} else {
    			return 1;
    		}
    	}
    }
    

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    	Point p1 = new Point(0,0);
    	Point p2 = new Point(2,2);
    	Point p3 = new Point(2,3);
    	// Point p4 = new Point(3,2);
    	StdOut.println("cmp " + p3 + " with " + p2 + " : " + (p1.slopeTo(p3) - p1.slopeTo(p2)));
    	
    }
}
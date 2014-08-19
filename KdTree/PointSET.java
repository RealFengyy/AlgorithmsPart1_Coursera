
public class PointSET {
   
   // instance variables
   private SET<Point2D> ptSet;	
	
   public PointSET() {
	   // construct an empty set of points
	   ptSet = new SET<Point2D>();
   }
   public boolean isEmpty() {
	   // is the set empty?
	   return ptSet.isEmpty();
   }
   public int size() {
	   // number of points in the set
	   return ptSet.size();
   }
   public void insert(Point2D p) {
	   // add the point p to the set (if it is not already in the set)
	   ptSet.add(p);
   }
   public boolean contains(Point2D p) {
	   // does the set contain the point p?
	   return ptSet.contains(p);
   }
   public void draw() {
	   // draw all of the points to standard draw
	   for (Point2D pNode : ptSet) {
		   pNode.draw();
	   }
   }
   public Iterable<Point2D> range(RectHV rect) {
	   // all points in the set that are inside the rectangle
	   Queue<Point2D> retQ = new Queue<Point2D>();
	   for (Point2D pNode : ptSet) {
		   if (rect.contains(pNode)) retQ.enqueue(pNode);
	   }
	   return retQ;
	   
   }
   public Point2D nearest(Point2D p) {
	   // a nearest neighbor in the set to p; null if set is empty
	   if (ptSet.isEmpty()) return null;
	   Point2D nearestPNode = null;
	   double distSquare = Double.MAX_VALUE;
	   for(Point2D pNode : ptSet) {
		   double tempDist = (pNode.x() - p.x()) * (pNode.x() - p.x()) + (pNode.y() - p.y()) * (pNode.y() - p.y());
		   if (distSquare > tempDist) {
			   distSquare = tempDist;
			   nearestPNode = pNode;
		   }
	   }
	   assert(nearestPNode != null);
	   return nearestPNode;   	
   }
}
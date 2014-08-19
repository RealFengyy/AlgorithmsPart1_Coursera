
public class KdTree {
	
	
   private static class NearesetTuple {
	   private Point2D p;
	   private double minDist;
	   private NearesetTuple() {
		   p = null;
		   minDist = Double.MAX_VALUE;
	   }
   }
	
   private static class PNode {
	   private Point2D p;      // the point
	   private RectHV rect;    // the axis-aligned rectangle corresponding to this node
	   private PNode lb;        // the left/bottom subtree
	   private PNode rt;        // the right/top subtree
	   
	   private PNode(Point2D p, RectHV rect) {
		   if (null == p || null == p) throw new NullPointerException();
		   this.p = p;
		   this.rect = rect;
		   this.lb = null;
		   this.rt = null;
	   }
	   

	   private double kdComparedTo(PNode pNode, boolean onX) {
		   if (true == onX) {
			   return this.p.x() - pNode.p.x();
		   }
		   return this.p.y() - pNode.p.y();
	   }
   }
   
   
   // Instance variables
   private int size;   // size of kdTree
   private PNode root; // root node

   public KdTree() {
	   // construct an empty set of points
	   size = 0;
	   root = null;
	  
   }
   public boolean isEmpty() {
	   // is the set empty?
	   return 0 == size;
   }
   public int size() {
	   // number of points in the set
	   return size;
   }
   
   public void insert(Point2D p) {
	   // add the point p to the set (if it is not already in the set)
	   if (null == p) throw new NullPointerException(); 
	   
	   if (null == root) {
		   size++;
		   root = new PNode(p, new RectHV(0, 0, 1, 1));
	   }
	   else {
		   if (!contains(p)) {
			   PNode newNode= new PNode(p, null);
			   double cmpResult = newNode.kdComparedTo(root, true);
			   if (cmpResult < 0) root.lb = recurseInsert(newNode, root.lb, root, false, true);
			   else root.rt = recurseInsert(newNode, root.rt, root, false, false);
			   size++;
		   }
	   }
	
   }
   
   public boolean contains(Point2D p) {
	   // does the set contain the point p?
	   PNode newNode = new PNode(p, null);
	   return null != recurseGet(newNode, root, true);
   }
   
   
   public void draw() {
	   // draw all of the points to standard draw	   
	   recurseDrawWithRectHV(root, true);
   
   }
   public Iterable<Point2D> range(RectHV rect) {
	   // all points in the set that are inside the rectangle	   
	   Queue<Point2D> ptQ = new Queue<Point2D>();
	   recurseRange(root, rect, ptQ);    
	   return ptQ;
   }
   
   
   public Point2D nearest(Point2D p) {
	   
	   // a nearest neighbor in the set to p; null if set is empty
	   if (null == root) return null;
	   NearesetTuple tuple = new NearesetTuple();	   
	   tuple.p = root.p;
	   tuple.minDist = p.distanceSquaredTo(tuple.p);	   
	   
	   if (queryOnLeftBottom(root, p, true)) {
		   tuple = recurseNearestPruning(root.lb, p, tuple, false);
		   tuple = recurseNearestPruning(root.rt, p, tuple, false);
	   } else {
		   tuple = recurseNearestPruning(root.rt, p, tuple, false);
		   tuple = recurseNearestPruning(root.lb, p, tuple, false);
	   }
	   return tuple.p;   
   }
   
   
   private static NearesetTuple recurseNearestPruning(PNode node, Point2D pt, NearesetTuple tuple, boolean onX) {
	   if (null == node) return tuple;
	   
	   
	   double nodeDist = node.p.distanceSquaredTo(pt);
	   if (nodeDist < tuple.minDist) {
		   
		   tuple.p = node.p;
		   tuple.minDist = nodeDist;
	   }
	   
	   double recDist = node.rect.distanceSquaredTo(pt);
	   if (recDist > tuple.minDist) return tuple;
	   
	   if (queryOnLeftBottom(node, pt, onX)) {
		   tuple = recurseNearestPruning(node.lb, pt, tuple, !onX);
		   tuple = recurseNearestPruning(node.rt, pt, tuple, !onX);
	   } else {
		   tuple = recurseNearestPruning(node.rt, pt, tuple, !onX);
		   tuple = recurseNearestPruning(node.lb, pt, tuple, !onX);
	   }
	   return tuple;
   }
   
   private static boolean queryOnLeftBottom(PNode node, Point2D pt, boolean onX) {
	   
	   if (onX) {
		   if (pt.x() < node.p.x())  return true;
		   return false;
	   } else {
		   if (pt.y() < node.p.y()) return true;
		   return false;
	   }
   }
   
  
   
   private static void recurseRange(PNode node, RectHV rangeRect, Queue<Point2D> ptQ) {
	   
	   if (null == node || !rangeRect.intersects(node.rect)) return;
	   if (rangeRect.contains(node.p)) ptQ.enqueue(node.p);
	   recurseRange(node.lb, rangeRect, ptQ);
	   recurseRange(node.rt, rangeRect, ptQ);
	   
	   
   }
   

   private static RectHV setRectHV(PNode parentNode, boolean onX, boolean goLeft) {
	   if (true == onX && true == goLeft) return new RectHV(parentNode.rect.xmin(),
			                                parentNode.rect.ymin(),
			                                parentNode.p.x(),
			                                parentNode.rect.ymax());
	   if (true == onX && false == goLeft) return new RectHV(parentNode.p.x(),
			   								parentNode.rect.ymin(),
			   								parentNode.rect.xmax(),
			   								parentNode.rect.ymax());
	   if (false == onX && true == goLeft) return new RectHV(parentNode.rect.xmin(),
			   								parentNode.rect.ymin(),
			   								parentNode.rect.xmax(),
			   								parentNode.p.y());
	   
	   // false == onX && false == goLeft
	   return new RectHV(parentNode.rect.xmin(),
			   		parentNode.p.y(),
			   		parentNode.rect.xmax(),
			   		parentNode.rect.ymax());
			   
			   	
   }
   
   private static PNode recurseInsert(PNode insertNode, PNode node, PNode parentNode, boolean onX, boolean goLeft) {
	   
	   assert(null != insertNode);	   
	   if (null == node)  return new PNode(insertNode.p, setRectHV(parentNode, !onX, goLeft));
	      
	   
	   double cmpResult = insertNode.kdComparedTo(node, onX);
	   
	   if (cmpResult < 0)  node.lb = recurseInsert(insertNode, node.lb, node, !onX, true);
	   else node.rt = recurseInsert(insertNode, node.rt, node, !onX, false);
	   return node;

   }
   
   private static PNode recurseGet(PNode nodeToFind, PNode node, boolean onX) {
	   assert(null != nodeToFind);
	   if (null == node) return null;
	   
	   double cmpResult = nodeToFind.kdComparedTo(node, onX);
	   if (cmpResult < 0)  return recurseGet(nodeToFind, node.lb, !onX);
	   else {
		   if (nodeToFind.p.x() == node.p.x() && nodeToFind.p.y() == node.p.y()) return node;
		   return recurseGet(nodeToFind, node.rt, !onX);
	   }

   }

   
   private static void recurseDrawWithRectHV(PNode node, boolean onX) {
	   if (null == node) return;
	   if (onX) {
		   doDraw(node, node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax(), onX);
		   recurseDrawWithRectHV(node.lb, !onX);
		   recurseDrawWithRectHV(node.rt, !onX);
	   } else {
		   doDraw(node, node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y(), onX);
		   recurseDrawWithRectHV(node.lb, !onX);
		   recurseDrawWithRectHV(node.rt, !onX);
	   }
   }

   private static void doDraw(PNode dNode, double x0, double y0, double x1, double y1, boolean onX) {
	   StdDraw.setPenRadius();
	   if (onX) StdDraw.setPenColor(StdDraw.RED);
	   else StdDraw.setPenColor(StdDraw.BLUE);
	   
	   StdDraw.line(x0, y0, x1, y1);
	   
	   StdDraw.setPenRadius(.01);
	   StdDraw.setPenColor(StdDraw.BLACK);
	   dNode.p.draw();
	   
   }

}
/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Iterator;
import java.util.LinkedList;

public class PointSET {

    private SET<Point2D> pointSet;

    // construct an empty set of points
    public PointSET() {
        pointSet = new SET<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return pointSet.size() == 0;
    }

    // number of points in the set
    public int size() {
        return pointSet.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Null point!");
        pointSet.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Null point!");
        return pointSet.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        StdDraw.setPenRadius(0.05);
        StdDraw.setPenColor(StdDraw.BLUE);
        for (Iterator<Point2D> it = pointSet.iterator(); it.hasNext(); ) {
            Point2D p = it.next();
            StdDraw.point(p.x(), p.y());
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Null rect!");
        LinkedList<Point2D> points = new LinkedList<Point2D>();
        for (Iterator<Point2D> it = pointSet.iterator(); it.hasNext(); ) {
            Point2D p = it.next();
            if (rect.contains(p)) points.add(p);
        }
        return points.size() == 0? null: points;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Null point!");
        Point2D nearest = null;
        for (Iterator<Point2D> it = pointSet.iterator(); it.hasNext(); ) {
            Point2D q = it.next();
            if (nearest == null) {
                nearest = q;
                continue;
            }
            if (p.distanceTo(nearest) > p.distanceTo(q)) nearest = q;
        }
        return nearest;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}

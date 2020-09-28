/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;

public class KdTree {

    private Node root;

    private class Node implements Comparable<Node> {
        private boolean xKey;
        private Point2D point;
        private int count;
        private Node left, right;

        public Node (Point2D point, boolean xKey) {
            this.point = point;
            this.xKey = xKey;
        }

        public int compareTo(Node that) {
            if (this.xKey) {
                return Double.compare(this.point.x(), that.point.x());
            } else {
                return Double.compare(this.point.y(), that.point.y());
            }
        }
    }

    // construct an empty set of points
    public KdTree() {

    }

    // is the set empty?
    public boolean isEmpty() {
        return (size() == 0);
    }

    private int size(Node n) {
        if (n == null) return 0;
        else return n.count;
    }

    // number of points in the set
    public int size() {
        return size(root);
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Null point!");
        root = insert(root, true, p);
    }

    private Node insert(Node x, boolean xKey, Point2D p) {
        if (x == null) {
            x = new Node(p, xKey);
            x.count = 1;
            return x;
        }
        Node pointNode = new Node(p, xKey);
        int cmp = x.compareTo(pointNode);
        if (cmp > 0) x.left = insert(x.left, !x.xKey, p);
        else if (cmp < 0) x.right = insert(x.right, !x.xKey, p);
        else if (!x.point.equals(p)) x.left = insert(x.left, !x.xKey, p);
        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Null point!");
        Node x = root;
        Node pointNode = new Node(p, true);
        while (x != null) {
            int cmp = x.compareTo(pointNode);
            if (cmp > 0) x = x.left;
            else if (cmp < 0) x = x.right;
            else {
                if (x.point.equals(p)) return true;
                else x = x.left;
            }
        }
        return false;
    }

    // draw all points to standard draw
    public void draw() {
        StdDraw.setPenRadius(0.05);
        StdDraw.setPenColor(StdDraw.BLUE);
        draw(root);
    }

    private void draw(Node x) {
        if (x == null) return;
        draw(x.left);
        StdDraw.point(x.point.x(), x.point.y());
        draw(x.right);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Null rect!");
        LinkedList<Point2D> points = new LinkedList<Point2D>();
        Node x =  root;
        range(points, rect, x);
        return points.size() == 0? null: points;
    }

    private void range(LinkedList<Point2D> points, RectHV rect, Node x) {
        if (x == null) return;
        if (inRect(x.point, rect)) points.add(x.point);
        if (x.xKey) {
            if (x.point.x() >= rect.xmin()) {
                range(points, rect, x.left);
            }
            if (x.point.x() <= rect.xmax()) {
                range(points, rect, x.right);
            }
        } else {
            if (x.point.y() >= rect.ymin()) {
                range(points, rect, x.left);
            }
            if (x.point.y() <= rect.ymax()) {
                range(points, rect, x.right);
            }
        }
    }

    private boolean inRect(Point2D point, RectHV rect) {
        if (point.x() <= rect.xmax() && point.x() >= rect.xmin()) {
            if (point.y() <= rect.ymax() && point.y() >= rect.ymin()) {
                return true;
            }
        }
        return false;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Null point!");
        if (isEmpty()) return null;
        Node x = root;
        Node nearest = nearest(x, x, p);
        return nearest.point;
    }

    private Node nearest(Node x, Node nearest, Point2D p) {
        if (x == null) return nearest;
        // StdOut.println("Where: " + x.point);
        if (x.point.equals(p)) return x;
        if (p.distanceTo(nearest.point) > p.distanceTo(x.point)) {
            // StdOut.println("DEBUG: Updating Nearest from " + nearest.point + " to " + x.point);
            nearest = x;
        }
        Node pNode = new Node(p, true);
        if (x.compareTo(pNode) >= 0) {
            // StdOut.println("DEBUG: " + x.point + " Going left");
            nearest = nearest(x.left, nearest, p);
        }
        if (x.compareTo(pNode) <= 0) {
            // StdOut.println("DEBUG: " + x.point + " Going right");
            nearest = nearest(x.right, nearest, p);
        }

        if (x.xKey) {
            double cmp = (x.point.y() - p.y()) - p.distanceTo(x.point);
            if (cmp < 0) {
                // StdOut.println("DEBUG: " + x.point + " Going right");
                // StdOut.print(x.compareTo(pNode));
                if (x.compareTo(pNode) > 0) nearest = nearest(x.right, nearest, p);
                else nearest = nearest(x.left, nearest, p);
            }
        } else {
            double cmp = (x.point.x() - p.x()) - p.distanceTo(x.point);
            if (cmp < 0) {
                // StdOut.println("DEBUG: " + x.point + " Going left");
                if (x.compareTo(pNode) > 0) nearest = nearest(x.right, nearest, p);
                else nearest = nearest(x.left, nearest, p);
            }
        }
        return nearest;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        KdTree tree = new KdTree();
        while(!StdIn.isEmpty()) {
            Double x = StdIn.readDouble();
            Double y = StdIn.readDouble();
            tree.insert(new Point2D(x, y));
        }

        StdOut.println("\n nearest");
        Point2D p5 = new Point2D(0.7, 0.652);
        StdOut.println(tree.nearest(p5));

        tree.draw();
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.point(p5.x(), p5.y());
    }
}

// 0.372 0.497
//         0.564 0.413
//         0.226 0.577
//         0.144 0.179
//         0.083 0.51
//         0.32 0.708
//         0.417 0.362
//         0.862 0.825
//         0.785 0.725
//         0.499 0.208

/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    /*
    * Finds all line segments containing 4 points
     */

    private ArrayList<LineSegment> segments = new ArrayList<LineSegment>();
    private Point[] copy;
    private int number = 0;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Null argument!");
        }

        if (Arrays.asList(points).contains(null)) {
            throw new IllegalArgumentException("Null points");
        }

        copy = Arrays.copyOf(points, points.length);
        Arrays.sort(copy);

        for (int i = 1; i < copy.length; i++) {
            if (copy[i].equals(copy[i - 1])) {
                throw new IllegalArgumentException("Repeated points!");
            }
        }

        for (int i = 0; i < copy.length; i++) {
            for (int j = i + 1; j < copy.length; j++) {
                double slope = copy[i].slopeTo(copy[j]);
                for (int k = j + 1; k < copy.length; k++) {
                    if (copy[i].slopeTo(copy[k]) != slope) {
                        continue;
                    }
                    for (int l = k + 1; l < copy.length; l++) {
                        if (copy[i].slopeTo(copy[l]) != slope) {
                            continue;
                        }
                        segments.add(new LineSegment(copy[i], copy[l]));
                        number++;
                    }
                }
            }
        }
    }

    /*
    * The number of line segments
     */
    public int numberOfSegments() {
        return number;
    }

    /*
    * The line segments
     */
    public LineSegment[] segments() {
        LineSegment[] a = new LineSegment[segments.size()];
        return segments.toArray(a);
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

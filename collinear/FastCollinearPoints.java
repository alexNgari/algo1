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

public class FastCollinearPoints {

    // private ArrayList<LineSegment> segments = new ArrayList<LineSegment>();
    private ArrayList<Point> starts = new ArrayList<>();
    private ArrayList<Point> ends = new ArrayList<>();
    private int number = 0;

    public FastCollinearPoints(Point[] points) {
        if (points == null || points[0] == null) {
            throw new IllegalArgumentException("Null points!");
        }

        if (Arrays.asList(points).contains(null)) {
            throw new IllegalArgumentException("Null points");
        }

        Point[] copy;
        copy = Arrays.copyOf(points, points.length);

        Arrays.sort(copy);

        for (int i = 1; i < copy.length; i++) {
            if (copy[i].equals(copy[i - 1])) {
                throw new IllegalArgumentException("Repeated points!");
            }
        }

        for (int i = 0; i < copy.length; i++) {
            Point[] slopeSort = Arrays.copyOf(copy, copy.length);
            Arrays.sort(slopeSort, copy[i].slopeOrder());

            // for (Point temp: slopeSort) StdOut.print(String.format("%.2f ", copy[i].slopeTo(temp)));
            // StdOut.println(" ");

            Point start = copy[i];
            Point end = copy[i];
            int equalSlopes = 0;
            for (int j = 1; j < slopeSort.length; j++) {
                if (equalSlopes >= 2) {
                    if (copy[i].slopeTo(slopeSort[j]) != copy[i].slopeTo(start)) {
                        if (slopeSort[0].compareTo(start) < 0) start = slopeSort[0];
                        else if (end.compareTo(slopeSort[0]) < 0) end = slopeSort[0];
                        // segments.add(new LineSegment(start, end));
                        boolean exists = false;
                        if (starts.contains(start) && ends.contains(end)){
                            for (int index = 0; index < starts.size(); index++){
                                if (start.equals(starts.get(index))) {
                                    if (end.equals(ends.get(index))) {
                                        exists = true;
                                        break;
                                    }
                                }
                            }
                        }
                        if (!exists) {
                            starts.add(start);
                            ends.add(end);
                            number++;
                            start = slopeSort[j];
                            end = slopeSort[j];
                            equalSlopes = 0;
                            continue;
                        }


                    } else if (j == slopeSort.length - 1) {
                        if (slopeSort[j].compareTo(start) < 0) start = slopeSort[j];
                        else if (end.compareTo(slopeSort[j]) < 0) end = slopeSort[j];
                        if (slopeSort[0].compareTo(start) < 0) start = slopeSort[0];
                        else if (end.compareTo(slopeSort[0]) < 0) end = slopeSort[0];
                        // segments.add(new LineSegment(start, end));
                        boolean exists = false;
                        if (starts.contains(start) && ends.contains(end)){
                            for (int index = 0; index < starts.size(); index++){
                                if (start.equals(starts.get(index))) {
                                    if (end.equals(ends.get(index))) {
                                        exists = true;
                                        break;
                                    }
                                }
                            }
                        }
                        // if (!(starts.contains(start) && ends.contains(end))){
                        if (!exists) {
                            starts.add(start);
                            ends.add(end);
                            number++;
                        }
                    }
                }

                // StdOut.print((copy[i].slopeTo(slopeSort[j]) == copy[i].slopeTo(start)) + " ");

                if (copy[i].slopeTo(slopeSort[j]) == copy[i].slopeTo(start)) {
                    if (slopeSort[j].compareTo(start) < 0) start = slopeSort[j];
                    else if (end.compareTo(slopeSort[j]) < 0) end = slopeSort[j];
                    equalSlopes++;

                    // StdOut.print("(" + j + " " + (copy.length-1) + ")");

                    if (slopeSort[0].compareTo(start) < 0) start = slopeSort[0];
                    else if (end.compareTo(slopeSort[0]) < 0) end = slopeSort[0];
                    // segments.add(new LineSegment(start, end));
                    if (j == copy.length-1 && equalSlopes >= 2) {
                        // if (!(starts.contains(start) && ends.contains(end))){
                        boolean exists = false;
                        if (starts.contains(start) && ends.contains(end)){
                            for (int index = 0; index < starts.size(); index++){
                                if (start.equals(starts.get(index))) {
                                    if (end.equals(ends.get(index))) {
                                        exists = true;
                                        break;
                                    }
                                }
                            }
                        }
                        if (!exists) {
                            starts.add(start);
                            ends.add(end);
                            number++;
                        }
                    }

                    // StdOut.println(equalSlopes);

                } else {
                    equalSlopes = 0;
                    start = slopeSort[j];
                    end = slopeSort[j];
                }

                // StdOut.print(equalSlopes + " ");

            }

            // StdOut.println("\n");

        }
    }

    // private void merge(Point[] a, Point[] aux, int lo, int mid, int hi, Point ref) {
    //     Comparator<Point> compareSlopes = ref.slopeOrder();
    //
    //     for (int k = lo; k <= hi; k++) {
    //         aux[k] = a[k];
    //     }
    //     int i = lo, j = mid + 1;
    //     for (int k = lo; k <= hi; k++) {
    //         if (i > mid) a[k] = aux[j++];
    //         else if (j > hi) a[k] = aux[i++];
    //         else if (compareSlopes.compare(aux[j], aux[i]) < 0) a[k] = aux[j++];
    //         else a[k] = aux[i++];
    //     }
    // }
    //
    // private void sort(Point[] a, Point[] aux, int lo, int hi, Point ref) {
    //     if (hi <= lo) return;
    //     int mid = lo + (hi - lo) / 2;
    //     sort(a, aux, lo, mid, ref);
    //     sort(a, aux, mid + 1, hi, ref);
    //     merge(a, aux, lo, mid, hi, ref);
    // }

    public int numberOfSegments() {
        return number;
    }

    public LineSegment[] segments() {
        // LineSegment[] a = new LineSegment[segments.size()];
        // return segments.toArray(a);
        LineSegment[] a = new LineSegment[starts.size()];
        for (int i = 0; i < starts.size(); i++) {
            a[i] = new LineSegment(starts.get(i), ends.get(i));
        }
        return a;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
        StdOut.println(collinear.segments().length + " " + collinear.numberOfSegments());
    }
}

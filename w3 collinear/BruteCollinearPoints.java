import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;

public class BruteCollinearPoints {
    private Point[][] pointPair; // pointPair[][0] -> pointPair[][1] 
    private LineSegment[] lines;
    private int count = 0;

    public BruteCollinearPoints(Point[] points) {   // finds all line segments containing 4 points
        if (points == null) {
            throw new IllegalArgumentException("The points is null\n");
        }
        for (int i = 0; i < points.length; ++i) {
            if (points[i] == null) throw new IllegalArgumentException("One of the point is null\n");
        }
        for (int i = 0; i < points.length; ++i) {
            for (int j = 0; j < points.length; ++j) {
                if (j == i) continue;
                if (points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException("Find duplicated point here!\n");
            }
        }

        pointPair = new Point[32][2];
        for (int i = 0; i < points.length; ++i) {
            /********* 1st FOR loop *********/
            for (int j = i + 1; j < points.length; ++j) {
                /********* 2nd FOR loop *********/
                // if (j == i) continue;
                
                // compute slope between i and j
                double slopeIJ = points[i].slopeTo(points[j]);

                for (int p = j + 1; p < points.length; ++p) {
                    /********* 3rd FOR loop *********/
                    // if (p == j || p == i) continue;

                    // compute slope between j and p
                    double slopeJP = points[j].slopeTo(points[p]);
                    if (slopeJP != slopeIJ) continue;

                    for (int q = p + 1; q < points.length; ++q) {
                        /********* 4th FOR loop *********/
                        // if (q == p || q == j || q == i) continue;

                        // compute slope between p and q
                        double slopePQ = points[p].slopeTo(points[q]);
                        if (slopePQ != slopeJP) continue;

                        // create new line
                        int[] aux = {i, j, p, q};
                        Point minPoint = points[i], maxPoint = points[i];
                        for (int k = 1; k < aux.length; ++k) {
                            if (points[aux[k]].compareTo(minPoint) < 0) minPoint = points[aux[k]];
                            if (points[aux[k]].compareTo(maxPoint) > 0) maxPoint = points[aux[k]];
                        }
                        
                        // remove duplicated cases
                        boolean duplicatedFlag = false;
                        for (int k = 0; k < count; ++k) {
                            if (pointPair[k][0].compareTo(minPoint) == 0 && pointPair[k][1].compareTo(maxPoint) == 0) {
                                duplicatedFlag = true;
                                break;
                            }
                        }
                        if (duplicatedFlag) continue;

                        // add into the list
                        if (count == pointPair.length) {
                            resize(pointPair.length * 2);
                        }
                        pointPair[count][0] = minPoint;
                        pointPair[count++][1] = maxPoint;
                    }
                }
            }
        }
        // change to lines
        lines = new LineSegment[count];
        for (int i = 0; i < count; ++i) {
            lines[i] = new LineSegment(pointPair[i][0], pointPair[i][1]);
        }
    }

    private void resize(int newCapacity) {
        Point[][] newPointPair = new Point[newCapacity][2];
        for (int i = 0; i < count; ++i) {
            newPointPair[i] = pointPair[i];
        }
        pointPair = newPointPair;
    }

    public int numberOfSegments() {       // the number of line segments
        return count;
    }

    public LineSegment[] segments() {               // the line segments
        return Arrays.copyOf(lines, count);
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
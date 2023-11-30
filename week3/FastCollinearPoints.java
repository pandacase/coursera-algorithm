import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {
    private static int numOfCP = 4; // the number of collinear points we want to find out.
    private Point[][] pointPair;    // pointPair[][0] -> pointPair[][1] 
    private LineSegment[] lines;
    private int count = 0;

    public FastCollinearPoints(Point[] points) { // finds all line segments containing 4 or more points
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
        // start at i, look for other points.
        for (int i = 0; i < points.length; ++i) {
            Point[] otherPoints = new Point[points.length - 1];
            
            // gather other points except points[i]
            int index = 0;
            for (int j = 0; j < points.length; ++j) {
                if (j == i) continue;
                else otherPoints[index++] = points[j];
            }
            
            // sort the other points by slope to point[i]
            Comparator<Point> slopeOrderComparator = points[i].slopeOrder();
            Arrays.sort(otherPoints, slopeOrderComparator);

            // find the adj 3 points that have same slope to points[i]
            int cntEqualPoints = 1;
            for (int j = 0; j < otherPoints.length; ++j) {
                
                if (j > 0) {
                    if (slopeOrderComparator.compare(otherPoints[j], otherPoints[j - 1]) == 0) {
                        cntEqualPoints += 1;
                    } else {
                        cntEqualPoints = 1;
                    }
                }

                if (cntEqualPoints >= numOfCP - 1 && (j == otherPoints.length - 1 || slopeOrderComparator.compare(otherPoints[j], otherPoints[j + 1]) != 0)) { // it means that 3 adj points have same slope with point[i] -> this four points are collinear
                    // find the Min and Max in the four collinear points.
                    Point minPoint = points[i], maxPoint = points[i];
                    for (int k = 0; k < cntEqualPoints; ++k) {
                        if (otherPoints[j - k].compareTo(minPoint) < 0) minPoint = otherPoints[j - k];
                        if (otherPoints[j - k].compareTo(maxPoint) > 0) maxPoint = otherPoints[j - k];
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

                    // add into the list.
                    if (count == pointPair.length) {
                        resize(pointPair.length * 2);
                    }
                    pointPair[count][0] = minPoint;
                    pointPair[count++][1] = maxPoint;
                }
            }
        }
        // change to lines.
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
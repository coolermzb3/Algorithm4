/* *****************************************************************************
 *  Name: Yu Zihong
 *  Date: 2020/11/8 1:06
 *  Description:
 **************************************************************************** */

import java.util.Arrays;
import java.util.LinkedList;

public class FastCollinearPoints {

    private int numberOfSegments;
    private LineSegment[] segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("point array is null");
        }
        int n = points.length;
        for (int i = 0; i < n; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("point array has null element");
            }
        }

        Arrays.sort(points);
        for (int i = 1; i < n; i++) {
            if (points[i].compareTo(points[i - 1]) == 0) {
                throw new IllegalArgumentException("point array has repeated point");
            }
        }

        Point[] pointsCopy = points.clone();
        Point minPoint;
        Point maxPoint;
        LinkedList<Point[]> startAndEndPoints = new LinkedList<>();
        boolean repeated;
        this.numberOfSegments = 0;

        for (int i = 0; i < n; i++) {
            // calculate slope with this point for all points and sort by slope order
            // !!! using index in array with many sort cause BUGs!!!
            // use a copy array for index!!
            for (int j = 0; j < n; j++) {
                points[j].setSlope(pointsCopy[i]);
            }
            Arrays.sort(points, Point.slopeOrder());

            // check continuous four collinear points
            int continuousEqualCount = 0;
            for (int j = n - 1; j > 0; j--) {
                /*
                 * if last element is equal, we can't get in the else branch to
                 * process it. Taking advantage of the first element is always -inf
                 * and never equal to others, we iterate the points[] in reverse order.
                 *
                 * the condition in for should have been j >= 0, but we
                 * will use j-1, so it is j > 0
                 */
                if (points[j].getSlope() == points[j - 1].getSlope()) {
                    continuousEqualCount++;
                }
                else {
                    // 4 collinear points == 3 equal slopes == 2 compares
                    // determine min and max point
                    if (continuousEqualCount >= 2) {
                        // the collinear points are: points[0], points[j ~ j+Count]
                        minPoint = points[0];
                        maxPoint = points[0];
                        for (int k = j; k < j + continuousEqualCount + 1; k++) {
                            if (points[k].compareTo(minPoint) < 0) {
                                minPoint = points[k];
                            }
                            if (points[k].compareTo(maxPoint) > 0) {
                                maxPoint = points[k];
                            }
                        }
                        // check if contains
                        repeated = false;
                        for (Point[] ps : startAndEndPoints) {
                            if (ps[0].compareTo(minPoint) == 0 && ps[1].compareTo(maxPoint) == 0) {
                                repeated = true;
                            }
                        }
                        if (!repeated) {
                            startAndEndPoints.add(new Point[] { minPoint, maxPoint });
                            numberOfSegments++;
                        }
                    }
                    continuousEqualCount = 0;
                }
            }
        }
        segments = new LineSegment[numberOfSegments];
        int i = 0;
        for (Point[] ps : startAndEndPoints) {
            segments[i++] = new LineSegment(ps[0], ps[1]);
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return numberOfSegments;
    }

    // the line segments
    public LineSegment[] segments() {
        return this.segments.clone();
    }

    public static void main(String[] args) {
        Integer[] a = new Integer[10];
        for (int i = 0; i < a.length; i++) {
            a[i] = i;
        }
        Arrays.sort(a);
        System.out.println("a = " + Arrays.toString(a));

    }
}

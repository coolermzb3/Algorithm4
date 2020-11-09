/* *****************************************************************************
 *  Name: Yu Zihong
 *  Date: 2020/11/8 1:06
 *  Description:
 **************************************************************************** */

import java.util.Arrays;
import java.util.LinkedList;

public class FastCollinearPoints {

    private int numberOfSegments;
    private final LineSegment[] segments;

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

        Point[] pointsCopy = points.clone();
        Arrays.sort(pointsCopy);
        for (int i = 1; i < n; i++) {
            if (pointsCopy[i].compareTo(pointsCopy[i - 1]) == 0) {
                throw new IllegalArgumentException("point array has repeated point");
            }
        }

        Point minPoint;
        Point maxPoint;
        LinkedList<LineSegment> segmentLinkedList = new LinkedList<>();

        this.numberOfSegments = 0;

        for (int i = 0; i < n; i++) {
            // sort by slope order using this point's comparator
            // !!! using index in array with many sort cause BUGs!!!
            // use a copy array for index!!
            Arrays.sort(pointsCopy, points[i].slopeOrder());

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
                if (points[i].slopeTo(pointsCopy[j]) == points[i].slopeTo(pointsCopy[j - 1])) {
                    continuousEqualCount++;
                }
                else {
                    // 4 collinear points == 3 equal slopes == 2 compares
                    // determine min and max point
                    if (continuousEqualCount >= 2) {
                        // the collinear points are: points[0], points[j ~ j+Count]
                        minPoint = pointsCopy[0];
                        maxPoint = pointsCopy[0];
                        for (int k = j; k < j + continuousEqualCount + 1; k++) {
                            if (pointsCopy[k].compareTo(minPoint) < 0) {
                                minPoint = pointsCopy[k];
                            }
                            if (pointsCopy[k].compareTo(maxPoint) > 0) {
                                maxPoint = pointsCopy[k];
                            }
                        }
                        // don't check if contains in loop... check it later
                        // update: check now! Why can't I think of it!!!
                        if (minPoint.compareTo(pointsCopy[0]) == 0) {
                            // add in to segment only when point is min point (no repeat!!)
                            segmentLinkedList.add(new LineSegment(minPoint, maxPoint));
                            numberOfSegments++;
                        }
                    }
                    continuousEqualCount = 0;
                }
            }
        }

        this.segments = new LineSegment[numberOfSegments];
        int i = 0;
        for (LineSegment ps : segmentLinkedList) {
            this.segments[i++] = ps;
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return this.numberOfSegments;
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

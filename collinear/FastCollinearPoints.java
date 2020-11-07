/* *****************************************************************************
 *  Name: Yu Zihong
 *  Date: 2020/11/8 1:06
 *  Description:
 **************************************************************************** */

import java.util.Arrays;

public class FastCollinearPoints {

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
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("point array has repeated point");
                }
            }
        }

        for (int i = 0; i < n; i++) {

            int continuousEqualCount = 0;

            Arrays.sort(points, Point.slopeOrder(points[i]));
            for (int j = 1; j < n - 1; j++) {
                // only first is self, j start from 1
                if (points[j].compareTo(points[j + 1]) == 0) {
                    continuousEqualCount++;
                }
                else {

                    // TODO
                    if (continuousEqualCount < 3) {
                        continue;
                    }

                    continuousEqualCount = 0;
                }
            }
        }


    }

    // the number of line segments
    public int numberOfSegments() {
        return 0;
    }

    // the line segments
    public LineSegment[] segments() {
        return null;
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

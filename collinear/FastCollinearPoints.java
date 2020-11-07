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

        Arrays.sort(points, Point.slopeOrder(points[0]));


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

    }
}

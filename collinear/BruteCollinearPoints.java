/* *****************************************************************************
 *  Name: Yu Zihong
 *  Date: 2020/11/7 17:45
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;

public class BruteCollinearPoints {

    private int numberOfSegments;
    private LineSegment[] segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
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

        Point[] p4 = new Point[4];
        Point minPoint;
        Point maxPoint;
        ArrayList<LineSegment> segmentList = new ArrayList<>();
        this.numberOfSegments = 0;

        for (int p = 0; p < n; p++) {
            for (int q = p + 1; q < n; q++) {
                for (int r = q + 1; r < n; r++) {
                    for (int s = r + 1; s < n; s++) {
                        p4[0] = points[p];
                        p4[1] = points[q];
                        p4[2] = points[r];
                        p4[3] = points[s];

                        if (p4[0].slopeTo(p4[1]) == p4[0].slopeTo(p4[2])
                                && p4[0].slopeTo(p4[1]) == p4[0].slopeTo(p4[3])) {
                            minPoint = p4[0];
                            maxPoint = p4[0];
                            for (int i = 1; i < 4; i++) {
                                if (p4[i].compareTo(minPoint) < 0) {
                                    minPoint = p4[i];
                                }
                                if (p4[i].compareTo(maxPoint) > 0) {
                                    maxPoint = p4[i];
                                }
                            }
                            segmentList.add(new LineSegment(minPoint, maxPoint));
                            numberOfSegments++;
                        }
                    }
                }
            }
        }

        segments = new LineSegment[numberOfSegments];
        for (int i = 0; i < numberOfSegments; i++) {
            segments[i] = segmentList.get(i);
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
        int[] a = new int[3];
        int n = a.length;
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                System.out.println("count = " + count++);
            }
        }
    }
}

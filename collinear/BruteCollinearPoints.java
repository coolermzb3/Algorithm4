/* *****************************************************************************
 *  Name: Yu Zihong
 *  Date: 2020/11/7 17:45
 *  Description:
 **************************************************************************** */

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
        numberOfSegments = 0;

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
                            numberOfSegments++;
                            System.out.println("some new thing");
                            System.out.println("another");
                            

                        }

                    }
                }
            }
        }

    }

    // the number of line segments
    public int numberOfSegments() {
        return numberOfSegments;
    }

    // the line segments
    public LineSegment[] segments() {
        return segments;
    }

    public static void main(String[] args) {

    }
}

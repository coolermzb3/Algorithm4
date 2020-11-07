/* *****************************************************************************
 *  Author:          Yu Zihong
 *  Date:            2020/10/22 1:37
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;
    private final int n, t;
    private final int[] thresholds;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int nn, int trials) {
        if (nn <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        n = nn;
        t = trials;
        thresholds = new int[t];
        for (int i = 0; i < t; i++) {
            Percolation perc = new Percolation(n);
            thresholds[i] = monteCarlo(perc);
        }
    }

    private int monteCarlo(Percolation perc) {
        int row, col;
        while (!perc.percolates()) {
            row = StdRandom.uniform(n) + 1;
            col = StdRandom.uniform(n) + 1;
            perc.open(row, col);
        }
        return perc.numberOfOpenSites();
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds) / (double) n / n;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholds) / (double) n / n;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(t);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + CONFIDENCE_95 * stddev() / Math.sqrt(t);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n, t;
        n = Integer.parseInt(args[0]);
        t = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, t);

        System.out.printf("mean                    = %f\n", ps.mean());
        System.out.printf("stddev                  = %f\n", ps.stddev());
        System.out.printf("95%% confidence interval = [%f, %f]\n", ps.confidenceLo(),
                          ps.confidenceHi());
    }

}

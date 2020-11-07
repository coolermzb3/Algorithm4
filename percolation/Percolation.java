/* *****************************************************************************
 *  Author:          Yu Zihong
 *  Date:            2020/10/22 1:36
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int n;
    private boolean[] isOpen, isConnectedToBottom;
    private final WeightedQuickUnionUF sites;
    private int numberOfOpenSites = 0;
    private boolean percolationFlag = false;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int nn) {
        if (nn <= 0) {
            throw new IllegalArgumentException();
        }
        n = nn;
        int len = n * n + 1;
        // add top and bottom grid, and connect to top row and bottom row
        sites = new WeightedQuickUnionUF(len);
        for (int i = 0; i < n; i++) {
            sites.union(0, i + 1);
        }
        isOpen = new boolean[len];
        isConnectedToBottom = new boolean[len];
    }

    private void valid(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException();
        }
    }

    private int trans(int row, int col) {
        return (row - 1) * n + col;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        valid(row, col);
        if (isOpen[trans(row, col)]) {
            return;
        }
        isOpen[trans(row, col)] = true;
        numberOfOpenSites++;

        int thisSiteIndex = trans(row, col);

        /*
        check whether every site's root is connected to bottom
        if bottom site is opened, it is true
        if opened neighbor site's root is true, set this site's root true
        */
        if (row == n) {
            isConnectedToBottom[sites.find(thisSiteIndex)] = true;
        }

        // up
        int upSiteIndex = trans(row - 1, col);
        if (row > 1 && isOpen[upSiteIndex]) {
            if (isConnectedToBottom[sites.find(upSiteIndex)] || isConnectedToBottom[sites
                    .find(thisSiteIndex)]) {
                sites.union(thisSiteIndex, upSiteIndex);
                isConnectedToBottom[sites.find(thisSiteIndex)] = true;
            }
            else {
                sites.union(thisSiteIndex, upSiteIndex);
            }
        }

        // down
        int downSiteIndex = trans(row + 1, col);
        if (row < n && isOpen[downSiteIndex]) {
            if (isConnectedToBottom[sites.find(downSiteIndex)] || isConnectedToBottom[sites
                    .find(thisSiteIndex)]) {
                sites.union(thisSiteIndex, downSiteIndex);
                isConnectedToBottom[sites.find(thisSiteIndex)] = true;
            }
            else {
                sites.union(thisSiteIndex, downSiteIndex);
            }
        }

        // left
        int leftSiteIndex = trans(row, col - 1);
        if (col > 1 && isOpen[leftSiteIndex]) {
            if (isConnectedToBottom[sites.find(leftSiteIndex)] || isConnectedToBottom[sites
                    .find(thisSiteIndex)]) {
                sites.union(thisSiteIndex, leftSiteIndex);
                isConnectedToBottom[sites.find(thisSiteIndex)] = true;
            }
            else {
                sites.union(thisSiteIndex, leftSiteIndex);
            }
        }

        // right
        int rightSiteIndex = trans(row, col + 1);
        if (col < n && isOpen[rightSiteIndex]) {
            if (isConnectedToBottom[sites.find(rightSiteIndex)] || isConnectedToBottom[sites
                    .find(thisSiteIndex)]) {
                sites.union(thisSiteIndex, rightSiteIndex);
                isConnectedToBottom[sites.find(thisSiteIndex)] = true;
            }
            else {
                sites.union(thisSiteIndex, rightSiteIndex);
            }
        }

        // perlocation iff isfull && root is connected to bottom
        if (!percolationFlag) {
            percolationFlag = sites.find(0) == sites.find(thisSiteIndex)
                    && isConnectedToBottom[sites.find(thisSiteIndex)];
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        valid(row, col);
        return isOpen[trans(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        valid(row, col);
        return isOpen[trans(row, col)] && sites.find(0) == sites.find(trans(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        if (n == 1) {
            return isOpen[1];
        }
        else {
            return percolationFlag;
        }
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation mp = new Percolation(1);
        System.out.println(mp.isOpen(1, 1));
        System.out.println(mp.percolates());
        mp.open(1, 1);
        System.out.println(mp.percolates());
        // mp.open(2, 2);
        // System.out.println("number:" + mp.numberOfOpenSites());
        // System.out.println("1,2 open? " + mp.isOpen(1, 2));
        // System.out.println("1,1 full? " + mp.isFull(1, 1));
        // System.out.println("1,2 full? " + mp.isFull(1, 2));
        // System.out.println("2,2 full? " + mp.isFull(2, 2));
        // System.out.println("percolates? " + mp.percolates());
        // mp.open(1, 2);
        // System.out.println("1,2 opened");
        // System.out.println("number:" + mp.numberOfOpenSites());
        // System.out.println("2,2 full? " + mp.isFull(2, 2));
        // System.out.println("percolates? " + mp.percolates());
        // mp.open(3, 3);
        // mp.open(3, 2);
        // System.out.println("3,2 opened");
        // System.out.println("number:" + mp.numberOfOpenSites());
        // System.out.println("3,2 full? " + mp.isFull(3, 3));
        // System.out.println("percolates? " + mp.percolates());
    }

}


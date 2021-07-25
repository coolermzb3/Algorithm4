/* *****************************************************************************
 *  Name: Yu Zihong
 *  Date: 2021/7/19 23:45
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Queue;

import java.util.Arrays;

public class Board {

    private final int[] board;
    private final int n;


    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles.length;

        board = new int[n * n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i * n + j] = tiles[i][j];
            }
        }
    }

    private Board(int n, int[] array1d) {
        // private constructor
        // array1d from `swap` has already been cloned
        this.n = n;
        this.board = array1d;
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", board[i * n + j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < board.length; i++) {
            // goal num of position i is i+1
            if (board[i] != i + 1) hamming++;
        }
        // eliminate the impact of 0
        return hamming - 1;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattan = 0;
        int i1, j1, i2, j2;
        for (int i = 0; i < board.length; i++) {
            // skip 0
            if (board[i] == 0) continue;
            // skip right pos
            if (board[i] == i + 1) continue;
            // actual index is i
            // goal index is board[i]-1
            i1 = i / n;
            j1 = i % n;
            i2 = (board[i] - 1) / n;
            j2 = (board[i] - 1) % n;
            manhattan += Math.abs(i1 - i2) + Math.abs(j1 - j2);
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        return this.n == that.n && Arrays.equals(this.board, that.board);
    }

    // swap tiles
    private Board swap(int ori, int des) {
        int[] copy = board.clone();
        int temp = copy[ori];
        copy[ori] = copy[des];
        copy[des] = temp;
        return new Board(n, copy);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int zero = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 0) {
                zero = i;
                break;
            }
        }

        Queue<Board> neighbors = new Queue<Board>();

        // check neighbor index validity
        // up
        if (zero >= n)
            neighbors.enqueue(swap(zero, zero - n));
        // down
        if (zero < board.length - n)
            neighbors.enqueue(swap(zero, zero + n));
        // left
        if (zero % n > 0) neighbors.enqueue(swap(zero, zero - 1));
        // right
        if (zero % n < n - 1) neighbors.enqueue(swap(zero, zero + 1));

        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        // 0 1 2 these three index must have two nonzero tiles
        if (board[0] == 0)
            return swap(1, 2);
        else if (board[1] == 0)
            return swap(0, 2);
        else
            return swap(0, 1);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] tiles = { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } };
        Board a = new Board(tiles);
        System.out.println(a.toString());
        System.out.println("hamming distance: " + a.hamming());
        System.out.println("manhattan distance: " + a.manhattan());
        System.out.println("twin: \n" + a.twin().toString());
        System.out.println("neighbors: \n");
        for (Board b : a.neighbors()) {
            System.out.println(b.toString());
        }

    }

}

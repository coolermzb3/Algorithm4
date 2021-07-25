/* *****************************************************************************
 *  Name: Yu Zihong
 *  Date: 2021/7/19 23:45
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Queue;

import java.util.Arrays;

public class Board {

    private int[][] board;
    private int n;

    // private class for array index
    private final class Index {
        private final int i;
        private final int j;

        public Index(int i, int j) {
            this.i = i;
            this.j = j;
        }

        public boolean isValid() {
            return i >= 0 && i < n && j >= 0 && j < n;
        }
    }

    // n-dimension array copy
    private int[][] arrayCopy(int[][] array) {
        int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                copy[i][j] = array[i][j];
            }
        }
        return copy;
    }

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles.length;
        board = arrayCopy(tiles);
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", board[i][j]));
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
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // goal num of position (i,j) is i*n+j+1
                if (board[i][j] != i * n + j + 1) hamming++;
            }
        }
        // eliminate the impact of 0
        return hamming - 1;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int goal_i, goal_j, manhattan = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // eliminate the impact of 0
                if (board[i][j] == 0) continue;

                // goal position of num
                goal_i = (board[i][j] - 1) / n;
                goal_j = (board[i][j] - 1) % n;

                manhattan += Math.abs(goal_i - i) + Math.abs(goal_j - j);
            }
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
        return this.n == that.n && Arrays.deepEquals(this.board, that.board);
    }

    // swap tiles
    private Board swap(Index ori, Index des) {
        int[][] copy = arrayCopy(board);
        int temp = copy[ori.i][ori.j];
        copy[ori.i][ori.j] = copy[des.i][des.j];
        copy[des.i][des.j] = temp;
        return new Board(copy);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Index zero = null;
        Index neighbor;

        // find 0
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 0) {
                    zero = new Index(i, j);
                    break;
                }
            }
        }
        assert zero != null;

        Queue<Board> neighbors = new Queue<Board>();

        // check neighbor index validity
        // up
        neighbor = new Index(zero.i - 1, zero.j);
        if (neighbor.isValid()) neighbors.enqueue(swap(zero, neighbor));
        // down
        neighbor = new Index(zero.i + 1, zero.j);
        if (neighbor.isValid()) neighbors.enqueue(swap(zero, neighbor));
        // left
        neighbor = new Index(zero.i, zero.j - 1);
        if (neighbor.isValid()) neighbors.enqueue(swap(zero, neighbor));
        // right
        neighbor = new Index(zero.i, zero.j + 1);
        if (neighbor.isValid()) neighbors.enqueue(swap(zero, neighbor));

        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        // (0,0) (0,n-1) (n-1,0) these three index must have two nonzero tiles
        if (board[0][0] == 0)
            return swap(new Index(0, n - 1), new Index(n - 1, 0));
        else if (board[0][n - 1] == 0)
            return swap(new Index(0, 0), new Index(n - 1, 0));
        else
            return swap(new Index(0, 0), new Index(0, n - 1));
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] tiles = { { 0, 1, 3 }, { 8, 4, 2 }, { 7, 6, 5 } };
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

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

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles.length;
        board = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = tiles[i][j];
            }
        }
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

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> neighbors = new Queue<Board>();
        

        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        return null;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] tiles = { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } };
        Board a = new Board(tiles);
        System.out.println(a.hamming());
        System.out.println(a.manhattan());

    }


}

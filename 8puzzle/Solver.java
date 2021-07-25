/* *****************************************************************************
 *  Name: Yu Zihong
 *  Date: 2021/7/20 0:01
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {

    // search node
    private class SearchNode {
        private final Board board;
        private final int moves;
        private final SearchNode prev;
        private final int priority;
        private final int distance;

        public SearchNode(Board board, int moves, SearchNode prev) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
            this.distance = board.manhattan();
            // this.priority = board.hamming() + moves;
            this.priority = this.distance + moves;
        }
    }

    private final SearchNode goalNode;
    private final boolean isSolvable;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();

        SearchNode self = new SearchNode(initial, 0, null);
        SearchNode twin = new SearchNode(initial.twin(), 0, null);
        SearchNode curSelf;
        SearchNode curTwin;

        MinPQ<SearchNode> pqSelf = new MinPQ<>(prior());
        MinPQ<SearchNode> pqTwin = new MinPQ<>(prior());

        pqSelf.insert(self);
        pqTwin.insert(twin);
        curSelf = pqSelf.delMin();
        curTwin = pqTwin.delMin();

        Board prevBoard;

        while (!curSelf.board.isGoal() && !curTwin.board.isGoal()) {
            prevBoard = curSelf.prev != null ? curSelf.prev.board : null;
            for (Board board : curSelf.board.neighbors()) {
                if (!board.equals(prevBoard))
                    // critical optimization
                    pqSelf.insert(new SearchNode(board, curSelf.moves + 1, curSelf));
            }
            prevBoard = curTwin.prev != null ? curTwin.prev.board : null;
            for (Board board : curTwin.board.neighbors()) {
                if (!board.equals(prevBoard))
                    // critical optimization
                    pqTwin.insert(new SearchNode(board, curTwin.moves + 1, curTwin));
            }
            curSelf = pqSelf.delMin();
            curTwin = pqTwin.delMin();
        }

        if (curSelf.board.isGoal()) {
            isSolvable = true;
            goalNode = curSelf;
            // System.out.println("pqSelf.size() = " + pqSelf.size());
        }
        else {
            isSolvable = false;
            goalNode = curTwin;
        }


    }

    // comparator for minPQ
    private Comparator<SearchNode> prior() {
        return new Prior();
    }

    private class Prior implements Comparator<SearchNode> {
        public int compare(SearchNode n1, SearchNode n2) {
            if (n1.priority < n2.priority) return -1;
            if (n1.priority > n2.priority) return 1;
            else return Integer.compare(n1.distance, n2.distance);
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (isSolvable)
            return goalNode.moves;
        else
            return -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable)
            return null;
        else {
            Stack<Board> stack = new Stack<>();
            SearchNode node = goalNode;
            stack.push(node.board);
            while (node.prev != null) {
                node = node.prev;
                stack.push(node.board);
            }
            return stack;
        }

    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // System.out.println(initial.toString());

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}

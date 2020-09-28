/* *****************************************************************************
 *  Name:    Alan Turing
 *  NetID:   aturing
 *  Precept: P00
 *
 *  Partner Name:    Ada Lovelace
 *  Partner NetID:   alovelace
 *  Partner Precept: P00
 *
 *  Description:  Prints 'Hello, World' to the terminal window.
 *                By tradition, this is everyone's first program.
 *                Prof. Brian Kernighan initiated this tradition in 1974.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private Node goal;

    private class Node implements Comparable<Node> {
        private Board board;
        private Node prev;
        private int moves;

        public Node(Board board, Node prev) {
            this.board = board;
            this.prev = prev;
            this.moves = (prev == null) ? 0 : this.prev.moves + 1;
        }

        public int compareTo(Node that) {
            return this.board.manhattan() + this.moves - that.board.manhattan() - that.moves;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        MinPQ<Node> mainQueue = new MinPQ<Node>();
        MinPQ<Node> twinQueue = new MinPQ<Node>();
        mainQueue.insert(new Node(initial, null));
        twinQueue.insert(new Node(initial.twin(),null));

        do {
            this.goal = solve(mainQueue);
        } while (this.goal == null && solve(twinQueue) == null);
    }

    private Node solve(MinPQ<Node> queue) {
        if (queue.isEmpty()) return null;
        Node min = queue.delMin();
        if (min.board.isGoal()) return min;
        for (Board neighbour: min.board.neighbors()) {
            if (min.prev != null) {
                if (neighbour.equals(min.prev.board)) continue;
            }
            queue.insert(new Node(neighbour, min));
        }
        return null;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return !(goal == null);
    }

    // min number of moves to solve initial board
    public int moves() {
        return goal.moves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        Stack<Board> solution = new Stack<Board>();
        Node temp = goal;
        while (temp != null) {
            solution.push(temp.board);
            temp = temp.prev;
        }
        return solution;
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

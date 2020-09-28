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
import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;

public class Board {

    private int[][] board;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.board = tiles;
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(String.format("%d\n", this.board.length));
        for (int i = 0; i < this.board.length; i++) {
            for (int element: this.board[i]) {
                s.append(element + " ");
            }
            if (i < this.board.length - 1) s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return this.board.length;
    }

    // number of tiles out of place
    public int hamming() {
        int distance = 0;
        int dim = dimension();
        int value = 1;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                // if (this.board[i][j] == 0) continue;
                // StdOut.println(String.format("Hamming: %d", value));
                if (this.board[i][j] != value++ && this.board[i][j] != 0) {
                    distance++;
                }
                value %= (dim * dim);
            }
        }
        return distance;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int distance = 0;
        int dim = dimension();
        int value = 1;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if ((this.board[i][j] != value) && (this.board[i][j] != 0)) {
                    // int diff = Math.abs(this.board[i][j] - value);
                    // distance += (diff / dim + diff % dim);
                    // int iValue = value  / dim;
                    // int jValue = value % dim;
                    int realIValue = (this.board[i][j] - 1) / dim;
                    int realJValue = (this.board[i][j] - 1) % dim;
                    distance += (Math.abs(realIValue - i) + Math.abs(realJValue - j));
                }
                value++;
                // if (++value == dim * dim) break;
            }
        }
        return distance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        int value = 1;
        int dim =  dimension();
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (this.board[i][j] != value++) return false;
                value %= (dim * dim);
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null || !(y instanceof Board) || ((Board) y).board.length != this.board.length) {
            return false;
        }
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board.length; j ++) {
                if (((Board) y).board[i][j] != this.board[i][j]) return false;
            }
        }
        return true;
    }

    private int[][] copyOfBoard() {
        int[][] newBoard = new int[this.board.length][this.board.length];
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board.length; j++) {
                newBoard[i][j] = this.board[i][j];
            }
        }
        return newBoard;
    }

    private void exchange(int[][] arr, int i, int j, int k, int l) {
        int temp = arr[i][j];
        arr[i][j] = arr[k][l];
        arr[k][l] = temp;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        LinkedList<Board> neighbors = new LinkedList<Board>();

        // Locate space
        int izero = 0;
        int jzero = 0;
        for (int i = 0; i < this.board.length; i ++) {
            for (int j = 0; j < this.board.length; j++) {
                if (this.board[i][j] == 0) {
                    izero = i;
                    jzero = j;
                    break;
                }
            }
        }

        if (izero > 0) {
            int[][] temp = copyOfBoard();
            exchange(temp, izero, jzero, izero - 1, jzero);
            neighbors.add(new Board(temp));
        }
        if (izero < this.board.length - 1) {
            int [][] temp = copyOfBoard();
            exchange(temp, izero, jzero, izero + 1, jzero);
            neighbors.add(new Board(temp));
        }
        if (jzero > 0) {
            int[][] temp = copyOfBoard();
            exchange(temp, izero, jzero, izero, jzero - 1);
            neighbors.add(new Board(temp));
        }
        if (jzero < this.board.length - 1) {
            int[][] temp =  copyOfBoard();
            exchange(temp, izero, jzero, izero, jzero + 1);
            neighbors.add(new Board(temp));
        }
        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int iChange = 0;
        int jChange = 0;
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board.length - 1; j++) {
                // StdOut.print(String.format("%d, %d\n", this.board[i][j], this.board[i][j+1]));
                if (this.board[i][j] > 0 && this.board[i][j+1] > 0) {
                    iChange = i;
                    jChange = j;
                    break;
                }
            }
        }
        int[][] temp = copyOfBoard();
        exchange(temp, iChange, jChange, iChange, jChange+1);
        return new Board(temp);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board board = new Board(tiles);
        // int [][] tiles =  {{1, 0, 3}, {4, 2, 5}, {7, 8, 6}};
        // Board board = new Board(tiles);
        StdOut.println("board: ");
        StdOut.println(board.toString());
        StdOut.println(board.equals(board));
        StdOut.println(String.format("dimension: %d", board.dimension()));
        StdOut.println(String.format("hamming: %d", board.hamming()));
        StdOut.println(String.format("manhattan: %d", board.manhattan()));
        StdOut.println(board.isGoal());
        Board twin = board.twin();
        StdOut.println("twin: ");
        StdOut.println(twin.toString());
        StdOut.println(board.equals(twin));
        for (Board temp: board.neighbors()) StdOut.println(temp.toString());
    }
}

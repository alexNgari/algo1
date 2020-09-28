/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int size;
    private boolean[][] space;
    private final WeightedQuickUnionUF unionFind;
    private int openSites = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if ( n<= 0) {
            throw new IllegalArgumentException("Dimension must be larger than 0");
        }
        size = n;
        space = new boolean[n][n];
        for (int i = 0; i<n; i++) {
            for (int j = 0; j<n; j++) {
                space[i][j] = false;
            }
        }
        // Union find structure has 2 dummy nodes
        unionFind = new WeightedQuickUnionUF(n*n+2);
        // // Connect dummy nodes to top and bottom rows
        // for (int i=0; i<n; i++) {
        //     unionFind.union(n*n, i);
        //     unionFind.union(n*n+1, n*n-(1+i));
        // }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row<1 || col<1 || row>size || col>size) {
            throw new IllegalArgumentException("Dimensions out of bounds");
        }
        if (isOpen(row, col)) {
            return;
        }
        space[row-1][col-1] = true;
        if (row == 1) {
            unionFind.union(col-1, size*size);
        }
        if (row == size) {
            unionFind.union((row-1)*size+col-1, size*size+1);
        }
        if (row != 1 ) {
            if (space[row-2][col-1]) {
                unionFind.union((row-2)*size+col-1,(row-1)*size+col-1);
            }
        }
        if (row != size) {
            if (space[row][col-1]) {
                unionFind.union(row*size+col-1, (row-1)*size+col-1);
            }
        }
        if (col != 1 ) {
            if (space[row-1][col-2]) {
                unionFind.union((row-1)*size+col-2, (row-1)*size+col-1);
            }
        }
        if (col != size) {
            if (space[row-1][col]) {
                unionFind.union((row-1)*size+col, (row-1)*size+col-1);
            }
        }
        openSites++;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || col < 1 || row>size || col > size) {
            throw new IllegalArgumentException("Dimensions out of bounds");
        }
        return space[row-1][col-1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || col < 1 || row > size || col > size) {
            throw new IllegalArgumentException("Dimensions out of bounds");
        }
        return (unionFind.find(size*size) == unionFind.find((row-1)*size+col-1))
                && (isOpen(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return unionFind.find(size*size) == unionFind.find(size*size+1);
    }

    // test client (optional)
    public static void main(String[] args) {
        // Percolation perco = new Percolation(5);
        // StdOut.println(perco.isFull(1,1));
        // StdOut.println(perco.isOpen(1,1));
        // perco.open(2,1);
        // StdOut.println(perco.isFull(2,1));
        // StdOut.println(perco.isOpen(2,1));
        // perco.open(1,1);
        // StdOut.println(perco.isFull(2,1));
        // StdOut.println(perco.isOpen(1,1));
    }
}
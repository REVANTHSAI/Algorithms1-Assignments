package com.revanth.Week1;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private static final boolean OPENED = true, CLOSED = false;
    private boolean[][] grid;
    private final WeightedQuickUnionUF quickFindUF;
    private int openSitesCount = 0;
    private final int virtualTopIndex, virtualBottomIndex;


    // creates n-by-n grid, with all sites initially blocked
    // 0 blocked 1 open
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();
        quickFindUF = new WeightedQuickUnionUF(n * n + 2);
        grid = new boolean[n][n];
        virtualTopIndex = 0;
        virtualBottomIndex = n * n + 1;
    }

    private int d2Tod1(int row, int column) {
        return (row * grid[0].length + column) + 1;
    }


    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateRowColumn(row, col);

        int shiftRow = row - 1;
        int shiftCol = col - 1;

        if (isOpen(row, col)) return;
        int curArrayIndex = d2Tod1(shiftRow, shiftCol);

        if (shiftRow > 0) {
            if (isOpen(row - 1, col) && !isConnected(curArrayIndex, d2Tod1(shiftRow - 1, shiftCol)))
                quickFindUF.union(curArrayIndex, d2Tod1(shiftRow - 1, shiftCol));
        }
        if (shiftRow < grid.length - 1) {
            if (isOpen(row + 1, col) && !isConnected(curArrayIndex, d2Tod1(shiftRow + 1, shiftCol)))
                quickFindUF.union(curArrayIndex, d2Tod1(shiftRow + 1, shiftCol));
        }

        if (shiftCol > 0) {
            if (isOpen(row, col - 1) && !isConnected(curArrayIndex, d2Tod1(shiftRow, shiftCol - 1)))
                quickFindUF.union(curArrayIndex, d2Tod1(shiftRow, shiftCol - 1));
        }

        if (shiftCol < grid[0].length - 1) {
            if (isOpen(row, col + 1) && !isConnected(curArrayIndex, d2Tod1(shiftRow, shiftCol + 1)))
                quickFindUF.union(curArrayIndex, d2Tod1(shiftRow, shiftCol + 1));
        }

        if (shiftRow == 0) {
            quickFindUF.union(virtualTopIndex, d2Tod1(shiftRow, shiftCol));
        }

        if (shiftRow == grid.length - 1) {
            quickFindUF.union(virtualBottomIndex, d2Tod1(shiftRow, shiftCol));
        }
        grid[shiftRow][shiftCol] = OPENED;
        openSitesCount++;
    }

    private boolean isConnected(int arrayIndex1, int arrayIndex2) {
        return quickFindUF.find(arrayIndex1) == quickFindUF.find(arrayIndex2);
    }


    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateRowColumn(row, col);
        int shiftRow = row - 1;
        int shiftCol = col - 1;
        return grid[shiftRow][shiftCol] == OPENED;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateRowColumn(row, col);
        int shiftRow = row - 1;
        int shiftCol = col - 1;
        if (grid[shiftRow][shiftCol] == CLOSED)
            return false;
        else
            return isConnected(virtualTopIndex, d2Tod1(shiftRow, shiftCol));
    }

    private void validateRowColumn(int row, int column) {
        if (row < 1 || row > grid.length || column < 1 || column > grid[0].length)
            throw new IllegalArgumentException();
    }


    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSitesCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return isConnected(virtualTopIndex, virtualBottomIndex);
    }

}

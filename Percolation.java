import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] opened;
    private int top;
    private int bottom;
    private int countOpen;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF uf2;
    private int len;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Given n <= 0");
        }
        opened = new boolean[n][n];
        uf = new WeightedQuickUnionUF(n * n + 2);
        uf2 = new WeightedQuickUnionUF(n * n + 1);
        top = n * n;
        bottom = n * n + 1;
        countOpen = 0;
        len = n;
    }

    private boolean checkIndex(int row, int col) {
        if (row < 1 || row > len || col < 1 || col > len) return false;
        return true;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (checkIndex(row, col)) {
            if (!isOpen(row, col)) {
                opened[row - 1][col - 1] = true;
                countOpen++;
            }
            if (row == 1) {
                uf.union(col - 1, top);
                uf2.union(col - 1, top);
            }
            if (row == len) {
                uf.union((row - 1) * len + col - 1, bottom);
            }
            if (row > 1 && isOpen(row - 1, col)) {
                uf.union((row - 1) * len + col - 1, (row - 2) * len + col - 1);
                uf2.union((row - 1) * len + col - 1, (row - 2) * len + col - 1);
            }
            if (col > 1 && isOpen(row, col - 1)) {
                uf.union((row - 1) * len + col - 1, (row - 1) * len + col - 2);
                uf2.union((row - 1) * len + col - 1, (row - 1) * len + col - 2);

            }
            if (row < len && isOpen(row + 1, col)) {
                uf.union((row - 1) * len + col - 1, (row) * len + col - 1);
                uf2.union((row - 1) * len + col - 1, (row) * len + col - 1);

            }
            if (col < len && isOpen(row, col + 1)) {
                uf.union((row - 1) * len + col - 1, (row - 1) * len + col);
                uf2.union((row - 1) * len + col - 1, (row - 1) * len + col);

            }
        }
        else {
            throw new IndexOutOfBoundsException();
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (checkIndex(row, col)) {
            return opened[row - 1][col - 1];
        }
        throw new IndexOutOfBoundsException();
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (checkIndex(row, col)) {
            return uf2.connected((row - 1) * len + col - 1, top);
        }
        throw new IndexOutOfBoundsException();
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return countOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(top, bottom);
    }

}

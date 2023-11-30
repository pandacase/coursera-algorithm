import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] material;    // matrix that mark the open points.
    private WeightedQuickUnionUF uf; // uf that mark the connection
    private WeightedQuickUnionUF ufIsFull; // uf for checking isFull, avoiding washback
    private int n;                   // size of a dimension
    private int numOfOpen;           // the number of open points.
    // private boolean Percolates;    // mark that if the sys percolate

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Wrong size!!");
        } else {
            this.n = n;
            this.numOfOpen = 0;
            material = new boolean[n+1][n+1];
            for (int i = 1; i <= n; ++i) {
                for (int j = 1; j <= n; ++j) {
                    material[i][j] = false;
                }
            }
            this.uf = new WeightedQuickUnionUF(n * n + 2);
            this.ufIsFull = new WeightedQuickUnionUF(n * n + 1);
            // the scope of material is between 1 and n*n. 
            // in the uf we let the point [0] be the virtual top point, 
            // and the point [n*n + 1] be the virtual bottom point.
        }
    }
    
    private int getPosInUF(int row, int col) {
        return (row - 1) * n + col;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isOpen(row, col)) {
            return;
        }

        if ((1 <= row && row <= this.n) && (1 <= col && col <= this.n)) {
            this.material[row][col] = true;
            this.numOfOpen += 1;

            int point = getPosInUF(row, col);
            if (row == 1) { // if a top point is opened, 
                uf.union(0, point);           // connect it with virtual top point
                ufIsFull.union(0, point);
            } else if (row == n) { // if a bottom point is opened,
                uf.union(n * n + 1, point);     // connect it with virtual bottom point
            }

            int[] x = {1, 0, -1, 0};
            int[] y = {0, 1, 0, -1};
            for (int i = 0; i < x.length; ++i) { // try 4 directions
                int neiRow = row + x[i];
                int neiCol = col + y[i];
                int neiPoint = getPosInUF(neiRow, neiCol);
                if ((1 <= neiRow && neiRow <= this.n) && (1 <= neiCol && neiCol <= this.n)) {
                    if (isOpen(neiRow, neiCol)) {
                        uf.union(point, neiPoint); // connect with neighbor point
                        ufIsFull.union(point, neiPoint);
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("Exceeds the size!");
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if ((1 <= row && row <= this.n) && (1 <= col && col <= this.n)) {
            return material[row][col];
        } else {
            throw new IllegalArgumentException("Exceed the size!!");
        }
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if ((1 <= row && row <= this.n) && (1 <= col && col <= this.n)) {
            int point = getPosInUF(row, col);
            return ufIsFull.find(0) == ufIsFull.find(point);
        } else {
            throw new IllegalArgumentException("Exceed the size!!");
        }
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.numOfOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(0) == uf.find(n * n + 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation p = new Percolation(4);
        p.open(1, 3);
        p.open(2, 3);
        p.open(3, 2);
        p.open(4, 2);

        System.out.println("Test 1: percolate");
        if (p.percolates()) {
            System.out.println("yes!!");
        } else {
            System.out.println("NOOOOO!!!");
        }

        System.out.println("Test 2: isFull");
        if (p.isFull(2, 3)) {
            System.out.println("yes!!!");
        } else {
            System.out.println("NOO!!!");
        }

        System.out.println("Test 3: numberOfOpenSites");
        System.out.println(p.numberOfOpenSites());
    }
}
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private byte[] status;           // mark the site's status.
    /*  state   open    topCnt  BtmCnt
     *  0b000   No      No      No 
     *  0b100   Yes     No      No
     *  0b110   Yes     Yes     No
     *  0b101   Yes     No      Yes
     *  0b111   Yes     Yes     Yes
    */
    private WeightedQuickUnionUF uf; // uf that mark the connection
    private int n;                   // size of a dimension
    private int numOfOpen;           // the number of open sites.
    private boolean isPercolated;    // mark that if the block is percolated

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Wrong size!!");
        }
        
        status = new byte[n * n + 1];
        this.uf = new WeightedQuickUnionUF(n * n + 1);
        this.n = n;
        this.numOfOpen = 0;
        this.isPercolated = false;
        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j <= n; ++j) {
                int site = getPosInUF(i, j);
                status[site] = 0b000;
            }
        }
    }
    
    private int getPosInUF(int row, int col) {
        return (row - 1) * n + col;
    }


    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isOpen(row, col)) {
            return;
        } else if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("Exceeds the size!");
        }

        // init the new open site's status
        this.numOfOpen += 1;
        int site = getPosInUF(row, col);
        if (n == 1) {
            this.status[site] = 0b111;
        } else if (row == 1) {
            this.status[site] = 0b110;
        } else if (row == n) {
            this.status[site] = 0b101;
        } else {
            this.status[site] = 0b100;
        }

        // try 4 directions and spread the status to the new open site's
        int[] x = {1, 0, -1, 0};
        int[] y = {0, 1, 0, -1};
        for (int i = 0; i < x.length; ++i) { 
            int neiRow = row + x[i];
            int neiCol = col + y[i];
            int neiSite = getPosInUF(neiRow, neiCol);
            if ((1 <= neiRow && neiRow <= this.n) && (1 <= neiCol && neiCol <= this.n)) {
                if (isOpen(neiRow, neiCol)) {
                    status[site] |= status[uf.find(neiSite)];
                    uf.union(site, neiSite);
                }
            }
        }

        // check if the sys has been percolated after open this site
        if (status[site] == 0b111) {
            this.isPercolated = true;
        }

        // update the connected component's status with the new open sites's
        status[uf.find(site)] = status[site];
        
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("Exceeds the size!");
        }

        int site = getPosInUF(row, col);
        // because every opened site in open is marked as open,
        // dont need to use find() here!
        return ((status[site] & 0b100) != 0);
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("Exceeds the size!");
        }

        int site = getPosInUF(row, col);
        return ((status[uf.find(site)]  & 0b010) != 0);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.numOfOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        return this.isPercolated;
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
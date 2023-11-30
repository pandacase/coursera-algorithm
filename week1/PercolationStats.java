import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] results;

    private double mean;
    private double stddev;
    private double confidenceLo;
    private double confidenceHi;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Wrong size!!");
        }

        this.results = new double[trials];
        for (int i = 0; i < trials; ++i) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                // System.out.println("test");
                int randX = StdRandom.uniformInt(1, n + 1);
                int randY = StdRandom.uniformInt(1, n + 1);
                p.open(randX, randY);
            }
            // now the p has been percolates!
            results[i] = (double) p.numberOfOpenSites() / (n * n);
        }

        this.mean = StdStats.mean(this.results);
        this.stddev = StdStats.stddev(this.results);
        double tmp = 1.96 * this.stddev / Math.sqrt((double) trials);
        this.confidenceLo = this.mean - tmp;
        this.confidenceHi = this.mean + tmp;
    }

    // sample mean of percolation threshold
    public double mean() {
        return this.mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return this.stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.confidenceHi;
    }

    // test client (see below)
    public static void main(String[] args) {
        try {
            int n = Integer.parseInt(args[0]);
            int t = Integer.parseInt(args[1]);
            PercolationStats pp = new PercolationStats(n, t);

            System.out.println("mean                    = " + pp.mean());
            System.out.println("stddev                  = " + pp.stddev());
            System.out.println("95% confidence interval = [" + pp.confidenceLo() + ", " + pp.confidenceHi() + "]");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Need 2 args: n and T !");
        }
    }

}
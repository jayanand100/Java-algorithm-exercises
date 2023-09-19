import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] x;
    private int noOfExperiments;

    public PercolationStats(int n, int trials) {
        noOfExperiments = trials;
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Given n <= 0 || trials <= 0");

        }

        x = new double[noOfExperiments];
        for (int t = 0; t < noOfExperiments; t++) {
            int numOfOpens = 0;
            Percolation pc = new Percolation(n);
            while (!pc.percolates()) {
                int i = StdRandom.uniform(1, n + 1);
                int j = StdRandom.uniform(1, n + 1);
                if (!pc.isOpen(i, j) && !pc.isFull(i, j)) {
                    pc.open(i, j);
                }
            }
            numOfOpens = pc.numberOfOpenSites();
            x[t] = (double) numOfOpens / (n * n);
        }
    }

    public double mean() {
        return StdStats.mean(x);
    }

    public double stddev() {
        return StdStats.stddev(x);
    }

    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(noOfExperiments));

    }

    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(noOfExperiments));

    }

    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]),
                                                   Integer.parseInt(args[1]));
        String confidence = ps.confidenceLo() + ", " + ps.confidenceHi();
        StdOut.println("mean                   =" + ps.mean());
        StdOut.println("stddev                 =" + ps.stddev());
        StdOut.println("95% confidence interval=" + confidence);
    }
}






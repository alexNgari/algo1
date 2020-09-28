/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double[] results;
    private static final double CONFIDENCE_95 = 1.96;
    private double mean;
    private double stddev;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (trials <= 0) {
            throw new IllegalArgumentException("Trials must be greater than 0");
        }
        results = new double[trials];
        for (int trial = 0; trial < trials; trial++) {
            boolean percolate = false;
            Percolation client = new Percolation(n);
            while (!percolate) {
                client.open(StdRandom.uniform(1,n+1), StdRandom.uniform(1, n+1));
                percolate = client.percolates();
            }
            results[trial] = (double)client.numberOfOpenSites()/(n*n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        mean = StdStats.mean(results);
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        stddev = StdStats.stddev(results);
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean-CONFIDENCE_95*stddev/Math.sqrt(results.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean+CONFIDENCE_95*stddev/Math.sqrt(results.length);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats statistics = new PercolationStats(n, T);
        StdOut.println(String.format("mean \t\t\t\t= %f", statistics.mean()));
        StdOut.println(String.format("stddev \t\t\t\t= %.16f", statistics.stddev()));
        StdOut.println(String.format("95%% confidence interval \t= [%.16f, %.16f]",
                                     statistics.confidenceLo(), statistics.confidenceHi()));
    }
}

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double mean;
    private double stddev;
    private double confidenceLo;
    private double confidenceHi;

    public PercolationStats(int n, int iterTimes, UFType type) {
        if (n <= 0 || iterTimes <= 0)
            throw new IllegalArgumentException("Illegal Argument Exception: n = " + n + "iterTimes = " + iterTimes);
        if (n == 1) {
            mean = 1;
            stddev = Double.NaN;
            confidenceLo = Double.NaN;
            confidenceHi = Double.NaN;
            return;
        }

        double[] experiments = new double[iterTimes];
        for (int i = 0; i < iterTimes; i++) {
            Percolation percolation = new Percolation(n, type);
            int count = 0;
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                if (!percolation.isOpen(row, col)) {
                    //StdOut.println("# " + row + " " + col);
                    percolation.open(row, col);
                    count++;
                }
            }
            experiments[i] = ((double) count) / (n * n);
        }
        mean = StdStats.mean(experiments);
        stddev = StdStats.stddev(experiments);
        confidenceLo = mean - (1.96 * stddev) / Math.sqrt(iterTimes);
        confidenceHi = mean + (1.96 * stddev) / Math.sqrt(iterTimes);
    }

    public static void main(String[] args) {
        int n = 200, iterTimes = 300;

        long start_time = System.nanoTime();
        PercolationStats stats1 = new PercolationStats(n, iterTimes, UFType.WeightedUnionFind);
        long timeUsed = System.nanoTime() - start_time;
        System.out.println("PercolationStats(n = " + n + ", iterTimes = " + iterTimes + ") with WeightedUnionFind");
        printResult(stats1, timeUsed);

        start_time = System.nanoTime();
        PercolationStats stats2 = new PercolationStats(n, iterTimes, UFType.FastUnionFind);
        timeUsed = System.nanoTime() - start_time;
        System.out.println("PercolationStats(n = " + n + ", iterTimes = " + iterTimes + ") with FastUnionFind");
        printResult(stats2, timeUsed);
    }

    private static void printResult(PercolationStats stats, long timeUsed) {
        System.out.println("mean            = " + stats.Mean());
        System.out.println("stddev          = " + stats.Stddev());
        System.out.println("confidence low  = " + stats.ConfidenceLo());
        System.out.println("confidence high = " + stats.ConfidenceHi());
        System.out.println("time used       = " + timeUsed * 1.0 / 1000000 + " ms");
    }

    public double Mean() {
        return mean;
    }

    public double Stddev() {
        return stddev;
    }

    public double ConfidenceLo() {
        return confidenceLo;
    }

    public double ConfidenceHi() {
        return confidenceHi;
    }
}
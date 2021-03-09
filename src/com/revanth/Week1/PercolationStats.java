package com.revanth.Week1;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {

    private static final double DEFAULT_MULTIPLIER = 1.96;
    private final double[] procThresholdList;
    private final int numberOfTrials;
    private final int dimOfGrid;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        numberOfTrials = trials;
        dimOfGrid = n;
        procThresholdList = new double[trials];
        for (int i = 0; i < trials; i++) {
            double threshold = this.performTrial();
            procThresholdList[i] = (threshold);
        }
    }

    // Performs a trial and gets Percolation Threshold
    private double performTrial() {
        Percolation percolation = new Percolation(dimOfGrid);
        while (!percolation.percolates()) {
            int randRow = StdRandom.uniform(1, dimOfGrid + 1);
            int randCol = StdRandom.uniform(1, dimOfGrid + 1);
            percolation.open(randRow, randCol);
        }
        return ((double) percolation.numberOfOpenSites()) / (dimOfGrid * dimOfGrid);
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(procThresholdList);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(procThresholdList);
    }


    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - DEFAULT_MULTIPLIER * stddev() / Math.sqrt(numberOfTrials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + DEFAULT_MULTIPLIER * stddev() / Math.sqrt(numberOfTrials);
    }

    private void info() {
        StdOut.printf("mean\t\t\t\t\t= %f\nstddev\t\t\t\t\t= %f\n95%% confidence interval\t= %f, %f\n",
                mean(), stddev(), confidenceLo(), confidenceHi());
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, t);
        ps.info();
    }
}

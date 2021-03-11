package com.revanth.DSAlgo;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

abstract class UF {
    int[] id;

    public UF(int N) {
        id = new int[N];
        for (int i = 0; i < N; i++) id[i] = i;

    }

    abstract void Union(int p, int q);

    abstract boolean connected(int p, int q);
}

class QuickFind extends UF {

    //O(N)
    public QuickFind(int N) {
        super(N);
    }

    // O(N)
    @Override
    void Union(int p, int q) {
        int idp = id[p];
        int idq = id[q];
        for (int i = 0; i < id.length; i++) {
            if (id[i] == idp) id[i] = idq;
        }
    }

    // O(1)
    @Override
    boolean connected(int p, int q) {
        return id[p] == id[q];
    }
}


class QuickUnion extends UF {

    //O(N)
    public QuickUnion(int N) {
        super(N);
    }

    //O(N)
    int root(int i) {
        while (id[i] != i) i = id[i];
        return i;
    }

    //O(N)
    @Override
    void Union(int p, int q) {
        int pRoot = root(p);
        int qRoot = root(q);
        id[pRoot] = qRoot; // Set parent of pRoot to q Root
    }

    //O(N)
    @Override
    boolean connected(int p, int q) {
        return root(p) == root(q);
    }
}

class WeightedQuickUnion extends UF {
    // Depth of any node x is at most lg N
    int[] weights;

    // O(N)
    public WeightedQuickUnion(int N) {
        super(N);
        weights = new int[N];
        Arrays.fill(weights, 1);
    }

    private int root(int i) {
        while (id[i] != i) i = id[i];
        return i;
    }

    //O(lg N)
    @Override
    void Union(int p, int q) {
        int biggerComp = weights[p] > weights[q] ? p : q;
        int smallerComp = (biggerComp == p) ? q : p;

        int rootBigComp = root(biggerComp);
        int rootSmallComp = root(smallerComp);

        if (rootBigComp == rootSmallComp) return;

        id[rootSmallComp] = rootBigComp;
        weights[rootBigComp] += weights[rootSmallComp];
    }

    //O(lg N)
    @Override
    boolean connected(int p, int q) {
        return root(p) == root(q);
    }
}


// One pass variant
// Make every other node in path point to its grandparent
class QuickUnionPathCompression1Pass extends QuickUnion {

    public QuickUnionPathCompression1Pass(int N) {
        super(N);
    }

    @Override
    int root(int i) {
        while (i != id[i]) {
            id[i] = id[id[i]];
            i = id[i];
        }
        return i;
    }
}

// 2 pass variant
// Make every other node in path point to its grandparent

class QuickUnionPathCompression2Pass extends QuickUnion {

    public QuickUnionPathCompression2Pass(int N) {
        super(N);
    }

    @Override
    int root(int i) {
        int cNode = i;
        while (cNode != id[cNode])
            cNode = id[cNode];
        int root = cNode;
        cNode = i;
        while (cNode != root) {
            int prevNode = cNode;
            cNode = id[cNode];
            id[prevNode] = root;
        }
        return root;
    }
}


public class UnionFind {

    public static void main(String[] args) {
        // write your code here
        int N = StdIn.readInt();
        QuickUnion qu = new QuickUnion(N);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (!qu.connected(p, q)) {
                qu.Union(p, q);
                StdOut.println(p + " " + q);
            }
        }
    }
}



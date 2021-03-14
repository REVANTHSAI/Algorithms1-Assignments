package com.revanth.DSAlgo;


public class MergeSort {

    private static void sort(int[] arr) {
        mergeSortRec(arr, new int[arr.length], 0, arr.length - 1);
    }

    private static void mergeSortRec(int[] arr, int[] auxArray, int left, int right) {
        if (right <= left)
            return;
        int mid = left + (right - left) / 2;
        mergeSortRec(arr, auxArray, left, mid);
        mergeSortRec(arr, auxArray, mid + 1, right);
        merge(arr, auxArray, left, mid, right);
    }

    private static void mergeSortIter(int[] arr) {
        int N = arr.length;
        int[] auxArray = new int[arr.length];

        for (int sz = 1; sz < N; sz = 2 * sz)
            for (int lo = 0; lo < N - sz; lo += sz + sz)
                merge(arr, auxArray, lo, lo + sz - 1, Math.min(lo + (2 * sz) - 1, N - 1));


    }

    private static void merge(int[] arr, int[] auxArray, int left, int mid, int right) {

        // Enhancement 1.
        // If first half and second half are already sorted then return.

        if (arr[mid] < arr[mid + 1])
            return;

        for (int k = left; k <= right; k++)
            auxArray[k] = arr[k];

        int lp = left, rp = mid + 1;


        for (int idx = left; idx <= right; idx++) {
            if (lp > mid)
                arr[idx] = auxArray[rp++];
            else if (rp > right)
                arr[idx] = auxArray[lp++];
            else if (auxArray[lp] < auxArray[rp])
                arr[idx] = auxArray[lp++];
            else
                arr[idx] = auxArray[rp++];
        }

    }

    public static void main(String[] args) {
        int[] arr = new int[]{9, 5, 7, 2, 9};
        mergeSortIter(arr);
        for (int i : arr)
            System.out.println(i);
    }

}

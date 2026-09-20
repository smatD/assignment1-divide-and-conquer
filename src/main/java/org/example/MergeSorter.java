package org.example;

public class MergeSorter {

    private long comparisons;
    private int maxDepth;

    private static final int CUTOFF = 10;

    public void sort(int[] array) {
        comparisons = 0;
        maxDepth = 0;

        if (array == null || array.length < 2) {
            return;
        }

        int[] buffer = new int[array.length];
        mergeSort(array, buffer, 0, array.length - 1, 1);
    }

    private void mergeSort(int[] array, int[] buffer,
                           int left, int right, int depth) {

        maxDepth = Math.max(maxDepth, depth);

        if (right - left + 1 <= CUTOFF) {
            insertionSort(array, left, right);
            return;
        }

        int middle = left + (right - left) / 2;

        mergeSort(array, buffer, left, middle, depth + 1);
        mergeSort(array, buffer, middle + 1, right, depth + 1);

        if (array[middle] <= array[middle + 1]) {
            return;
        }

        merge(array, buffer, left, middle, right);
    }

    private void insertionSort(int[] array, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int value = array[i];
            int j = i - 1;

            while (j >= left) {
                comparisons++;

                if (array[j] <= value) {
                    break;
                }

                array[j + 1] = array[j];
                j--;
            }

            array[j + 1] = value;
        }
    }

    private void merge(int[] array, int[] buffer,
                       int left, int middle, int right) {

        int i = left;
        int j = middle + 1;
        int k = left;

        while (i <= middle && j <= right) {
            comparisons++;

            if (array[i] <= array[j]) {
                buffer[k++] = array[i++];
            } else {
                buffer[k++] = array[j++];
            }
        }

        while (i <= middle) {
            buffer[k++] = array[i++];
        }

        while (j <= right) {
            buffer[k++] = array[j++];
        }

        for (int p = left; p <= right; p++) {
            array[p] = buffer[p];
        }
    }

    public long getComparisons() {
        return comparisons;
    }

    public int getMaxDepth() {
        return maxDepth;
    }
}
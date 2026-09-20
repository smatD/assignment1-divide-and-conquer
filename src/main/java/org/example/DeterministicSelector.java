package org.example;

public class DeterministicSelector {

    private long comparisons;
    private long swaps;
    private int maxDepth;

    public int select(int[] array, int k) {
        comparisons = 0;
        swaps = 0;
        maxDepth = 0;

        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty");
        }

        if (k < 0 || k >= array.length) {
            throw new IllegalArgumentException("Invalid k");
        }

        return select(array, 0, array.length - 1, k, 1);
    }

    private int select(int[] array, int left, int right,
                       int k, int depth) {

        maxDepth = Math.max(maxDepth, depth);

        if (left == right) {
            return array[left];
        }

        int pivot = medianOfMedians(array, left, right);

        int[] bounds = partition(array, left, right, pivot);

        int lessEnd = bounds[0];
        int greaterStart = bounds[1];

        if (k <= lessEnd) {
            return select(array, left, lessEnd, k, depth + 1);
        }

        if (k >= greaterStart) {
            return select(array, greaterStart, right, k, depth + 1);
        }

        return pivot;
    }

    private int medianOfMedians(int[] array, int left, int right) {

        int size = right - left + 1;

        if (size <= 5) {
            insertionSort(array, left, right);
            return array[left + size / 2];
        }

        int numberOfMedians = 0;

        for (int start = left; start <= right; start += 5) {

            int end = Math.min(start + 4, right);

            insertionSort(array, start, end);

            int median = start + (end - start) / 2;

            swap(array, left + numberOfMedians, median);

            numberOfMedians++;
        }

        return select(array,
                left,
                left + numberOfMedians - 1,
                left + numberOfMedians / 2,
                0);
    }

    private int[] partition(int[] array, int left,
                            int right, int pivot) {

        int less = left;
        int current = left;
        int greater = right;

        while (current <= greater) {

            comparisons++;

            if (array[current] < pivot) {
                swap(array, less, current);
                less++;
                current++;

            } else if (array[current] > pivot) {
                swap(array, current, greater);
                greater--;

            } else {
                current++;
            }
        }

        return new int[]{less - 1, greater + 1};
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

    private void swap(int[] array, int i, int j) {

        if (i == j) {
            return;
        }

        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;

        swaps++;
    }

    public long getComparisons() {
        return comparisons;
    }

    public long getSwaps() {
        return swaps;
    }

    public int getMaxDepth() {
        return maxDepth;
    }
}
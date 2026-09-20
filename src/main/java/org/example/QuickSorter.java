package org.example;

import java.util.Random;

public class QuickSorter {

    private final Random random = new Random();

    private long comparisons;
    private long swaps;
    private int maxDepth;

    public void sort(int[] array) {
        comparisons = 0;
        swaps = 0;
        maxDepth = 0;

        if (array == null || array.length < 2) {
            return;
        }

        quickSort(array, 0, array.length - 1, 1);
    }

    private void quickSort(int[] array, int low, int high, int depth) {

        while (low < high) {
            maxDepth = Math.max(maxDepth, depth);

            int pivotIndex = low + random.nextInt(high - low + 1);
            int pivot = array[pivotIndex];

            int[] parts = partition(array, low, high, pivot);

            int leftLow = low;
            int leftHigh = parts[0];

            int rightLow = parts[1];
            int rightHigh = high;

            int leftSize = leftHigh - leftLow + 1;
            int rightSize = rightHigh - rightLow + 1;

            if (leftSize <= 0) {
                low = rightLow;
                depth++;
            } else if (rightSize <= 0) {
                high = leftHigh;
                depth++;
            } else if (leftSize < rightSize) {
                quickSort(array, leftLow, leftHigh, depth + 1);
                low = rightLow;
                depth++;
            } else {
                quickSort(array, rightLow, rightHigh, depth + 1);
                high = leftHigh;
                depth++;
            }
        }
    }

    private int[] partition(int[] array, int low, int high, int pivot) {

        int i = low;
        int j = high;

        while (i <= j) {

            while (i <= high) {
                comparisons++;

                if (array[i] >= pivot) {
                    break;
                }

                i++;
            }

            while (j >= low) {
                comparisons++;

                if (array[j] <= pivot) {
                    break;
                }

                j--;
            }

            if (i <= j) {
                swap(array, i, j);
                i++;
                j--;
            }
        }

        return new int[]{j, i};
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
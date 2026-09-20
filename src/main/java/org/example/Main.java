package org.example;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        System.out.println("Divide and Conquer Assignment");

        int[] array = {
                8, 3, 5, 1, 9, 2, 7, 4, 6
        };

        System.out.println("\nOriginal:");
        System.out.println(Arrays.toString(array));

        MergeSorter mergeSorter = new MergeSorter();

        int[] mergeArray = array.clone();
        mergeSorter.sort(mergeArray);

        System.out.println("\nMergeSort:");
        System.out.println(Arrays.toString(mergeArray));

        QuickSorter quickSorter = new QuickSorter();

        int[] quickArray = array.clone();
        quickSorter.sort(quickArray);

        System.out.println("\nQuickSort:");
        System.out.println(Arrays.toString(quickArray));

        DeterministicSelector selector =
                new DeterministicSelector();

        int k = 4;

        int selected = selector.select(array.clone(), k);

        System.out.println("\nDeterministic Select:");
        System.out.println(
                "k = " + k + ", value = " + selected
        );

        Point[] points = {
                new Point(0, 0),
                new Point(5, 5),
                new Point(1, 1),
                new Point(10, 10),
                new Point(1.5, 1.5)
        };

        ClosestPairSolver solver =
                new ClosestPairSolver();

        ClosestPairSolver.Result result =
                solver.findClosestPair(points);

        System.out.println("\nClosest Pair:");
        System.out.println(result);

        System.out.println("\nRunning experiments...");

        Experiment.run();

        System.out.println("\nDone.");
    }
}
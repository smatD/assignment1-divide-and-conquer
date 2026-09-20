package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Experiment {

    private static final Random random = new Random(42);

    public static void run() {

        File directory = new File("results");

        if (!directory.exists()) {
            directory.mkdirs();
        }

        try (FileWriter writer =
                     new FileWriter("results/results.csv")) {

            writer.write("algorithm,n,input_type,time_ns,depth,operations\n");

            int[] sizes = {
                    100,
                    500,
                    1000,
                    5000,
                    10000,
                    20000
            };

            String[] types = {
                    "random",
                    "sorted",
                    "reverse",
                    "duplicate"
            };

            for (int n : sizes) {

                for (String type : types) {

                    int[] array = createArray(n, type);

                    runMergeSort(writer, array, n, type);
                    runQuickSort(writer, array, n, type);
                    runSelect(writer, array, n, type);
                }
            }

            int[] pointSizes = {
                    100,
                    500,
                    1000,
                    5000,
                    10000
            };

            for (int n : pointSizes) {
                runClosestPair(writer, n);
            }

            System.out.println("Results saved to results/results.csv");

        } catch (IOException e) {
            System.out.println("Could not write results: "
                    + e.getMessage());
        }
    }

    private static int[] createArray(int n, String type) {

        int[] array = new int[n];

        if (type.equals("random")) {

            for (int i = 0; i < n; i++) {
                array[i] = random.nextInt(1_000_000);
            }

        } else if (type.equals("sorted")) {

            for (int i = 0; i < n; i++) {
                array[i] = i;
            }

        } else if (type.equals("reverse")) {

            for (int i = 0; i < n; i++) {
                array[i] = n - i;
            }

        } else {

            for (int i = 0; i < n; i++) {
                array[i] = random.nextInt(10);
            }
        }

        return array;
    }

    private static void runMergeSort(
            FileWriter writer,
            int[] original,
            int n,
            String type) throws IOException {

        int[] array = original.clone();

        MergeSorter sorter = new MergeSorter();

        long start = System.nanoTime();
        sorter.sort(array);
        long end = System.nanoTime();

        writer.write(
                "MergeSort," +
                        n + "," +
                        type + "," +
                        (end - start) + "," +
                        sorter.getMaxDepth() + "," +
                        sorter.getComparisons() +
                        "\n"
        );
    }

    private static void runQuickSort(
            FileWriter writer,
            int[] original,
            int n,
            String type) throws IOException {

        int[] array = original.clone();

        QuickSorter sorter = new QuickSorter();

        long start = System.nanoTime();
        sorter.sort(array);
        long end = System.nanoTime();

        writer.write(
                "QuickSort," +
                        n + "," +
                        type + "," +
                        (end - start) + "," +
                        sorter.getMaxDepth() + "," +
                        sorter.getComparisons() +
                        "\n"
        );
    }

    private static void runSelect(
            FileWriter writer,
            int[] original,
            int n,
            String type) throws IOException {

        int[] array = original.clone();

        DeterministicSelector selector =
                new DeterministicSelector();

        int k = n / 2;

        long start = System.nanoTime();
        selector.select(array, k);
        long end = System.nanoTime();

        writer.write(
                "DeterministicSelect," +
                        n + "," +
                        type + "," +
                        (end - start) + "," +
                        selector.getMaxDepth() + "," +
                        selector.getComparisons() +
                        "\n"
        );
    }

    private static void runClosestPair(
            FileWriter writer,
            int n) throws IOException {

        Point[] points = new Point[n];

        for (int i = 0; i < n; i++) {
            points[i] = new Point(
                    random.nextDouble() * 10000,
                    random.nextDouble() * 10000
            );
        }

        ClosestPairSolver solver =
                new ClosestPairSolver();

        long start = System.nanoTime();
        solver.findClosestPair(points);
        long end = System.nanoTime();

        writer.write(
                "ClosestPair," +
                        n +
                        ",random," +
                        (end - start) +
                        "," +
                        solver.getMaxDepth() +
                        "," +
                        solver.getComparisons() +
                        "\n"
        );
    }
}
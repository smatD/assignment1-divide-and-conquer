package org.example;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class AlgorithmTest {

    @Test
    public void testMergeSort() {

        int[][] tests = {
                {},
                {1},
                {2, 1},
                {5, 4, 3, 2, 1},
                {1, 2, 3, 4, 5},
                {5, 5, 5, 5},
                {3, 1, 2, 1, 3},
                {10, -2, 7, 0, -5}
        };

        for (int[] test : tests) {

            int[] expected = test.clone();
            Arrays.sort(expected);

            MergeSorter sorter = new MergeSorter();

            sorter.sort(test);

            assertArrayEquals(expected, test);
        }
    }

    @Test
    public void testQuickSort() {

        int[][] tests = {
                {},
                {1},
                {2, 1},
                {5, 4, 3, 2, 1},
                {1, 2, 3, 4, 5},
                {5, 5, 5, 5},
                {3, 1, 2, 1, 3},
                {10, -2, 7, 0, -5}
        };

        for (int[] test : tests) {

            int[] expected = test.clone();
            Arrays.sort(expected);

            QuickSorter sorter = new QuickSorter();

            sorter.sort(test);

            assertArrayEquals(expected, test);
        }
    }

    @Test
    public void testDeterministicSelect() {

        Random random = new Random(123);

        for (int test = 0; test < 200; test++) {

            int size = 1 + random.nextInt(100);

            int[] array = new int[size];

            for (int i = 0; i < size; i++) {
                array[i] = random.nextInt(50);
            }

            int[] expected = array.clone();
            Arrays.sort(expected);

            DeterministicSelector selector =
                    new DeterministicSelector();

            for (int k = 0; k < size; k++) {

                int[] copy = array.clone();

                int result = selector.select(copy, k);

                assertEquals(expected[k], result);
            }
        }
    }

    @Test
    public void testClosestPair() {

        Random random = new Random(123);

        for (int test = 0; test < 100; test++) {

            int size = 2 + random.nextInt(50);

            Point[] points = new Point[size];

            for (int i = 0; i < size; i++) {

                points[i] = new Point(
                        random.nextDouble() * 100,
                        random.nextDouble() * 100
                );
            }

            ClosestPairSolver solver =
                    new ClosestPairSolver();

            ClosestPairSolver.Result result =
                    solver.findClosestPair(points);

            double expected =
                    bruteForceClosestDistance(points);

            assertEquals(
                    expected,
                    result.distance,
                    1e-9
            );
        }
    }

    private double bruteForceClosestDistance(Point[] points) {

        double best = Double.MAX_VALUE;

        for (int i = 0; i < points.length; i++) {

            for (int j = i + 1; j < points.length; j++) {

                double distance =
                        points[i].distanceTo(points[j]);

                if (distance < best) {
                    best = distance;
                }
            }
        }

        return best;
    }

    @Test
    public void testClosestPairKnownExample() {

        Point[] points = {
                new Point(0, 0),
                new Point(10, 10),
                new Point(1, 1),
                new Point(20, 20)
        };

        ClosestPairSolver solver =
                new ClosestPairSolver();

        ClosestPairSolver.Result result =
                solver.findClosestPair(points);

        assertEquals(
                Math.sqrt(2),
                result.distance,
                1e-9
        );
    }
}
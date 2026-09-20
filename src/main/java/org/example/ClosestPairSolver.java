package org.example;

import java.util.Arrays;

public class ClosestPairSolver {

    private int maxDepth;
    private long comparisons;

    public static class Result {
        public Point p1;
        public Point p2;
        public double distance;

        public Result(Point p1, Point p2, double distance) {
            this.p1 = p1;
            this.p2 = p2;
            this.distance = distance;
        }

        @Override
        public String toString() {
            return p1 + " - " + p2 + " = " + distance;
        }
    }

    public Result findClosestPair(Point[] points) {

        maxDepth = 0;
        comparisons = 0;

        if (points == null || points.length < 2) {
            throw new IllegalArgumentException(
                    "At least two points are required"
            );
        }

        Point[] byX = points.clone();

        Arrays.sort(byX, (a, b) -> {
            int result = Double.compare(a.x, b.x);

            if (result == 0) {
                return Double.compare(a.y, b.y);
            }

            return result;
        });

        Point[] byY = points.clone();

        Arrays.sort(byY, (a, b) -> {
            int result = Double.compare(a.y, b.y);

            if (result == 0) {
                return Double.compare(a.x, b.x);
            }

            return result;
        });

        return solve(byX, byY, 1);
    }

    private Result solve(Point[] byX, Point[] byY, int depth) {

        maxDepth = Math.max(maxDepth, depth);

        int n = byX.length;

        if (n <= 3) {
            return bruteForce(byX);
        }

        int mid = n / 2;

        Point[] leftX = Arrays.copyOfRange(byX, 0, mid);
        Point[] rightX = Arrays.copyOfRange(byX, mid, n);

        Point[] leftY = new Point[mid];
        Point[] rightY = new Point[n - mid];

        java.util.HashSet<Point> leftSet =
                new java.util.HashSet<>();

        for (Point p : leftX) {
            leftSet.add(p);
        }

        int li = 0;
        int ri = 0;

        for (Point p : byY) {

            if (leftSet.contains(p)) {
                leftY[li++] = p;
            } else {
                rightY[ri++] = p;
            }
        }

        Result leftResult =
                solve(leftX, leftY, depth + 1);

        Result rightResult =
                solve(rightX, rightY, depth + 1);

        Result best;

        if (leftResult.distance <= rightResult.distance) {
            best = leftResult;
        } else {
            best = rightResult;
        }

        double delta = best.distance;

        double middleX = byX[mid].x;

        Point[] strip = new Point[n];
        int stripSize = 0;

        for (Point p : byY) {

            if (Math.abs(p.x - middleX) < delta) {
                strip[stripSize++] = p;
            }
        }

        for (int i = 0; i < stripSize; i++) {

            for (int j = i + 1;
                 j < stripSize &&
                         strip[j].y - strip[i].y < delta;
                 j++) {

                comparisons++;

                double distance =
                        strip[i].distanceTo(strip[j]);

                if (distance < best.distance) {

                    best = new Result(
                            strip[i],
                            strip[j],
                            distance
                    );

                    delta = distance;
                }
            }
        }

        return best;
    }

    private Result bruteForce(Point[] points) {

        Result best = new Result(
                points[0],
                points[1],
                points[0].distanceTo(points[1])
        );

        for (int i = 0; i < points.length; i++) {

            for (int j = i + 1; j < points.length; j++) {

                comparisons++;

                double distance =
                        points[i].distanceTo(points[j]);

                if (distance < best.distance) {

                    best = new Result(
                            points[i],
                            points[j],
                            distance
                    );
                }
            }
        }

        return best;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public long getComparisons() {
        return comparisons;
    }
}
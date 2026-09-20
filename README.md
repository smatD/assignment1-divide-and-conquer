# Assignment 1 Divide-and-Conquer Algorithm Analysis

## Project Overview

This project implements and analyzes four classic divide-and-conquer algorithms:

MergeSort
Randomized QuickSort
Deterministic Select using Median-of-Medians
Closest Pair of Points

The purpose of the assignment is to compare theoretical algorithmic complexity with practical execution results.

The algorithms were implemented in Java and tested using JUnit. Execution time was measured using System.nanoTime(). The program also records maximum recursion depth and an additional operation metric.

The project uses Maven and Java 21.

# Project Structure

```
assignment1-divide-and-conquer
-src/
--main/java/org/example/
---MergeSorter.java
---QuickSorter.java
---DeterministicSelector.java
---ClosestPairSolver.java
---Experiment.java
---Point.java
---Main.java
--test/java/org/example/
---AlgorithmTest.java
-docs/
--screenshots/
--plots/
-results/
--results.csv
-README.md
-pom.xml
-.gitignore
```

## Algorithm Analysis

## 1. MergeSort

MergeSort divides the input array into two approximately equal parts. Each part is recursively sorted, and the two sorted parts are then merged.

The implementation uses a reusable auxiliary buffer instead of creating a new buffer during every merge. For small subarrays, insertion sort is used as a cutoff optimization.

### Complexity

Best case:

```
Θ(n log n)
```

Average case:

```
Θ(n log n)
```

Worst case:

```
Θ(n log n)
```

Additional auxiliary space:

```
Θ(n)
```

### Recurrence

The recurrence is:

```
T(n) = 2T(n/2) + Θ(n)
```

Using the Master Theorem:

```
a = 2
b = 2
f(n) = Θ(n)
```

Since:

```
n^(log2(2)) = n
```

the recurrence is:

```
Θ(n log n)
```

The measured recursion depth increased approximately logarithmically as the input size increased.

## 2. Randomized QuickSort

QuickSort selects a random pivot and partitions the array around that pivot. Elements smaller than the pivot are placed on one side and larger elements on the other side.

The implementation performs partitioning in-place.

To reduce recursion depth, the algorithm recursively processes the smaller partition and iterates over the larger partition.

### Complexity

Expected:

```
Θ(n log n)
```

Worst case:

```
O(n²)
```

Average practical performance is generally close to:

```
Θ(n log n)
```

Space usage from recursion is approximately:

```
O(log n)
```

when smaller-first recursion is used.

### Recurrence

For a reasonably balanced partition:

```
T(n) = 2T(n/2) + Θ(n)
```

which gives:

```
Θ(n log n)
```

For a highly unbalanced partition:

```
T(n) = T(n - 1) + Θ(n)
```

which gives:

```
Θ(n^2)
```

Randomized pivot selection reduces the likelihood of repeatedly obtaining extremely unbalanced partitions.

---

## 3. Deterministic Select

Deterministic Select finds the k-th smallest element without completely sorting the array.

The implementation uses the Median-of-Medians algorithm. The elements are divided into groups of five. The median of each group is found, and the median of these medians is used as the pivot.

The array is then partitioned around the pivot, and only the partition containing the required element is processed recursively.

### Complexity

Best case:

```
Θ(n)
```

Average case:

```
Θ(n)
```

Worst case:

```
Θ(n)
```

The important property is that the worst-case complexity remains linear.

### Recurrence

The recurrence can be described approximately as:

```
T(n) = T(n/5) + T(7n/10) + Θ(n)
```

The first recursive term finds the median of the group medians.

The second term represents the remaining partition after the guaranteed-good pivot is used.

The linear partitioning work combined with the reduced recursive problem gives:

```
T(n) = Θ(n)
```

This is the main advantage of Median-of-Medians over a simple randomized selection algorithm when a worst-case guarantee is required.

## 4. Closest Pair of Points

The Closest Pair algorithm finds the two points with the smallest Euclidean distance.

The points are initially sorted by their x-coordinate and y-coordinate. The point set is divided into two halves. Each half is solved recursively.

After finding the closest pair in both halves, a vertical strip around the dividing line is examined. Points in the strip are already ordered by their y-coordinate, which allows only a small number of comparisons for each point.

### Complexity

Sorting:

```
Θ(n log n)
```

Recursive divide-and-conquer:

```
Θ(n log n)
```

Overall:

```
Θ(n log n)
```

Additional space:

```
O(n)
```

### Recurrence

The main recurrence is:

```
T(n) = 2T(n/2) + Θ(n)
```

Using the Master Theorem:

```
a = 2
b = 2
f(n) = Θ(n)
```

Therefore:

```
T(n) = Θ(n log n)
```

This is significantly better than checking every pair directly, which requires:

```
Θ(n^2)
```

---

# Experimental Results

## Experimental Setup

The experiments were performed using Java and System.nanoTime().

The sorting and selection algorithms were tested using:

- Random arrays
- Sorted arrays
- Reverse-sorted arrays
- Duplicate-heavy arrays

The tested sizes were:

```
100
500
1000
5000
10000
20000
```

Closest Pair was tested using:

```
100
500
1000
5000
10000
```

The program recorded:

- Execution time in nanoseconds
- Maximum recursion depth
- Number of comparisons or other algorithmic operations

The random generator used a fixed seed for the experiments, making the generated test data reproducible.

## Example Experimental Results

The complete measurements are stored in results/results.csv

# Discussion

## Do the results match theoretical complexity

The results are consistent with the expected theoretical behavior, although individual execution times do not increase perfectly smoothly.

MergeSort shows approximately logarithmic recursion depth and generally grows close to the expected n log n behavior.

QuickSort also generally performs close to n log n on the tested data, but its execution time and recursion depth vary because the pivot is randomized.

Deterministic Select shows approximately linear growth in the number of operations. Its execution time can sometimes be higher than expected for smaller inputs because Median-of-Medians performs additional work to guarantee a good pivot.

Closest Pair shows increasing execution time consistent with `n log n` behavior rather than the quadratic behavior of a brute-force solution.


## How does input structure affect performance

Input structure can affect practical execution time.

MergeSort is relatively insensitive to whether the input is sorted, reverse-sorted, or random because its division structure is predictable.

QuickSort is more affected by input structure because partitioning depends on the selected pivot. Randomized pivot selection reduces the effect of unfavorable input arrangements.

Duplicate-heavy inputs can also affect partitioning behavior. The three-way partitioning used by the implementation handles values equal to the pivot together, which can reduce unnecessary recursive work.

Deterministic Select can behave differently depending on the number of duplicate values because the partition separates values smaller than, equal to, and greater than the pivot.


## Why does smaller-first recursion help QuickSort

QuickSort can have very unbalanced partitions.

If the algorithm always recursively processes the first partition, a long chain of recursive calls can occur.

Instead, the implementation recursively processes the smaller partition and handles the larger partition using iteration.

This limits the amount of recursive stack space required.

Therefore, even if the partition sizes are unbalanced, the recursion depth can remain much smaller than the total number of QuickSort operations.


## Why does Median-of-Medians guarantee O(n)

Median-of-Medians groups elements into groups of five and finds the median of each group.

The median of those medians provides a pivot that is guaranteed to be reasonably close to the middle of the input.

Therefore, a large portion of the input can be discarded after each partition.

The algorithm performs linear work for grouping, finding medians, and partitioning, while the recursive problem is sufficiently smaller.

This results in the worst-case recurrence:

```
T(n) = T(n/5) + T(7n/10) + Θ(n)
```

which is:

```
Θ(n)
```

---

## Why is divide-and-conquer Closest Pair faster than O(n²)

The brute-force method checks every possible pair of points.

There are approximately:

```
n^2 / 2
```

pairs, so its complexity is:

```
Θ(n^2)
```

The divide-and-conquer algorithm instead divides the points into two halves and solves the smaller problems recursively.

It then only examines points close to the dividing line.

This reduces the overall complexity to:

```
Θ(n log n)
```

As n becomes large, the difference between n log n and n^2 becomes increasingly significant.

---

## Practical factors affecting performance

The measured execution time is affected by more than algorithmic complexity.

Important factors include:

- JVM warm-up
- JIT compilation
- Garbage collection
- CPU scheduling
- CPU cache behavior
- Memory allocation
- Operating-system background processes
- Java array copying
- Sorting overhead
- Random number generation

Because of these factors, System.nanoTime() measurements can vary between runs.

For this reason, theoretical complexity is more useful for describing long-term growth, while the experimental measurements show practical behavior on the specific computer and JVM used for the experiment.


# Reflection

This assignment helped demonstrate how divide-and-conquer algorithms work in actual Java programs rather than only as theoretical recurrences. I implemented MergeSort, randomized QuickSort, Deterministic Select, and the Closest Pair algorithm and then compared their measured behavior with their theoretical complexities. The experiments showed that recursion depth generally grows much more slowly than input size for algorithms such as MergeSort, while QuickSort has more variation because of randomized pivot selection.

One of the main implementation challenges was handling edge cases correctly, especially for Closest Pair when points have equal x-coordinates. Testing against reference implementations was useful for finding these problems. I also learned that measured execution time is not always perfectly smooth because Java's JVM, JIT compilation, memory management, and other system factors affect timing. Overall, the assignment showed the difference between theoretical complexity and practical performance.


# Screenshots

The `docs/screenshots/` directory should contain screenshots:

1. Program output from Main.
2. Successful Maven/JUnit tests.
3. Generated results.csv.
4. Time vs. n plot.
5. Recursion depth vs. n plot.

The plots are stored in:

```
docs/plots/
```

The experimental data is stored in:

```
results/results.csv
```


# Conclusion

The four implemented algorithms demonstrate different uses of divide-and-conquer.

MergeSort provides predictable Θ(n log n) sorting performance.

QuickSort usually provides Θ(n log n) performance but has a possible O(n^2) worst case. Randomized pivots and smaller-first recursion improve its practical behavior.

Deterministic Select provides a guaranteed Θ(n) worst-case selection algorithm using the Median-of-Medians technique.

Closest Pair demonstrates how dividing a geometric problem into smaller subproblems can reduce the complexity from `Θ(n²)` to `Θ(n log n)`.

The experimental results generally follow the expected theoretical trends, while differences between individual measurements demonstrate the influence of practical JVM and hardware factors.
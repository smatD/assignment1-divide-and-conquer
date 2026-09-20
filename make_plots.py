import csv
import os
import matplotlib.pyplot as plt

os.makedirs("docs/plots", exist_ok=True)

rows = []

with open("results/results.csv", "r") as file:
    reader = csv.DictReader(file)

    for row in reader:
        rows.append({
            "algorithm": row["algorithm"],
            "n": int(row["n"]),
            "input_type": row["input_type"],
            "time": int(row["time_ns"]),
            "depth": int(row["depth"]),
            "operations": int(row["operations"])
        })


# --------------------------------------------------
# TIME VS N
# --------------------------------------------------

algorithms = [
    "MergeSort",
    "QuickSort",
    "DeterministicSelect",
    "ClosestPair"
]

for algorithm in algorithms:

    data = [
        row for row in rows
        if row["algorithm"] == algorithm
           and row["input_type"] == "random"
    ]

    x = [row["n"] for row in data]
    y = [row["time"] / 1_000_000 for row in data]

    plt.figure()

    plt.plot(x, y, marker="o")

    plt.xlabel("Input size (n)")
    plt.ylabel("Time (ms)")
    plt.title(algorithm + " - Time vs n")

    plt.grid(True)

    filename = (
            "docs/plots/"
            + algorithm.lower()
            + "_time.png"
    )

    plt.savefig(filename, dpi=150)
    plt.close()


# --------------------------------------------------
# RECURSION DEPTH VS N
# --------------------------------------------------

for algorithm in algorithms:

    data = [
        row for row in rows
        if row["algorithm"] == algorithm
           and row["input_type"] == "random"
    ]

    x = [row["n"] for row in data]
    y = [row["depth"] for row in data]

    plt.figure()

    plt.plot(x, y, marker="o")

    plt.xlabel("Input size (n)")
    plt.ylabel("Maximum recursion depth")
    plt.title(algorithm + " - Recursion Depth vs n")

    plt.grid(True)

    filename = (
            "docs/plots/"
            + algorithm.lower()
            + "_depth.png"
    )

    plt.savefig(filename, dpi=150)
    plt.close()


print("Plots created successfully.")
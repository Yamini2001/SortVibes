# SortVibes - Sorting Visualizer

SortVibes is a Java Swing-based application that visually demonstrates the sorting process using different sorting algorithms. The project provides an interactive way to observe sorting, featuring additional functionalities like resetting the array, shuffling, and real-time statistics.

## Features

- **Sorting Algorithm Visualization:** Experience the sorting process using various sorting algorithms.
- **Array Reset and Shuffle:** Easily reset the array to its original state or shuffle it randomly.
- **Real-time Statistics:** View the number of comparisons and swaps during the sorting process.
- **Responsive Design:** Ensure a clear and readable visualization, adapting to changes in window size.

## Sorting Algorithms

SortVibes supports the following sorting algorithms:

- **Bubble Sort:** A simple comparison-based algorithm that repeatedly steps through the list, compares adjacent elements, and swaps them if they are in the wrong order.

- **Selection Sort:** This algorithm sorts an array by repeatedly finding the minimum element from the unsorted part and putting it at the beginning.

- **Insertion Sort:** It builds the final sorted array one item at a time. It is much less efficient on large lists than more advanced algorithms such as quicksort, heapsort, or merge sort.

- **Merge Sort:** A divide and conquer algorithm that divides the input array into two halves, recursively sorts each half, and then merges them.

- **Quick Sort:** Another divide and conquer algorithm that picks an element as a pivot and partitions the array around the pivot.

## How to Run

To run SortVibes:

1. Clone the repository.
2. Compile and run `SortingVisualizer.java` using a Java IDE or command line.

```bash
javac SortingVisualizer.java
java SortingVisualizer

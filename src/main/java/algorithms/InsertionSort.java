package sorts;

import metrics.PerformanceTracker;

public class InsertionSort {

    /**
     * Sorts an array using insertion sort algorithm with performance tracking.
     * Optimized for nearly sorted data with early termination.
     *
     * @param arr the array to sort
     * @param tracker performance metrics tracker
     * @throws IllegalArgumentException if array is null
     */
    public static void sort(int[] arr, PerformanceTracker tracker) {
        if (arr == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }

        if (arr.length <= 1) {
            return;
        }

        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            tracker.incrementArrayAccesses(1);

            int j = i - 1;

            // Early termination
            tracker.incrementComparisons();
            if (arr[j] <= key) {
                tracker.incrementArrayAccesses(1);
                continue;
            }

            // Shift elements greater than key to the right
            while (j >= 0) {
                tracker.incrementComparisons();
                tracker.incrementArrayAccesses(1);

                if (arr[j] > key) {
                    arr[j + 1] = arr[j];
                    tracker.incrementSwaps();
                    tracker.incrementArrayAccesses(2);
                    j--;
                } else {
                    break;
                }
            }

            // Insert at correct postion
            arr[j + 1] = key;
            tracker.incrementArrayAccesses(1);
        }
    }

    /**
     * Sorts an array using insertion sort without performance tracking.
     *
     * @param arr the array to sort
     * @throws IllegalArgumentException if array is null
     */
    public static void sort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }

        if (arr.length <= 1) {
            return;
        }

        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;

            // Early termination
            if (arr[j] <= key) {
                continue;
            }

            // Shift elements
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }

            arr[j + 1] = key;
        }
    }

    /**
     * Checks if an array is sorted in ascending order.
     *
     * @param arr the array to check
     * @return true if sorted, false otherwise
     */
    public static boolean isSorted(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return true;
        }

        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) {
                return false;
            }
        }
        return true;
    }
}
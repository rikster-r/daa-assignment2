package algorithms;

import metrics.PerformanceTracker;

public class SelectionSort {

    public static void sort(int[] arr, PerformanceTracker tracker) {
        if (arr == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            tracker.incrementArrayAccesses(1);
            for (int j = i + 1; j < n; j++) {
                tracker.incrementComparisons();
                tracker.incrementArrayAccesses(1);
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
                tracker.incrementSwaps();
                tracker.incrementArrayAccesses(4);
            }
        }
    }

    public static void sort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
            }
        }
    }

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

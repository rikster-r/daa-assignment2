package algorithms;

import metrics.PerformanceTracker;

public class SelectionSort {

    public static void sort(int[] arr) {
        PerformanceTracker tracker = new PerformanceTracker();
        sort(arr, tracker);
    }

    public static void sort(int[] arr, PerformanceTracker tracker) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                tracker.incrementComparisons();
                tracker.incrementArrayAccesses(2);
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }
            if (minIdx != i) {
                int tmp = arr[minIdx];
                arr[minIdx] = arr[i];
                arr[i] = tmp;
                tracker.incrementSwaps();
                tracker.incrementArrayAccesses(4);
            }
        }
    }

    public static boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1] > arr[i]) return false;
        }
        return true;
    }
}

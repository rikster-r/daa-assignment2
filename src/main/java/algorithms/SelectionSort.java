package algorithms;

import metrics.PerformanceTracker;

public class SelectionSort {

    public static void sort(int[] arr) {
        PerformanceTracker tracker = new PerformanceTracker();
        sort(arr, tracker);
    }

    public static void sort(int[] arr, PerformanceTracker tracker) {
        int n = arr.length;
        boolean alreadySorted;

        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            alreadySorted = true;

            for (int j = i + 1; j < n; j++) {
                tracker.incrementComparisons();
                tracker.incrementArrayAccesses(2);

                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }

                // Detect not sorted yet
                if (arr[j] < arr[j - 1]) {
                    alreadySorted = false;
                }
            }

            if (minIdx != i) {
                int tmp = arr[minIdx];
                arr[minIdx] = arr[i];
                arr[i] = tmp;
                tracker.incrementSwaps();
                tracker.incrementArrayAccesses(4);
            }

            // Early termination if already sorted
            if (alreadySorted) {
                break;
            }
        }
    }
}

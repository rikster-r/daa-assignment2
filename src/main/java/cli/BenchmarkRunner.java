package cli;

import metrics.PerformanceTracker;
import algorithms.SelectionSort;
import java.util.Random;

public class BenchmarkRunner {

    public static BenchmarkResult runBenchmark(String algorithm, int size, String inputType) {
        int[] arr = generateArray(size, inputType);
        PerformanceTracker tracker = new PerformanceTracker();

        tracker.start();

        switch (algorithm.toLowerCase()) {
            case "selection":
                SelectionSort.sort(arr, tracker);
                break;
            default:
                System.out.println("Unknown algorithm: " + algorithm);
                return null;
        }

        tracker.stop();

        tracker.exportToCSV("benchmark_results.csv", algorithm + "-" + inputType, size);

        return new BenchmarkResult(algorithm, size, inputType, tracker);
    }

    public static int[] generateArray(int size, String type) {
        int[] arr = new int[size];
        Random rand = new Random();

        switch (type.toLowerCase()) {
            case "sorted":
                for (int i = 0; i < size; i++) arr[i] = i;
                break;
            case "reversed":
                for (int i = 0; i < size; i++) arr[i] = size - i;
                break;
            case "nearly-sorted":
                for (int i = 0; i < size; i++) arr[i] = i;
                for (int i = 0; i < size / 10; i++) {
                    int idx1 = rand.nextInt(size);
                    int idx2 = rand.nextInt(size);
                    int tmp = arr[idx1];
                    arr[idx1] = arr[idx2];
                    arr[idx2] = tmp;
                }
                break;
            default: // random
                for (int i = 0; i < size; i++) arr[i] = rand.nextInt(10000);
        }
        return arr;
    }

    public static class BenchmarkResult {
        private final String algorithm;
        private final int size;
        private final String inputType;
        private final PerformanceTracker tracker;

        public BenchmarkResult(String algorithm, int size, String inputType, PerformanceTracker tracker) {
            this.algorithm = algorithm;
            this.size = size;
            this.inputType = inputType;
            this.tracker = tracker;
        }

        @Override
        public String toString() {
            return String.format(
                    "%s sort | size=%d | input=%s | comparisons=%d | swaps=%d | accesses=%d | allocations=%d | time=%d ms",
                    algorithm, size, inputType,
                    tracker.getComparisons(),
                    tracker.getSwaps(),
                    tracker.getArrayAccesses(),
                    tracker.getMemoryAllocations(),
                    tracker.getExecutionTimeMillis()
            );
        }
    }
}

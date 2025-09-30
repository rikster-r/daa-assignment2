package cli;

import metrics.PerformanceTracker;
import algorithms.InsertionSort;
import java.util.Random;

public class BenchmarkRunner {

    public static BenchmarkResult runBenchmark(String algorithm, int size) {
        int[] arr = generateArray(size);
        PerformanceTracker tracker = new PerformanceTracker();

        tracker.start();

        switch (algorithm.toLowerCase()) {
            case "insertion":
                InsertionSort.sort(arr, tracker);
                break;
            case "selection":
                // SelectionSort.sort(arr, tracker); // plug in later
                System.out.println("Selection sort not yet implemented");
                break;
            default:
                System.out.println("Unknown algorithm: " + algorithm);
                return null;
        }

        tracker.stop();
        return new BenchmarkResult(algorithm, size, tracker);
    }

    public static int[] generateArray(int size) {
        int[] arr = new int[size];
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt(10000);
        }
        return arr;
    }

    /**
     * Runs benchmarks for insertion sort on random data
     */
    public static void runComprehensiveBenchmark() {
        String[] algorithms = { "insertion" };
        int[] sizes = { 100, 500, 1000, 2000, 5000 };

        System.out.println("Starting Benchmark (Random Arrays)...\n");

        for (String algorithm : algorithms) {
            for (int size : sizes) {
                BenchmarkResult result = runBenchmark(algorithm, size);
                if (result != null) {
                    System.out.println(result);
                    result.getTracker().exportToCSV("benchmark_results.csv", algorithm, size);
                }
            }
            System.out.println(); // Blank line between algorithms
        }

        System.out.println("Benchmark complete. Results saved to benchmark_results.csv");
    }

    /**
     * Main method to run example benchmarks
     */
    public static void main(String[] args) {
        // Example usage
        System.out.println("=== Single Benchmark Example ===");
        BenchmarkResult result = runBenchmark("insertion", 1000);
        if (result != null) {
            System.out.println(result);
            System.out.println();
        }

        // Run comprehensive benchmark
        System.out.println("=== Comprehensive Benchmark ===");
        runComprehensiveBenchmark();
    }

    public static class BenchmarkResult {
        private final String algorithm;
        private final int size;
        private final PerformanceTracker tracker;

        public BenchmarkResult(String algorithm, int size, PerformanceTracker tracker) {
            this.algorithm = algorithm;
            this.size = size;
            this.tracker = tracker;
        }

        public String getAlgorithm() {
            return algorithm;
        }

        public int getSize() {
            return size;
        }

        public PerformanceTracker getTracker() {
            return tracker;
        }

        @Override
        public String toString() {
            return String.format(
                    "%s sort | size=%d | comparisons=%d | swaps=%d | accesses=%d | allocations=%d | time=%d ms",
                    algorithm, size,
                    tracker.getComparisons(),
                    tracker.getSwaps(),
                    tracker.getArrayAccesses(),
                    tracker.getMemoryAllocations(),
                    tracker.getExecutionTimeMillis());
        }
    }
}

import metrics.PerformanceTracker;
import algorithms.SelectionSort;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeAll;

import java.io.File;
import java.util.Random;

class SelectionSortBenchmarkTest {

    private static final String CSV_FILE = "selection_sort_benchmark.csv";

    @BeforeAll
    static void setUp() {
        // Delete existing CSV file to start fresh
        File csvFile = new File(CSV_FILE);
        if (csvFile.exists()) {
            csvFile.delete();
        }
    }

    @Test
    @DisplayName("Benchmark: Small arrays (100 elements)")
    void benchmarkSmallArrays() {
        int size = 100;
        runBenchmarkForSize(size);
    }

    @Test
    @DisplayName("Benchmark: Medium arrays (500 elements)")
    void benchmarkMediumArrays() {
        int size = 500;
        runBenchmarkForSize(size);
    }

    @Test
    @DisplayName("Benchmark: Large arrays (1000 elements)")
    void benchmarkLargeArrays() {
        int size = 1000;
        runBenchmarkForSize(size);
    }

    @Test
    @DisplayName("Benchmark: Extra large arrays (2000 elements)")
    void benchmarkExtraLargeArrays() {
        int size = 2000;
        runBenchmarkForSize(size);
    }

    @Test
    @DisplayName("Benchmark: Very large arrays (5000 elements)")
    void benchmarkVeryLargeArrays() {
        int size = 5000;
        runBenchmarkForSize(size);
    }

    @Test
    @DisplayName("Benchmark: All sizes comprehensive test")
    void benchmarkComprehensive() {
        int[] sizes = {100, 500, 1000, 2000, 5000};

        System.out.println("\n=== Comprehensive Selection Sort Benchmark ===");
        System.out.println("Results will be exported to: " + CSV_FILE);
        System.out.println();

        for (int size : sizes) {
            System.out.println("Testing size: " + size);
            runBenchmarkForSize(size);
            System.out.println();
        }

        System.out.println("Benchmark complete! Check " + CSV_FILE + " for results.");
    }

    /**
     * Runs benchmarks for a specific array size across all input types
     */
    private void runBenchmarkForSize(int size) {
        String[] inputTypes = {"random", "sorted", "reversed", "nearly-sorted"};

        for (String inputType : inputTypes) {
            int[] arr = generateArray(size, inputType);
            PerformanceTracker tracker = new PerformanceTracker();

            tracker.reset();
            tracker.start();
            SelectionSort.sort(arr, tracker);
            tracker.stop();

            // Export to CSV
            tracker.exportToCSV(CSV_FILE, "SelectionSort", size);

            // Print results to console
            System.out.printf("  %-15s | comparisons=%7d | swaps=%7d | accesses=%7d | time=%5d ms%n",
                    inputType,
                    tracker.getComparisons(),
                    tracker.getSwaps(),
                    tracker.getArrayAccesses(),
                    tracker.getExecutionTimeMillis());

            // Verify correctness
            assert SelectionSort.isSorted(arr) : "Array should be sorted after selection sort";
        }
    }

    /**
     * Generates test arrays of different types
     */
    private int[] generateArray(int size, String type) {
        int[] arr = new int[size];
        Random rand = new Random(42); // Fixed seed for reproducibility

        switch (type.toLowerCase()) {
            case "sorted":
                for (int i = 0; i < size; i++) {
                    arr[i] = i;
                }
                break;

            case "reversed":
                for (int i = 0; i < size; i++) {
                    arr[i] = size - i;
                }
                break;

            case "nearly-sorted":
                // Start with sorted array
                for (int i = 0; i < size; i++) {
                    arr[i] = i;
                }
                // Swap 10% of elements randomly
                int swaps = size / 10;
                for (int i = 0; i < swaps; i++) {
                    int idx1 = rand.nextInt(size);
                    int idx2 = rand.nextInt(size);
                    int tmp = arr[idx1];
                    arr[idx1] = arr[idx2];
                    arr[idx2] = tmp;
                }
                break;

            case "random":
            default:
                for (int i = 0; i < size; i++) {
                    arr[i] = rand.nextInt(10000);
                }
                break;
        }

        return arr;
    }

    @Test
    @DisplayName("Benchmark: Best case vs Worst case comparison")
    void benchmarkBestVsWorst() {
        int size = 1000;

        System.out.println("\n=== Best Case vs Worst Case Analysis ===");

        // Best case: already sorted
        int[] sortedArr = generateArray(size, "sorted");
        PerformanceTracker bestCaseTracker = new PerformanceTracker();
        bestCaseTracker.start();
        SelectionSort.sort(sortedArr, bestCaseTracker);
        bestCaseTracker.stop();
        bestCaseTracker.exportToCSV(CSV_FILE, "SelectionSort-BestCase", size);

        System.out.printf("Best Case (sorted):   comparisons=%7d | swaps=%7d | time=%5d ms%n",
                bestCaseTracker.getComparisons(),
                bestCaseTracker.getSwaps(),
                bestCaseTracker.getExecutionTimeMillis());

        // Worst case: reverse sorted
        int[] reversedArr = generateArray(size, "reversed");
        PerformanceTracker worstCaseTracker = new PerformanceTracker();
        worstCaseTracker.start();
        SelectionSort.sort(reversedArr, worstCaseTracker);
        worstCaseTracker.stop();
        worstCaseTracker.exportToCSV(CSV_FILE, "SelectionSort-WorstCase", size);

        System.out.printf("Worst Case (reversed): comparisons=%7d | swaps=%7d | time=%5d ms%n",
                worstCaseTracker.getComparisons(),
                worstCaseTracker.getSwaps(),
                worstCaseTracker.getExecutionTimeMillis());

        // Calculate ratio
        double comparisonRatio = (double) worstCaseTracker.getComparisons() / bestCaseTracker.getComparisons();
        double swapRatio = (double) worstCaseTracker.getSwaps() / Math.max(1, bestCaseTracker.getSwaps());

        System.out.printf("%nPerformance Ratio (Worst/Best):%n");
        System.out.printf("  Comparisons: %.2fx%n", comparisonRatio);
        System.out.printf("  Swaps: %.2fx%n", swapRatio);
        
        System.out.printf("%nNote: Selection Sort has O(n²) comparisons regardless of input%n");
    }

    @Test
    @DisplayName("Benchmark: Input type consistency test")
    void benchmarkInputTypeConsistency() {
        int size = 1000;

        System.out.println("\n=== Input Type Consistency Test ===");
        System.out.println("Selection Sort comparison count should be consistent across input types");

        // Nearly sorted (10% shuffled)
        int[] nearlySortedArr = generateArray(size, "nearly-sorted");
        PerformanceTracker nearlySortedTracker = new PerformanceTracker();
        nearlySortedTracker.start();
        SelectionSort.sort(nearlySortedArr, nearlySortedTracker);
        nearlySortedTracker.stop();
        nearlySortedTracker.exportToCSV(CSV_FILE, "SelectionSort-NearlySorted", size);

        // Random
        int[] randomArr = generateArray(size, "random");
        PerformanceTracker randomTracker = new PerformanceTracker();
        randomTracker.start();
        SelectionSort.sort(randomArr, randomTracker);
        randomTracker.stop();
        randomTracker.exportToCSV(CSV_FILE, "SelectionSort-Random", size);

        // Sorted
        int[] sortedArr = generateArray(size, "sorted");
        PerformanceTracker sortedTracker = new PerformanceTracker();
        sortedTracker.start();
        SelectionSort.sort(sortedArr, sortedTracker);
        sortedTracker.stop();
        sortedTracker.exportToCSV(CSV_FILE, "SelectionSort-Sorted", size);

        System.out.printf("Nearly Sorted: comparisons=%7d | swaps=%7d | time=%5d ms%n",
                nearlySortedTracker.getComparisons(),
                nearlySortedTracker.getSwaps(),
                nearlySortedTracker.getExecutionTimeMillis());

        System.out.printf("Random:        comparisons=%7d | swaps=%7d | time=%5d ms%n",
                randomTracker.getComparisons(),
                randomTracker.getSwaps(),
                randomTracker.getExecutionTimeMillis());

        System.out.printf("Sorted:        comparisons=%7d | swaps=%7d | time=%5d ms%n",
                sortedTracker.getComparisons(),
                sortedTracker.getSwaps(),
                sortedTracker.getExecutionTimeMillis());

        // Verify consistency
        System.out.printf("%nComparison count variance:%n");
        System.out.printf("Nearly-Sorted vs Random: %d difference%n",
                Math.abs(nearlySortedTracker.getComparisons() - randomTracker.getComparisons()));
        System.out.printf("Random vs Sorted: %d difference%n",
                Math.abs(randomTracker.getComparisons() - sortedTracker.getComparisons()));
        System.out.printf("%nSelection Sort performs the same number of comparisons regardless of input order%n");
    }
}
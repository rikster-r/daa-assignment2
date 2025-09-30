import algorithms.SelectionSort;
import metrics.PerformanceTracker;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SelectionSortTest {

    @Test
    void testEmptyArray() {
        int[] arr = {};
        SelectionSort.sort(arr);
        assertTrue(SelectionSort.isSorted(arr));
    }

    @Test
    void testSingleElement() {
        int[] arr = {42};
        SelectionSort.sort(arr);
        assertTrue(SelectionSort.isSorted(arr));
    }

    @Test
    void testSortedArray() {
        int[] arr = {1, 2, 3, 4, 5};
        SelectionSort.sort(arr);
        assertTrue(SelectionSort.isSorted(arr));
    }

    @Test
    void testReverseArray() {
        int[] arr = {5, 4, 3, 2, 1};
        SelectionSort.sort(arr);
        assertTrue(SelectionSort.isSorted(arr));
    }

    @Test
    void testRandomArray() {
        int[] arr = {3, 1, 4, 1, 5, 9, 2};
        SelectionSort.sort(arr);
        assertTrue(SelectionSort.isSorted(arr));
    }

    @Test
    void testWithPerformanceTracker() {
        int[] arr = {5, 3, 8, 4, 2};
        PerformanceTracker tracker = new PerformanceTracker();
        tracker.start();
        SelectionSort.sort(arr, tracker);
        tracker.stop();
        assertTrue(SelectionSort.isSorted(arr));
        assertTrue(tracker.getComparisons() > 0);
        assertTrue(tracker.getSwaps() > 0);
    }
}

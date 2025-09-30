import cli.BenchmarkRunner;
import org.junit.jupiter.api.Test;

public class SelectionSortTest {

    @Test
    void benchmarkSelectionSort() {
        int[] sizes = {100, 1000, 5000};
        String[] inputTypes = {"random", "sorted", "reversed", "nearly-sorted"};

        for (int size : sizes) {
            for (String inputType : inputTypes) {
                BenchmarkRunner.BenchmarkResult result =
                        BenchmarkRunner.runBenchmark("selection", size, inputType);

                if (result != null) {
                    System.out.println(result);
                }
            }
        }
    }
}

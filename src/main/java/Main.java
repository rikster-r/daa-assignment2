import cli.*;
public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java Main <algorithm> <size> [inputType]");
            System.out.println("Algorithms: selection");
            System.out.println("Input types: random (default), sorted, reversed, nearly-sorted");
            return;
        }

        String algorithm = args[0].toLowerCase();
        int size;
        try {
            size = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Error: size must be an integer.");
            return;
        }
        String inputType = args.length > 2 ? args[2].toLowerCase() : "random";

        BenchmarkRunner.BenchmarkResult result = BenchmarkRunner.runBenchmark(algorithm, size, inputType);

        if (result != null) {
            System.out.println(result);
        }
    }
}

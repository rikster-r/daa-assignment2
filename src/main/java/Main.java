public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java Main <algorithm> <size>");
            System.out.println("Algorithms: insertion, selection");
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

        BenchmarkRunner.BenchmarkResult result = BenchmarkRunner.runBenchmark(algorithm, size);
        if (result != null) {
            System.out.println(result);
        }
    }
}

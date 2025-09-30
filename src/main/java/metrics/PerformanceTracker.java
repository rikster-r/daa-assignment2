package metrics;

import java.io.FileWriter;
import java.io.IOException;

public class PerformanceTracker {
    private long comparisons;
    private long swaps;
    private long arrayAccesses;
    private long memoryAllocations;
    private long startTime;
    private long endTime;

    public void reset() {
        comparisons = 0;
        swaps = 0;
        arrayAccesses = 0;
        memoryAllocations = 0;
        startTime = 0;
        endTime = 0;
    }

    public void start() {
        startTime = System.nanoTime();
    }

    public void stop() {
        endTime = System.nanoTime();
    }

    public void incrementComparisons() { comparisons++; }
    public void incrementSwaps() { swaps++; }
    public void incrementArrayAccesses(int count) { arrayAccesses += count; }
    public void incrementMemoryAllocations() { memoryAllocations++; }

    public long getComparisons() { return comparisons; }
    public long getSwaps() { return swaps; }
    public long getArrayAccesses() { return arrayAccesses; }
    public long getMemoryAllocations() { return memoryAllocations; }
    public long getExecutionTimeMillis() { return (endTime - startTime) / 1_000_000; }

    public void exportToCSV(String fileName, String algorithm, int inputSize) {
        boolean fileExists = new java.io.File(fileName).exists();

        try (FileWriter writer = new FileWriter(fileName, true)) {
            if (!fileExists) {
                writer.write("Algorithm,InputSize,Comparisons,Swaps,ArrayAccesses,MemoryAllocations,ExecutionTime(ms)\n");
            }

            writer.write(String.format("%s,%d,%d,%d,%d,%d,%d\n",
                    algorithm, inputSize, comparisons, swaps, arrayAccesses, memoryAllocations, getExecutionTimeMillis()));
        } catch (IOException e) {
            System.err.println("Error writing to CSV: " + e.getMessage());
        }
    }
}

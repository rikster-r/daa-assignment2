# Algorithm Benchmarking – Insertion & Selection Sort

## Overview
This project implements two basic quadratic sorting algorithms with performance tracking:
- **Insertion Sort** (optimized for nearly-sorted arrays)
- **Selection Sort** (with early termination placeholder)

A benchmarking runner generates random arrays, sorts them, and records performance metrics.

## Features
- Insertion Sort and Selection Sort implementations
- Performance tracking:
    - Comparisons
    - Swaps
    - Array accesses
    - Memory allocations
    - Execution time (ms)
- CSV export of benchmark results
- CLI interface for running benchmarks

## Usage

### Tests
```bash
mvn test
```

### Custom benchmark
```bash
java -cp target/classes Main <algorithm> <size>
```

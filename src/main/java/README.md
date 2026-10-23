# Lab: Multithreaded File Word Counter

## Overview

In this lab, you will use Java multithreading to count words in all `.txt` files in a directory **and its subdirectories**.

You are given:

- `singlethreaded/FileWordCounter.java` — a complete, single-threaded implementation to study.
- `multithreaded/MultithreadedFileWordCounter.java` — starter code with `TODO` sections to complete.

The single-threaded program processes files one at a time. Your implementation will use a thread pool to process multiple files concurrently. Several workers will update the **same shared word counter**, so synchronization is required.

## Start with the Single-Threaded Version

Before writing any code, **go through `singlethreaded/FileWordCounter.java` carefully**. Run it and make sure you understand how it recursively traverses directories, reads `.txt` files, and updates the total word count.

Your multithreaded implementation will perform the same work, but with separate tasks processing the files concurrently.

## Instructions

After reviewing the single-threaded version, complete the `TODO` sections in `MultithreadedFileWordCounter.java`.

1. **Create a thread pool.** Initialize the `ExecutorService` field using `Executors.newCachedThreadPool()`.

2. **Implement `CounterWorker.run()`.** Each worker processes one text file. Open it with `Files.newBufferedReader()` using try-with-resources, read it line by line, and count words using the same splitting logic as the single-threaded version. Accumulate the count in a **local variable**. After processing the file, call `updateCount(localWordCount)` once to add its result to the shared total.

3. **Protect shared data.** Implement synchronized methods `updateCount(int localCount)` and `getCount()`. Do not update the shared `count` field directly from `run()`.

4. **Traverse directories.** In `countWordsInFiles(Path directory)`, use `DirectoryStream<Path>` to examine directory entries. Recursively visit subdirectories. For each `.txt` file, create a `CounterWorker` and submit it to the pool with `execute()`. Do not create your own `Thread` objects.

5. **Wait for completion.** In `traverseAndGatherResults(Path dir)`, use try-with-resources with `poolManager`, and call `countWordsInFiles(dir)` inside the `try` block. Closing the executor automatically waits for all submitted tasks to finish. You do not need a list of futures or a separate shutdown method.

6. **Print the result.** In `main()`, process the supplied `dir` directory and print the total word count **after** all tasks have finished.

## Testing

1. Run `FileWordCounter` on the provided `dir` directory.
2. Run `MultithreadedFileWordCounter` on the same directory.
3. Verify that both programs print the **same word count**.
4. Run the multithreaded program several times; the count should remain the same.
5. Make sure text files in nested subdirectories are included and non-`.txt` files are ignored.


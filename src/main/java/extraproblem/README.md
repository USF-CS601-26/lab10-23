# Exercise: Parallel File Counting with Fork/Join

## Overview

In this exercise, you will use Java's **Fork/Join Framework** to count the total number of files in a directory and all its subdirectories.

The Fork/Join Framework allows you to split a large task into smaller subtasks that can run concurrently, improving performance when working with tasks that can be divided recursively.

You are provided with an incomplete Java program called `ForkJoinFileCountDemo`. Your goal is to complete the missing code inside the `compute()` method.

## Exercise Description

The program starts from a root directory:

```java
Path root = Paths.get("src/main");
```

It must count all regular files located in this directory, including files inside any nested subdirectories.

To accomplish this, the program uses a `FileCountTask` class that extends `RecursiveTask<Integer>`.

Each task is responsible for counting the files in one directory and delegating subdirectories to new tasks.

## Your Task

Complete the `compute()` method in the `FileCountTask` class.

Your implementation should follow these steps:

1. Iterate through all paths in the current directory using the provided `DirectoryStream<Path>`.

2. For each path:
   - If the path represents a regular file, increment the local file counter.
   - If the path represents a directory, create a new `FileCountTask` for that subdirectory.

3. Use `fork()` to execute each subdirectory task asynchronously.

4. Keep track of the forked tasks so that you can retrieve their results later.

5. After processing the directory entries, use `join()` to wait for the subtasks to finish and add their results to the local counter.

6. Return the total number of files found.

Do not count directories themselves. 

## Questions

After completing the exercise, consider the following questions:

1. What would happen if we called `join()` immediately after every `fork()`?
2. How does the Fork/Join Framework differ from a traditional recursive solution?
3. Does parallel execution always make directory traversal faster? Why or why not?

**(Optional Extension):** Extend the program to count both files and directories separately, returning the two totals.
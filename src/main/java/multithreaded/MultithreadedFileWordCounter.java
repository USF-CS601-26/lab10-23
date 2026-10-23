package multithreaded;

import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.*;

public class MultithreadedFileWordCounter {
    private int count;

    // TODO: Create a thread pool using Executors.newCachedThreadPool()
    private final ExecutorService poolManager = null;

    public MultithreadedFileWordCounter() {
        count = 0;
    }

    // Each worker counts words in one file
    class CounterWorker implements Runnable {
        private final Path file;

        public CounterWorker(Path file) {
            this.file = file;
        }

        @Override
        public void run() {
            int localWordCount = 0;

            // TODO: Read the file line by line and count words
            // Use Files.newBufferedReader() and try-with-resources
            // Split each non-blank line into words
            // Accumulate the word count in localWordCount

            // TODO: Update the shared total safely
            // Hint: call a synchronized method
        }
    }

    // TODO: Write a synchronized method updateCount
    // that adds the local word count to the shared count

    // TODO: Write a synchronized method getCount()
    // that returns the total word count


    /**
     * Traverse the directory recursively and submit
     * a task for each .txt file.
     */
    public void countWordsInFiles(Path directory) {
        try (DirectoryStream<Path> filesAndFolders =
                     Files.newDirectoryStream(directory)) {

            // TODO: Iterate over files and folders
            // If an entry is a directory, recursively
            // call countWordsInFiles() on that directory
            // If an entry is a .txt file, create a CounterWorker
            // and submit it to the thread pool using execute() or submit()

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Submit tasks and wait for all workers to finish.
     */
    public void traverseAndGatherResults(Path dir) {
        // TODO: Use try-with-resources with poolManager
        // Call countWordsInFiles(dir) inside the try block
        // Closing the executor waits for all tasks to finish
    }

    static void main(String[] args) {
        MultithreadedFileWordCounter counter =
                new MultithreadedFileWordCounter();

        counter.traverseAndGatherResults(Paths.get("dir"));

        // TODO: Print the total word count
    }
}
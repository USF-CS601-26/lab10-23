package solution;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.*;

public class MultithreadedFileWordCounter {
    private int count;
    private final ExecutorService poolManager =
            Executors.newCachedThreadPool();

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

            try (BufferedReader br = Files.newBufferedReader(file)) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (!line.isBlank()) {
                        String[] words = line.split("[,;!\\. ]+");
                        localWordCount += words.length;
                    }
                }
            } catch (IOException e) {
                System.out.println(e);
            }
            System.out.println(Thread.currentThread().getName());
            updateCount(localWordCount);
        }
    }

    // Safely update the shared total
    public synchronized void updateCount(int localCount) {
        count += localCount;
    }

    public synchronized int getCount() {
        return count;
    }

    // Traverse directories and submit tasks for .txt files
    public void countWordsInFiles(Path directory) {
        try (DirectoryStream<Path> filesAndFolders =
                     Files.newDirectoryStream(directory)) {

            for (Path path : filesAndFolders) {
                if (Files.isDirectory(path)) {
                    countWordsInFiles(path);
                } else if (path.toString().endsWith(".txt")) {
                    // Future future = poolManager.submit(new CounterWorker(path));
                    // can also use execute, since not using the future
                    poolManager.execute(new CounterWorker(path));
                }
            }

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    // Submit tasks and wait for all workers to finish
    public void traverseAndGatherResults(Path dir) {
        try (poolManager) {
            countWordsInFiles(dir);
        }
        // close() is called automatically and waits for all tasks
    }

    static void main(String[] args) {
        MultithreadedFileWordCounter counter =
                new MultithreadedFileWordCounter();

        counter.traverseAndGatherResults(Paths.get("dir"));
        System.out.println(counter.getCount());
    }
}
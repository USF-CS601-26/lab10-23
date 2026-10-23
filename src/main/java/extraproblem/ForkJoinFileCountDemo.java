package extraproblem;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.*;

public class ForkJoinFileCountDemo {

    private static class FileCountTask extends RecursiveTask<Integer> {
        private final Path dir;

        FileCountTask(Path dir) {
            this.dir = dir;
        }

        @Override
        protected Integer compute() {
            int count = 0;
            try(DirectoryStream<Path> paths = Files.newDirectoryStream(dir)) {
                // FILL IN CODE
                // For each path:
                //   - If it is a regular file, increment 'count'.
                //   - If it is a subdirectory, create a new FileCountTask for it and call fork() to run it asynchronously.

                // Join all forked subtasks and add their results to 'count'.
                // Return the total count for this directory.

            }
            catch(IOException e) {
                System.out.println(e);
            }

            return count;
        }
    }

    static void main(String[] args) {
        Path root = Paths.get("src/main");
        ForkJoinPool pool = new ForkJoinPool();
        int total = pool.invoke(new FileCountTask(root));
        System.out.println("Total files: " + total);
        pool.shutdown();
    }
}





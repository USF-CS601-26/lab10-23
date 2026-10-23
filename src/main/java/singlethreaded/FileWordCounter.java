package singlethreaded;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Single-threaded implementation that counts words in all .txt files
 * in a directory and its subdirectories.
 *
 * Review this implementation before completing
 * MultithreadedFileWordCounter.
 */
public class FileWordCounter {
    private int count;

    public FileWordCounter(){
        count = 0;
    }

    public void countWordsInFiles(Path directory) {
        try (DirectoryStream<Path> filesAndFolders = Files.newDirectoryStream(directory)) {
            for (Path path: filesAndFolders) {
                if (Files.isDirectory(path))
                    countWordsInFiles(path);
                else {
                    if (path.toString().endsWith(".txt")) {
                        // Reads the file, splits each line into words, and counts the words
                        try (BufferedReader br = Files.newBufferedReader(path.toAbsolutePath())) {
                            String line;
                            while ((line = br.readLine()) != null) {
                                if (!line.isBlank()) {
                                    String[] words = line.split("[,;!\\. ]+");
                                    count += words.length;
                                }
                            }
                        }
                        catch (IOException e) {
                            System.out.println(e);
                        }

                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public int traverseAndCount(Path directory) {
        countWordsInFiles(directory);
        return getCount();
    }

    public int getCount() {
        return count;
    }

    static void main(String[] args) {
        FileWordCounter fileCounter = new FileWordCounter();
        System.out.println(fileCounter.traverseAndCount(Paths.get("dir")));
    }

}
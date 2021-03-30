package hw6;

import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Read text from a text file, count how often each unique word appears.
 *
 * <p>Note that the definition of "word" is rather arbitrary
 * and won't make the linguists among you very happy.</p>
 */
public class Experiment {

  // Update this to suppress the output.
  public static boolean VERBOSE = true;
  // Update this to any other data file for benchmarking experiments.
  public static String DATA_FILE = "hotel_california.txt";

  /**
   * Execution starts here.
   * @param wordFrequencyData an implementation of hw6.Map.
   * @throws FileNotFoundException if the data file does not exist.
   */
  public static void run(Map<String, Integer> wordFrequencyData) throws FileNotFoundException {
    Path resourceDirectory = getPath();
    Scanner sc = new Scanner(resourceDirectory.toFile());

    int wordCount = 0;
    while (sc.hasNext()) {
      String word = sc.next();
      if (isWord(word)) {
        addWord(wordFrequencyData, word);
        wordCount++;
      }
    }

    if (VERBOSE) {
      String description = String.format("Processed %d words using %s",
          wordCount, wordFrequencyData.getClass().getName());
      System.out.println(description);
    }
  }

  private static Path getPath() {
    URL url = Thread.currentThread().getContextClassLoader().getResource("");
    String path = url.getPath().replace("%20", " ")
        .replace("classes", "resources");

    Path resourceDirectory = Paths.get(path, DATA_FILE);
    // TODO On Windows, use this:
    // Path resourceDirectory = Paths.get(path.substring(1), DATA_FILE);
    return resourceDirectory;
  }

  private static boolean isWord(String word) {
    // The regular expression splits strings on whitespace and
    //   non-word characters (anything except [a-zA-Z_0-9]). Far
    //   from perfect, but close enough for this simple program.
    // Skip "short" words, most of which just "dirty up" the statistics.
    return word.matches("[a-zA-Z0-9]+") && word.length() > 1;
  }

  // Add word and update frequency count.
  private static void addWord(Map<String, Integer> data, String word) {
    if (data.has(word)) {
      data.put(word, data.get(word) + 1);
    } else {
      data.insert(word, 1);
    }
  }
}

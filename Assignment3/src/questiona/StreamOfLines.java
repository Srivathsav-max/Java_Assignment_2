package questiona;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StreamOfLines {

    public static void main(String[] args) throws IOException {
        Pattern pattern = Pattern.compile("\\s+");

        // Get the absolute path before reading the file
        Path filePath = Paths.get("Chapter2Paragraph.txt");
       
        // count occurrences of each word in a Stream<String> sorted by word
        Map<String, Long> wordCounts =
                Files.lines(filePath)
                        .map(line -> line.replaceAll("(?!')\\p{P}", ""))
                        .flatMap(line -> pattern.splitAsStream(line))
                        .collect(Collectors.groupingBy(String::toLowerCase,
                                TreeMap::new, Collectors.counting()));

        // display the words grouped by starting letter
        wordCounts.entrySet()
                .stream()
                .collect(
                        Collectors.groupingBy(entry -> entry.getKey().charAt(0),
                                TreeMap::new, Collectors.toList()))
                .forEach((letter, wordList) ->
                {
                    System.out.printf("%n%C%n", letter);
                    wordList.stream().forEach(word -> System.out.printf(
                            "%13s: %d%n", word.getKey(), word.getValue()));
                });
    }
}

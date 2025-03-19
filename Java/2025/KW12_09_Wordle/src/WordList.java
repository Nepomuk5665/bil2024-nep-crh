import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WordList {
    public List<String> extractWordsToList(String filePath) {
        List<String> words = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] lineWords = line.toLowerCase().split("[\\s\\p{Punct}]+");
                words.addAll(Arrays.asList(lineWords));
            }
            words.removeAll(Arrays.asList("", null));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return words;
    }
}
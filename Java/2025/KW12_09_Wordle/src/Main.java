import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String filePath = "src/wordlist.txt";
        WordList wordList = new WordList();
        List<String> words = wordList.extractWordsToList(filePath);

        if (words != null) {
            System.out.println("Extracted Words:");
            for (String word : words) {
                System.out.println(word);
            }
            System.out.println("Total word count: " + words.size());
        } else {
            System.out.println("Error extracting words.");
        }
        
        GameManager gameManager = new GameManager(words);
        gameManager.playGame();
    }
}
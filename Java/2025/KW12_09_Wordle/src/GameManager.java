import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameManager {
    private List<String> wordList;
    private String randomWord;
    private int allowedGuesses = 5;

    public GameManager(List<String> wordList) {
        this.wordList = wordList;
        int randomIndex = (int) (Math.random() * wordList.size());
        System.out.println("Random index: "+ randomIndex);
        System.out.println("Random word: " + wordList.get(randomIndex));
        this.randomWord = wordList.get(randomIndex);
    }

    public void playGame() {
        int randomWordLength = randomWord.length();
        List<Character> charList = new ArrayList<>();
        for (int i = 0; i < randomWordLength; i++) {
            charList.add(randomWord.charAt(i));
        }

        for (int i = 0; i < allowedGuesses; i++) {
            System.out.println((i+1) +". guess?");
            Scanner scanner = new Scanner(System.in);
            String guess = scanner.next();
            if (guess.length() != randomWordLength) {
                System.out.println("the Guess: "+guess+ "doesn't match the random word length. Random word length: " + randomWordLength);
            } else {
                if (randomWord.equals(guess)) {
                    System.out.println("Correct!");
                    return;
                } else {
                    for (int j = 0; j < randomWordLength; j++) {
                        if (charList.get(j) == guess.charAt(j)) {
                            System.out.print(ColorUtils.ANSI_GREEN + guess.charAt(j) + ColorUtils.ANSI_RESET);
                        } else if (randomWord.contains(String.valueOf(guess.charAt(j)))) {
                            System.out.print(ColorUtils.ANSI_YELLOW + guess.charAt(j) + ColorUtils.ANSI_RESET);
                        } else {
                            System.out.print(ColorUtils.ANSI_GRAY + guess.charAt(j) + ColorUtils.ANSI_RESET);
                        }
                    }
                }
            }
            System.out.println();
        }
    }
}
import java.lang.constant.Constable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GameManager {
    private final List<String> wordList;
    private String targetWord;
    private static final int MAX_GUESSES = 5;
    private final Scanner scanner;
    private final WordList wordListManager;

    public GameManager(List<String> wordList, WordList wordListManager) {
        this.wordList = wordList;
        this.wordListManager = wordListManager;
        this.scanner = new Scanner(System.in);
    }
    
    private String selectRandomWord() {
        if (wordListManager.isUsingApi()) {
            return wordListManager.getRandomWordFromApi();
        } else {
            int randomIndex = (int) (Math.random() * wordList.size());
            return wordList.get(randomIndex);
        }
    }
    
    public void showMainMenu() {
        boolean exitGame = false;
        
        while (!exitGame) {
            System.out.println("\n=== WORDLE SPIEL ===");
            System.out.println("1. Spiel starten");
            System.out.println("2. Spielregeln");
            System.out.println("3. Beenden");
            System.out.print("Wähle eine Option (1-3): ");
            
            String choice = scanner.next();
            
            switch (choice) {
                case "1": playGame(); break;
                case "2": showRules(); break;
                case "3":
                    exitGame = true;
                    System.out.println("Auf Wiedersehen!");
                    break;
                default:
                    System.out.println("Ungültige Eingabe.");
            }
        }
    }
    
    private void showRules() {
        System.out.println("\n=== SPIELREGELN ===");
        System.out.println("- Errate das Wort in " + MAX_GUESSES + " Versuchen");
        System.out.println("- " + ColorUtils.ANSI_GREEN + "GRÜN" + ColorUtils.ANSI_RESET + " = richtiger Buchstabe, richtige Position");
        System.out.println("- " + ColorUtils.ANSI_YELLOW + "GELB" + ColorUtils.ANSI_RESET + " = richtiger Buchstabe, falsche Position");
        System.out.println("- " + ColorUtils.ANSI_GRAY + "GRAU" + ColorUtils.ANSI_RESET + " = falscher Buchstabe");
        System.out.println("\nEnter drücken...");
        scanner.nextLine();
        scanner.nextLine();
    }

    public void playGame() {
        this.targetWord = selectRandomWord();
        System.out.println("\n=== NEUES SPIEL ===");
        System.out.println("Rate das Wort mit " + targetWord.length() + " Buchstaben");
        
        int wordLength = targetWord.length();
        List<Character> targetChars = convertWordToCharList(targetWord);

        for (int attempt = 0; attempt < MAX_GUESSES; attempt++) {
            System.out.println("\nVersuch " + (attempt + 1) + "/" + MAX_GUESSES + ":");
            String guess = scanner.next().toLowerCase();
            
            if (guess.length() != wordLength) {
                System.out.println("Falsche Länge! Wortlänge: " + wordLength);
                attempt--;
                continue;
            }
            
            if (targetWord.equals(guess)) {
                System.out.println("Richtig! Das Wort war: " + targetWord);
                return;
            }
            
            displayColoredFeedback(guess, targetChars);
            System.out.println();
        }
        
        System.out.println("\nVerloren! Das Wort war: " + targetWord);
    }
    
    private List<Character> convertWordToCharList(String word) {
        List<Character> charList = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            charList.add(word.charAt(i));
        }
        return charList;
    }
    
    private void displayColoredFeedback(String guess, List<Character> targetChars) {
        Map<Character, Integer> charCount = new HashMap<>();
        for (char c : targetWord.toCharArray()) {
            charCount.put(c, charCount.getOrDefault(c, 0) + 1);
        }
        
        boolean[] matched = new boolean[guess.length()];
        
        for (int i = 0; i < guess.length(); i++) {
            char c = guess.charAt(i);
            if (c == targetChars.get(i)) {
                matched[i] = true;
                charCount.put(c, charCount.get(c) - 1);
            }
        }

        for (int i = 0; i < guess.length(); i++) {
            char c = guess.charAt(i);
            
            if (matched[i]) {
                System.out.print(ColorUtils.ANSI_GREEN + c + ColorUtils.ANSI_RESET);
            } else if (charCount.getOrDefault(c, 0) > 0) {
                System.out.print(ColorUtils.ANSI_YELLOW + c + ColorUtils.ANSI_RESET);
                charCount.put(c, charCount.get(c) - 1);
            } else {
                System.out.print(ColorUtils.ANSI_GRAY + c + ColorUtils.ANSI_RESET);
            }
        }
    }
}
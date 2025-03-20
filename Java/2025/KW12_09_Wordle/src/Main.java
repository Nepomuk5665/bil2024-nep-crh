import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String WORD_LIST_PATH = "src/wordlist.txt";
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        WordList wordList = new WordList();
        
        System.out.println("Woher sollen die Wörter kommen?");
        System.out.println("1: Online API (5-Buchstaben Wörter)");
        System.out.println("2: Lokale Datei");
        System.out.print("Wähle (1 oder 2): ");
        
        String choice = scanner.nextLine();
        
        if (choice.equals("1")) {
            wordList.setUseApi(true);
            System.out.println("API wird verwendet für zufällige Wörter");
            GameManager gameManager = new GameManager(null, wordList);
            gameManager.showMainMenu();
        } else {
            List<String> words = wordList.extractWordsToList(WORD_LIST_PATH);
            
            if (words != null) {
                System.out.println("Wortliste geladen: " + words.size() + " Wörter verfügbar");
                GameManager gameManager = new GameManager(words, wordList);
                gameManager.showMainMenu();
            } else {
                System.out.println("Fehler beim Laden der Wortliste.");
            }
        }
    }
}
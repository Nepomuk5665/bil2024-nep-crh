import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Textfile Analyzer");
        System.out.println("-----------------");
        
        TextAnalyzer analyzer = new TextAnalyzer();
        analyzer.analyze();
    }
}

class TextAnalyzer {
    private Scanner scanner = new Scanner(System.in);
    
    public void analyze() {
        System.out.print("Enter the path to the text file: ");
        String filePath = scanner.nextLine();
        
        try {
            String fileContent = readFile(filePath);
            HashMap<String, Integer> wordCounts = countWords(fileContent);
            
            int uniqueWords = wordCounts.size();
            int totalWords = 0;
            String mostCommonWord = "";
            int maxCount = 0;
            
            for (Map.Entry<String, Integer> entry : wordCounts.entrySet()) {
                totalWords += entry.getValue();
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostCommonWord = entry.getKey();
                }
            }
            
            printResults(uniqueWords, totalWords, mostCommonWord, wordCounts);
            writeToFile(filePath, uniqueWords, totalWords, mostCommonWord, wordCounts);
            
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    private String readFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }
    
    private HashMap<String, Integer> countWords(String text) {
        HashMap<String, Integer> wordCounts = new HashMap<>();
        String[] words = text.toLowerCase().replaceAll("[^a-zA-Z0-9\\s]", " ").split("\\s+");
        
        for (String word : words) {
            if (!word.isEmpty()) {
                wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
            }
        }
        return wordCounts;
    }
    
    private void printResults(int uniqueWords, int totalWords, String mostCommonWord, HashMap<String, Integer> wordCounts) {
        System.out.println("\nAnalysis Results:");
        System.out.println("-----------------");
        System.out.println("Number of unique words:\t" + uniqueWords);
        System.out.println("Total number of words:\t" + totalWords);
        System.out.println("Most common word:\t" + mostCommonWord);
        
        System.out.println("\nTop 10 words by frequency:");
        wordCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(10)
                .forEach(entry -> System.out.println(entry.getKey() + "\t" + entry.getValue()));
    }
    
    private void writeToFile(String inputFilePath, int uniqueWords, int totalWords, String mostCommonWord, HashMap<String, Integer> wordCounts) throws IOException {
        Path path = Paths.get(inputFilePath);
        String fileName = path.getFileName().toString();
        String directory = path.getParent().toString();
        String outputFileName = fileName.substring(0, fileName.lastIndexOf('.')) + "_evaluation.txt";
        String outputFilePath = Paths.get(directory, outputFileName).toString();
        
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd / HH:mm:ss");
        String dateTime = now.format(formatter);
        
        HashMap<String, Integer> sortedWordCounts = new LinkedHashMap<>();
        wordCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(entry -> sortedWordCounts.put(entry.getKey(), entry.getValue()));
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFilePath))) {
            writer.println(dateTime + " " + fileName);
            writer.println("--------------------------------------------");
            writer.println("Number of unique words:\t" + uniqueWords);
            writer.println("Total number of words:\t" + totalWords);
            writer.println("Most common word:\t" + mostCommonWord);
            writer.println("--------------------------------------------");
            
            for (Map.Entry<String, Integer> entry : sortedWordCounts.entrySet()) {
                writer.println(entry.getKey() + "\t" + entry.getValue());
            }
        }
        
        System.out.println("\nEvaluation file created: " + outputFilePath);
    }
}
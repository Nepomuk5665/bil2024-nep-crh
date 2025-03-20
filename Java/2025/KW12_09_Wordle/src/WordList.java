import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class WordList {
    private static final String EMPTY_STRING = "";
    private static final String WORD_DELIMITER_REGEX = "[\\s\\p{Punct}]+";
    private static final String API_URL = "https://random-word-api.vercel.app/api?words=1&length=5";
    
    private boolean useApi = false;
    
    public void setUseApi(boolean useApi) {
        this.useApi = useApi;
    }
    
    public boolean isUsingApi() {
        return useApi;
    }
    
    public String getRandomWordFromApi() {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String response = in.readLine();
                in.close();
                
                // Die API gibt etwas zurück wie ["word"]
                if (response != null && response.length() > 4) {
                    return response.substring(2, response.length() - 2);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Fallback wenn die API nicht funktioniert
        return "apple";
    }
    
    public List<String> extractWordsToList(String filePath) {
        List<String> words = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            processFileLines(reader, words);
            removeEmptyEntries(words);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        
        return words;
    }
    
    private void processFileLines(BufferedReader reader, List<String> wordList) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] lineWords = line.toLowerCase().split(WORD_DELIMITER_REGEX);
            wordList.addAll(Arrays.asList(lineWords));
        }
    }
    
    private void removeEmptyEntries(List<String> wordList) {
        wordList.removeAll(Arrays.asList(EMPTY_STRING, null));
    }
}
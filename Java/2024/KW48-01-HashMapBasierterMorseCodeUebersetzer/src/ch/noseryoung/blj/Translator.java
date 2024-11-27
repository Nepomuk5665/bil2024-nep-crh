package ch.noseryoung.blj;

import java.util.Arrays;
import java.util.HashMap;

public class Translator {
    static HashMap<String, String> MorseCode = new HashMap<>();
    static{
        // a - z Morse code
        MorseCode.put("a", ".-");
        MorseCode.put("b", "-...");
        MorseCode.put("c", "-.-.");
        MorseCode.put("d", "-..");
        MorseCode.put("e", ".");
        MorseCode.put("f", "..-.");
        MorseCode.put("g", "--.");
        MorseCode.put("h", "....");
        MorseCode.put("i", "..");
        MorseCode.put("j", ".---");
        MorseCode.put("k", "-.-");
        MorseCode.put("l", ".-..");
        MorseCode.put("m", "--");
        MorseCode.put("n", "-.");
        MorseCode.put("o", "---");
        MorseCode.put("p", ".--.");
        MorseCode.put("q", "--.-");
        MorseCode.put("r", ".-.");
        MorseCode.put("s", "...");
        MorseCode.put("t", "-");
        MorseCode.put("u", "..-");
        MorseCode.put("v", "...-");
        MorseCode.put("w", ".--");
        MorseCode.put("x", "-..-");
        MorseCode.put("y", "-.--");
        MorseCode.put("z", "--..");

        // 0 - 9 Morse code
        MorseCode.put("0", "-----");
        MorseCode.put("1", ".----");
        MorseCode.put("2", "..---");
        MorseCode.put("3", "...--");
        MorseCode.put("4", "....-");
        MorseCode.put("5", ".....");
        MorseCode.put("6", "-....");
        MorseCode.put("7", "--...");
        MorseCode.put("8", "---..");
        MorseCode.put("9", "----.");

        //Sonderzeichen Morse Code
        MorseCode.put("?", "..--..");
        MorseCode.put("!", "-.-.--");
        MorseCode.put(".", ".-.-.-");
        MorseCode.put(",", "--..--");
        MorseCode.put(";", "-.-.-.");
        MorseCode.put(":", "---...");
        MorseCode.put("+", ".-.-.");
        MorseCode.put("-", "-....-");
        MorseCode.put("/", "-..-.");
        MorseCode.put("=", "-...-");
    }
    public Translator(){

    }

    String translateToMorse(String input) {


        input = input.toLowerCase();

        StringBuilder morseResult = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            String character = String.valueOf(input.charAt(i)); // Get each character
            if (MorseCode.containsKey(character)) {
                morseResult.append(MorseCode.get(character)).append(" "); // Mit abstand
            } else if (character.equals(" ")) {
                morseResult.append(" / "); // abstand
            }else{
                System.out.println("Character: " + character + " not found");
            }
        }
        return morseResult.toString();

    }

    String translateFromMorse(String input) {
        input = input.toLowerCase();

        StringBuilder Result = new StringBuilder();
        String[] words = input.split(" / "); // Split into words

        for (String word : words) {
            String[] letters = word.trim().split(" "); // letters
            for (String letter : letters) {
                for (HashMap.Entry<String, String> entry : MorseCode.entrySet()) {
                    if (entry.getValue().equals(letter)) {
                        Result.append(entry.getKey());
                        break;
                    }
                }
            }
            Result.append(" "); // space
        }

        return Result.toString().trim(); // remove whitespace both ends
    }
}

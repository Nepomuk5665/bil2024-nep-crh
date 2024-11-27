package ch.noseryoung.blj;
import java.util.Scanner;

public class CLIInput {

    public void StartCLIInput() {
        int exit = 1;
        while (exit != 0) {
            Translator translator = new Translator();
            Scanner in = new Scanner(System.in);
            System.out.println("Was willst du machen?\n1. From Text To Morse\n2. From Morse To Text\n3. Exit");
            int input = in.nextInt();
            in.nextLine(); // Consume the newline
            String result = null;
            String text;
            switch (input) {
                case 1:
                    System.out.print("Text Welcher zu Morse umgewandelt werden soll: ");
                    text = in.nextLine();
                    result = translator.translateToMorse(text);
                    break;
                case 2:
                    System.out.print("Morse Welcher zu Text umgewandelt werden soll: ");
                    text = in.nextLine();
                    result = translator.translateFromMorse(text);
                    break;
                case 3:
                    exit = 0;
                    continue;
                default:
                    throw new IllegalStateException("Unexpected value: " + input);
            }
            System.out.println("Resultat: \n" + result);
        }
    }


}

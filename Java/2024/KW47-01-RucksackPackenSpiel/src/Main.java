import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean gameEnd = false;


            ArrayList<String> items = new ArrayList<>();

            Scanner input = new Scanner(System.in);

            System.out.println("Welcome to the game!");



        while(!gameEnd) {
            for(int i = 0; i < items.size(); i++) {
                System.out.println("Was wurde von Index " + i + " hinzugefügt ?");

                String baum = input.nextLine();

                if (items.get(i).equals(baum)) {
                    System.out.println("Richtig!");
                }
                else {
                    System.out.println("Falsch!\nHiermit endet das Spiel");
                    gameEnd = true;
                    break;
                }
            }
            boolean alreadyAdded = true;
            if(!gameEnd) {
                while(alreadyAdded) {
                    System.out.println("Was willst du einpacken?");
                    String add = input.nextLine();
                    if (items.contains(add)){
                        System.out.println("Wurde schon hinzugefüt mach was anderes.");
                    }else{
                        items.add(add);
                        alreadyAdded = false;
                    }

                }
            }


        }
        System.out.println("\nGoodbye!");
    }
}
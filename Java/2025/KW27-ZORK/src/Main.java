import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Boolean gameOn = true;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello and welcome to ZORK!");


        ArrayList<Item> items = new ArrayList<Item>();
        items.add(new Item("Monkey", "Ich bin ein cute monkey monk monk"));
        items.add(new Item("Book", "Das buch der Fische"));
        items.add(new Item("Chicken", "LASS MICH LOOOSSSSSS"));
        items.add(new Item("Fish", "Flip Flup flabedi flip"));

        String hand;

        while (gameOn) {
            System.out.print("> ");
            String input = scanner.nextLine().toLowerCase();

            if (input.startsWith("take")) {
                boolean found = false;
                for (Item item : items) {
                    if (input.contains(item.name.toLowerCase())) {
                        hand = item.name;
                        System.out.println("Du hast jetzt " + hand + " in der Hand");
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("Das Item existiert nicht.");
                }

            } else if (input.startsWith("read")) {
                if (hand != null) {
                    for (Item item : items) {
                        if (item.name.equalsIgnoreCase(hand)) {
                            System.out.println(item.description);
                        }
                    }
                } else {
                    System.out.println("Du hast nichts in der Hand.");
                }

            } else if (input.contains("quit")) {
                gameOn = false;
            } else {
                System.out.println("Invalid input");
            }
        }
    }
}

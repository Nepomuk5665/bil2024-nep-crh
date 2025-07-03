import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Boolean gameOn = true;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello and welcome to ZORK!");



        ArrayList<Item> items = new ArrayList<Item>();
        items.add(new Item("Monkey", "Ich bin ein cute monkey monk monk mit gutem Finger", false, 1));
        items.add(new Item("Book", "Das buch der Fische", false, 1));
        items.add(new Item("Chicken", "LASS MICH LOOOSSSSSS", false, 1));
        items.add(new Item("Fish", "Flip Flup flabedi flip", false, 1));

        ArrayList<String> room = new ArrayList<>();
        room.add("Du bist in eimen Dunklen Raum mit einem Monkey einem Buch ein Fisch und ein Chicken. Da ist eine Türe mit einem Pin und Fingerabdruck sensor.");



        int roomNumber = 1;

        ArrayList<Item> itemsRoomNow = new ArrayList<Item>();

        String hand = null;
        Boolean newRoom = true;

        while (gameOn) {
            if (newRoom) {
                System.out.println("Welcome to Room number " + roomNumber + "!");
                System.out.println(room.get(roomNumber - 1));

                for (Item item : items) {
                    if (item.roomNumber == roomNumber) {
                        itemsRoomNow.add(item);
                    }
                }

                newRoom = false;
            }
            if (roomNumber == 1) {
                Boolean found = false;
                System.out.print("> ");
                String input = scanner.nextLine().toLowerCase();

                if (input.startsWith("take")) {
                    for (Item item : itemsRoomNow) {
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
                        for (Item item : itemsRoomNow) {
                            if (item.name.equalsIgnoreCase(hand) && item.canbeopened == false) {
                                System.out.println(item.description);
                                found = true;
                            }
                        }
                    } else {
                        System.out.println("Du hast nichts in der Hand.");
                        found = true;
                    }
                    if (!found) {
                        System.out.println("Dieser Gegenstand kann nicht gelesen werden.");
                    }
                } else if (input.startsWith("open")) {
                    if (hand != null) {
                        for (Item item : itemsRoomNow) {
                            if (item.name.equalsIgnoreCase(hand) && item.canbeopened == true) {
                                found = true;
                                System.out.println(item.description);
                            }
                        }
                    } else {
                        System.out.println("Du hast nichts in der Hand.");
                        found = true;
                    }
                    if (!found) {
                        System.out.println("Dieser Gegenstand kann nicht geöffnet werden.");
                    }
                } else if (input.contains("quit")) {
                    gameOn = false;
                } else {
                    System.out.println("Invalid input");
                }
            }
        }
    }
}

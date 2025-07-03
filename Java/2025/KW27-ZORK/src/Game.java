import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private ArrayList<Room> rooms;
    private ArrayList<Item> allItems;
    private Room currentRoom;
    private String itemInHand;
    private Scanner scanner;
    private boolean gameOn;

    public Game() {
        this.rooms = new ArrayList<>();
        this.allItems = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        this.gameOn = true;
        this.itemInHand = null;
        initializeGame();
    }

    private void initializeGame() {
        Room room1 = new Room(1, "Du bist in eimen Dunklen Raum mit einem Monkey einem Buch ein Fisch und ein Chicken. Da ist eine Türe mit einem Pin und Fingerabdruck sensor.");
        rooms.add(room1);

        Item monkey = new Item("Monkey", "Ich bin ein cute monkey monk monk mit gutem Finger", false, 1);
        Item book = new Item("Book", "Das buch der Fische", false, 1);
        Item chicken = new Item("Chicken", "LASS MICH LOOOSSSSSS", false, 1);
        Item fish = new Item("Fish", "Flip Flup flabedi flip", false, 1);

        allItems.add(monkey);
        allItems.add(book);
        allItems.add(chicken);
        allItems.add(fish);

        for (Item item : allItems) {
            if (item.roomNumber == 1) {
                room1.addItem(item);
            }
        }

        currentRoom = room1;
    }

    public void start() {
        System.out.println("Hello and welcome to ZORK!");
        currentRoom.displayRoom();

        while (gameOn) {
            System.out.print("> ");
            String input = scanner.nextLine().toLowerCase();
            processCommand(input);
        }
    }

    private void processCommand(String input) {
        if (input.startsWith("take")) {
            handleTake(input);
        } else if (input.startsWith("read")) {
            handleRead();
        } else if (input.startsWith("open")) {
            handleOpen();
        } else if (input.contains("quit")) {
            gameOn = false;
        } else {
            System.out.println("Invalid input");
        }
    }

    private void handleTake(String input) {
        boolean found = false;
        for (Item item : currentRoom.getItems()) {
            if (input.contains(item.name.toLowerCase())) {
                itemInHand = item.name;
                System.out.println("Du hast jetzt " + itemInHand + " in der Hand");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Das Item existiert nicht.");
        }
    }

    private void handleRead() {
        if (itemInHand != null) {
            boolean found = false;
            for (Item item : currentRoom.getItems()) {
                if (item.name.equalsIgnoreCase(itemInHand) && !item.canbeopened) {
                    System.out.println(item.description);
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Dieser Gegenstand kann nicht gelesen werden.");
            }
        } else {
            System.out.println("Du hast nichts in der Hand.");
        }
    }

    private void handleOpen() {
        if (itemInHand != null) {
            boolean found = false;
            for (Item item : currentRoom.getItems()) {
                if (item.name.equalsIgnoreCase(itemInHand) && item.canbeopened) {
                    System.out.println(item.description);
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Dieser Gegenstand kann nicht geöffnet werden.");
            }
        } else {
            System.out.println("Du hast nichts in der Hand.");
        }
    }
}
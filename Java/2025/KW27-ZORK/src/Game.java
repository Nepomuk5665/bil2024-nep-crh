import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Game {
    private HashMap<Integer, Room> rooms;
    private Room currentRoom;
    private ArrayList<Item> inventory;
    private String itemInHand;
    private Scanner scanner;
    private boolean gameOn;

    public Game() {
        this.rooms = new HashMap<>();
        this.inventory = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        this.gameOn = true;
        this.itemInHand = null;
        initializeGame();
    }

    private void initializeGame() {
        Room startRoom = new Room(1, "Dunkler Raum", "Du bist in einem dunklen Raum. Es riecht modrig und feucht.");
        Room hallway = new Room(2, "Flur", "Ein langer, schmaler Flur mit alten Bildern an den Wänden.");
        Room library = new Room(3, "Bibliothek", "Eine staubige Bibliothek voller alter Bücher.");
        Room kitchen = new Room(4, "Küche", "Eine verlassene Küche. Etwas scheint hier zu fehlen.");
        Room secretRoom = new Room(5, "Geheimraum", "Ein versteckter Raum mit mysteriösen Artefakten.");
        Room exitRoom = new Room(6, "Ausgang", "Du siehst Tageslicht! Der Ausgang ist nah!");

        rooms.put(1, startRoom);
        rooms.put(2, hallway);
        rooms.put(3, library);
        rooms.put(4, kitchen);
        rooms.put(5, secretRoom);
        rooms.put(6, exitRoom);

        Item monkey = new Item("Monkey", "Ich bin ein cute monkey monk monk mit gutem Finger", false, 1);
        Item book = new Item("Book", "Das Buch der Fische - Seite 42: Der Schlüssel liegt im Wissen", false, 1);
        Item chicken = new Item("Chicken", "LASS MICH LOOOSSSSSS", false, 1);
        Item fish = new Item("Fish", "Flip Flup flabedi flip", false, 1);
        Item key = new Item("Schlüssel", "Ein rostiger alter Schlüssel", false, 3);
        Item goldKey = new Item("Goldschlüssel", "Ein glänzender goldener Schlüssel", false, 5);
        Item knife = new Item("Messer", "Ein scharfes Küchenmesser", false, 4);

        startRoom.addItem(monkey);
        startRoom.addItem(book);
        startRoom.addItem(chicken);
        startRoom.addItem(fish);
        library.addItem(key);
        kitchen.addItem(knife);
        secretRoom.addItem(goldKey);

        Door doorToHallway = new Door("Holztür", "Eine alte Holztür", false, null);
        doorToHallway.connectRooms(startRoom, hallway);
        startRoom.addDoor("nord", doorToHallway);
        hallway.addDoor("süd", doorToHallway);

        Door doorToLibrary = new Door("Büchertür", "Eine Tür mit Buchverzierungen", true, "Monkey");
        doorToLibrary.connectRooms(hallway, library);
        hallway.addDoor("west", doorToLibrary);
        library.addDoor("ost", doorToLibrary);

        Door doorToKitchen = new Door("Küchentür", "Eine Schwingtür zur Küche", false, null);
        doorToKitchen.connectRooms(hallway, kitchen);
        hallway.addDoor("ost", doorToKitchen);
        kitchen.addDoor("west", doorToKitchen);

        Door doorToSecret = new Door("Geheimtür", "Eine versteckte Tür hinter dem Bücherregal", true, "Schlüssel");
        doorToSecret.connectRooms(library, secretRoom);
        library.addDoor("nord", doorToSecret);
        secretRoom.addDoor("süd", doorToSecret);

        Door doorToExit = new Door("Ausgangstür", "Die Tür zur Freiheit", true, "Goldschlüssel");
        doorToExit.connectRooms(hallway, exitRoom);
        hallway.addDoor("nord", doorToExit);
        exitRoom.addDoor("süd", doorToExit);

        currentRoom = startRoom;
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
        if (input.startsWith("take") || input.startsWith("nimm")) {
            handleTake(input);
        } else if (input.startsWith("read") || input.startsWith("lies")) {
            handleRead();
        } else if (input.startsWith("open") || input.startsWith("öffne")) {
            handleOpen();
        } else if (input.startsWith("go") || input.startsWith("gehe")) {
            handleGo(input);
        } else if (input.startsWith("use") || input.startsWith("benutze")) {
            handleUse(input);
        } else if (input.equals("inventory") || input.equals("inv") || input.equals("i")) {
            showInventory();
        } else if (input.equals("look") || input.equals("l") || input.equals("schau")) {
            currentRoom.displayRoom();
        } else if (input.equals("help") || input.equals("hilfe") || input.equals("?")) {
            showHelp();
        } else if (input.contains("quit") || input.contains("exit")) {
            gameOn = false;
        } else {
            System.out.println("Ich verstehe nicht. Tippe 'help' für Hilfe.");
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

    private void handleGo(String input) {
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            System.out.println("Wohin möchtest du gehen? (nord, süd, ost, west)");
            return;
        }

        String direction = parts[1];
        Door door = currentRoom.getDoor(direction);

        if (door == null) {
            System.out.println("In diese Richtung gibt es keinen Ausgang.");
            return;
        }

        if (door.canPass(itemInHand)) {
            Room nextRoom = door.getOtherRoom(currentRoom);
            if (nextRoom != null) {
                currentRoom = nextRoom;
                currentRoom.displayRoom();
                if (currentRoom.getRoomNumber() == 6) {
                    System.out.println("\n*** GLÜCKWUNSCH! Du hast das Spiel gewonnen! ***");
                    gameOn = false;
                }
            }
        } else {
            System.out.println("Die " + door.getName() + " ist verschlossen. Du brauchst: " + door.getRequiredItem());
        }
    }

    private void handleUse(String input) {
        String[] parts = input.split(" ");
        if (parts.length < 3 || !parts[1].equals("on")) {
            System.out.println("Benutze was womit? (use ITEM on DOOR)");
            return;
        }

        String direction = parts[2];
        Door door = currentRoom.getDoor(direction);

        if (door == null) {
            System.out.println("Es gibt keine Tür in diese Richtung.");
            return;
        }

        if (itemInHand == null) {
            System.out.println("Du hast nichts in der Hand.");
            return;
        }

        if (door.canPass(itemInHand)) {
            System.out.println("Die " + door.getName() + " ist jetzt offen!");
        } else {
            System.out.println("Das funktioniert nicht mit diesem Gegenstand.");
        }
    }

    private void showInventory() {
        if (inventory.isEmpty() && itemInHand == null) {
            System.out.println("Du trägst nichts bei dir.");
        } else {
            System.out.println("Du trägst:");
            if (itemInHand != null) {
                System.out.println("In der Hand: " + itemInHand);
            }
            for (Item item : inventory) {
                System.out.println("- " + item.name);
            }
        }
    }

    private void showHelp() {
        System.out.println("\nVerfügbare Befehle:");
        System.out.println("- take/nimm [ITEM]: Nimm einen Gegenstand");
        System.out.println("- read/lies: Lies den Gegenstand in deiner Hand");
        System.out.println("- open/öffne: Öffne den Gegenstand in deiner Hand");
        System.out.println("- go/gehe [RICHTUNG]: Gehe in eine Richtung (nord, süd, ost, west)");
        System.out.println("- use/benutze [ITEM] on [RICHTUNG]: Benutze Item an einer Tür");
        System.out.println("- inventory/inv/i: Zeige dein Inventar");
        System.out.println("- look/l/schau: Schaue dich um");
        System.out.println("- help/hilfe/?: Zeige diese Hilfe");
        System.out.println("- quit/exit: Beende das Spiel");
    }
}
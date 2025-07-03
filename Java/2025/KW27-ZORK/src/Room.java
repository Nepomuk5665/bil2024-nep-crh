import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Room {
    private int roomNumber;
    private String name;
    private String description;
    private ArrayList<Item> items;
    private HashMap<String, Door> doors;

    public Room(int roomNumber, String name, String description) {
        this.roomNumber = roomNumber;
        this.name = name;
        this.description = description;
        this.items = new ArrayList<>();
        this.doors = new HashMap<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public void addDoor(String direction, Door door) {
        doors.put(direction.toLowerCase(), door);
    }

    public Door getDoor(String direction) {
        return doors.get(direction.toLowerCase());
    }

    public HashMap<String, Door> getDoors() {
        return doors;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void displayRoom() {
        System.out.println("\n=== " + name + " ===");
        System.out.println(description);
        
        if (!items.isEmpty()) {
            System.out.println("\nGegenstände im Raum:");
            for (Item item : items) {
                System.out.println("- " + item.name);
            }
        }
        
        if (!doors.isEmpty()) {
            System.out.println("\nAusgänge:");
            for (String direction : doors.keySet()) {
                Door door = doors.get(direction);
                System.out.println("- " + direction + ": " + door.getName() + 
                    (door.isLocked() ? " (verschlossen)" : ""));
            }
        } else {
            System.out.println("Da war ein Problem mit den Türen bitte Spiel neu laden.");
        }
    }
}
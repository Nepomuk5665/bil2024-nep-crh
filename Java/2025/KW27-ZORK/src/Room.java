import java.util.ArrayList;

public class Room {
    private int roomNumber;
    private String description;
    private ArrayList<Item> items;

    public Room(int roomNumber, String description) {
        this.roomNumber = roomNumber;
        this.description = description;
        this.items = new ArrayList<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getDescription() {
        return description;
    }

    public void displayRoom() {
        System.out.println("Welcome to Room number " + roomNumber + "!");
        System.out.println(description);
    }
}
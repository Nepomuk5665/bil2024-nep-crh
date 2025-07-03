public class Door {
    private String name;
    private String description;
    private boolean isLocked;
    private String requiredItem;
    private Room roomA;
    private Room roomB;

    public Door(String name, String description, boolean isLocked, String requiredItem) {
        this.name = name;
        this.description = description;
        this.isLocked = isLocked;
        this.requiredItem = requiredItem;
    }

    public void connectRooms(Room roomA, Room roomB) {
        this.roomA = roomA;
        this.roomB = roomB;
    }

    public Room getOtherRoom(Room currentRoom) {
        if (currentRoom == roomA) {
            return roomB;
        } else if (currentRoom == roomB) {
            return roomA;
        }
        return null;
    }

    public boolean canPass(String itemInHand) {
        if (!isLocked) {
            return true;
        }
        if (requiredItem != null && requiredItem.equalsIgnoreCase(itemInHand)) {
            isLocked = false;
            return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public String getRequiredItem() {
        return requiredItem;
    }
}
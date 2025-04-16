public class Car extends Vehicle {
    private String category;
    private int doors;
    private int seats;
    private boolean isConvertible;
    
    public Car(String name, String category, int doors, int seats, boolean isConvertible) {
        super(name, 18);
        this.category = category;
        this.doors = doors;
        this.seats = seats;
        this.isConvertible = isConvertible;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public int getDoors() {
        return doors;
    }
    
    public void setDoors(int doors) {
        this.doors = doors;
    }
    
    public int getSeats() {
        return seats;
    }
    
    public void setSeats(int seats) {
        this.seats = seats;
    }
    
    public boolean isConvertible() {
        return isConvertible;
    }
    
    public void setConvertible(boolean convertible) {
        isConvertible = convertible;
    }
    
    @Override
    public String toString() {
        return super.toString() + ", Category: " + category + ", Doors: " + doors + ", Seats: " + seats + 
               (isConvertible ? ", Convertible" : "");
    }
}
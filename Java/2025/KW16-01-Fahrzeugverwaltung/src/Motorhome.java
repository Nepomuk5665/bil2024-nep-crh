public class Motorhome extends Vehicle {
    private int beds;
    private int kitchens;
    private boolean hasToilet;
    
    public Motorhome(String name, int beds, int kitchens, boolean hasToilet) {
        super(name, 18);
        this.beds = beds;
        this.kitchens = kitchens;
        this.hasToilet = hasToilet;
    }
    
    public int getBeds() {
        return beds;
    }
    
    public void setBeds(int beds) {
        this.beds = beds;
    }
    
    public int getKitchens() {
        return kitchens;
    }
    
    public void setKitchens(int kitchens) {
        this.kitchens = kitchens;
    }
    
    public boolean hasToilet() {
        return hasToilet;
    }
    
    public void setHasToilet(boolean hasToilet) {
        this.hasToilet = hasToilet;
    }
    
    @Override
    public String toString() {
        return super.toString() + ", Beds: " + beds + ", Kitchens: " + kitchens + 
               (hasToilet ? ", Has Toilet" : "");
    }
}
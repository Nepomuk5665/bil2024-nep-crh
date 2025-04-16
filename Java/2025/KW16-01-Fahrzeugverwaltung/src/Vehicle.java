public abstract class Vehicle implements Rentable {
    private String name;
    private int minimumDriverAge;
    
    public Vehicle(String name, int minimumDriverAge) {
        this.name = name;
        this.minimumDriverAge = minimumDriverAge;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public int getMinimumDriverAge() {
        return minimumDriverAge;
    }
    
    public void setMinimumDriverAge(int minimumDriverAge) {
        this.minimumDriverAge = minimumDriverAge;
    }
    
    @Override
    public String toString() {
        return getClass().getSimpleName() + ": " + name;
    }
}
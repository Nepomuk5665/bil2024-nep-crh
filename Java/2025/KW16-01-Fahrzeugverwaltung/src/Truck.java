public class Truck extends Vehicle {
    private int maxLoadWeight;
    private boolean hasLiftgate;
    
    public Truck(String name, int maxLoadWeight, boolean hasLiftgate) {
        super(name, 18);
        this.maxLoadWeight = maxLoadWeight;
        this.hasLiftgate = hasLiftgate;
    }
    
    public int getMaxLoadWeight() {
        return maxLoadWeight;
    }
    
    public void setMaxLoadWeight(int maxLoadWeight) {
        this.maxLoadWeight = maxLoadWeight;
    }
    
    public boolean hasLiftgate() {
        return hasLiftgate;
    }
    
    public void setHasLiftgate(boolean hasLiftgate) {
        this.hasLiftgate = hasLiftgate;
    }
    
    @Override
    public String toString() {
        return super.toString() + ", Max Load: " + maxLoadWeight + "kg" + 
               (hasLiftgate ? ", Has Liftgate" : "");
    }
}
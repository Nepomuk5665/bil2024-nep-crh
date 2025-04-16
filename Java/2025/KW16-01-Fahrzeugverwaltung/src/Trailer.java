public class Trailer extends Vehicle {
    private String purpose;
    private int maxLoadWeight;
    
    public Trailer(String name, String purpose, int maxLoadWeight) {
        super(name, 18);
        this.purpose = purpose;
        this.maxLoadWeight = maxLoadWeight;
    }
    
    public String getPurpose() {
        return purpose;
    }
    
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
    
    public int getMaxLoadWeight() {
        return maxLoadWeight;
    }
    
    public void setMaxLoadWeight(int maxLoadWeight) {
        this.maxLoadWeight = maxLoadWeight;
    }
    
    @Override
    public String toString() {
        return super.toString() + ", Purpose: " + purpose + ", Max Load: " + maxLoadWeight + "kg";
    }
}
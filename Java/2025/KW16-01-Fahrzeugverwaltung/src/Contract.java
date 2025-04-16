import java.time.LocalDate;

public class Contract {
    private Person renter;
    private Rentable rentable;
    private LocalDate startDate;
    private LocalDate endDate;
    private String contractTerms;
    
    public Contract(Person renter, Rentable rentable, LocalDate startDate, LocalDate endDate, String contractTerms) 
            throws MinorAgeException {
        if (renter.getAge() < rentable.getMinimumDriverAge()) {
            throw new MinorAgeException("Mieter " + renter.getName() + " ist zu jung (Alter " + renter.getAge() + 
                    "), um dieses Fahrzeug zu mieten. Mindestalter: " + rentable.getMinimumDriverAge());
        }
        
        this.renter = renter;
        this.rentable = rentable;
        this.startDate = startDate;
        this.endDate = endDate;
        this.contractTerms = contractTerms;
    }
    
    public Person getRenter() {
        return renter;
    }
    
    public void setRenter(Person renter) throws MinorAgeException {
        if (renter.getAge() < rentable.getMinimumDriverAge()) {
            throw new MinorAgeException("Mieter " + renter.getName() + " ist zu jung, um dieses Fahrzeug zu mieten. " +
                    "Mindestalter: " + rentable.getMinimumDriverAge());
        }
        this.renter = renter;
    }
    
    public Rentable getRentable() {
        return rentable;
    }
    
    public void setRentable(Rentable rentable) throws MinorAgeException {
        if (renter.getAge() < rentable.getMinimumDriverAge()) {
            throw new MinorAgeException("Mieter " + renter.getName() + " ist zu jung, um dieses Fahrzeug zu mieten. " +
                    "Mindestalter: " + rentable.getMinimumDriverAge());
        }
        this.rentable = rentable;
    }
    
    public LocalDate getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    
    public LocalDate getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    
    public String getContractTerms() {
        return contractTerms;
    }
    
    public void setContractTerms(String contractTerms) {
        this.contractTerms = contractTerms;
    }
    
    public boolean overlaps(Contract other) {
        if (!this.rentable.equals(other.rentable)) {
            return false;
        }
        
        return !(this.endDate.isBefore(other.startDate) || this.startDate.isAfter(other.endDate));
    }
    
    @Override
    public String toString() {
        return "Vertrag für " + rentable.getName() + " gemietet von " + renter.getName() + 
               " von " + startDate + " bis " + endDate;
    }
}
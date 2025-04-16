import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VehicleRentalManager {
    private List<Person> customerList;
    private List<Person> denylist;
    private List<Rentable> rentables;
    private List<Contract> contracts;
    
    public VehicleRentalManager() {
        customerList = new ArrayList<>();
        denylist = new ArrayList<>();
        rentables = new ArrayList<>();
        contracts = new ArrayList<>();
    }
    
    public List<Person> getCustomerList() {
        return customerList;
    }
    
    public List<Person> getDenylist() {
        return denylist;
    }
    
    public List<Rentable> getRentables() {
        return rentables;
    }
    
    public List<Contract> getContracts() {
        return contracts;
    }
    
    public void addPerson(Person person) {
        if (!customerList.contains(person)) {
            customerList.add(person);
        }
    }
    
    public void addPersonToDenylist(Person person) {
        if (!denylist.contains(person)) {
            denylist.add(person);
        }
    }
    
    public void removePersonFromDenylist(Person person) {
        denylist.remove(person);
    }
    
    public void addVehicle(Vehicle vehicle) {
        addRentable(vehicle);
    }
    
    public void addRentable(Rentable rentable) {
        if (!rentables.contains(rentable)) {
            rentables.add(rentable);
        }
    }
    
    public Contract createContract(Person renter, Rentable rentable, LocalDate startDate, LocalDate endDate, String contractTerms) 
            throws MinorAgeException, LeaseLengthCollisionException, DenylistedPersonException {
        
        if (denylist.contains(renter)) {
            throw new DenylistedPersonException("Person " + renter.getName() + " steht auf der Sperrliste und kann keine Fahrzeuge mieten.");
        }
        
        Contract newContract = new Contract(renter, rentable, startDate, endDate, contractTerms);
        
        for (Contract existingContract : contracts) {
            if (existingContract.getRentable().equals(rentable) && existingContract.overlaps(newContract)) {
                throw new LeaseLengthCollisionException("Fahrzeug " + rentable.getName() + 
                        " ist im gewünschten Zeitraum bereits vermietet.");
            }
        }
        
        contracts.add(newContract);
        return newContract;
    }
}
import java.time.LocalDate;

public class Starter {
    public static void main(String[] args) {
        VehicleRentalManager manager = new VehicleRentalManager();
        
        Car smallCar = new Car("Opel Corsa", "Kleinwagen", 4, 5, false);
        Car luxuryCar = new Car("BMW Z4", "Luxuswagen", 2, 2, true);
        Truck truck = new Truck("VW Crafter", 3500, true);
        Motorhome motorhome = new Motorhome("VW California", 4, 1, true);
        Trailer trailer = new Trailer("Böckmann Tieflader", "Velos", 750);
        
        manager.addVehicle(smallCar);
        manager.addVehicle(luxuryCar);
        manager.addVehicle(truck);
        manager.addVehicle(motorhome);
        manager.addVehicle(trailer);
        
        Person adultPerson = new Person("Hans Müller", LocalDate.of(1990, 5, 15), "ZH123456");
        Person youngPerson = new Person("Anna Schmid", LocalDate.of(2008, 3, 10), "BE789012");
        Person blacklistedPerson = new Person("Thomas Weber", LocalDate.of(1985, 8, 22), "AG456789");
        
        manager.addPerson(adultPerson);
        manager.addPerson(youngPerson);
        manager.addPerson(blacklistedPerson);
        
        manager.addPersonToDenylist(blacklistedPerson);
        
        System.out.println("Fahrzeuge im Bestand: ");
        for (Rentable rentable : manager.getRentables()) {
            System.out.println("- " + rentable);
        }
        
        System.out.println("\nKunden: ");
        for (Person person : manager.getCustomerList()) {
            System.out.println("- " + person);
        }
        
        System.out.println("\nPersonen auf der Sperrliste: ");
        for (Person person : manager.getDenylist()) {
            System.out.println("- " + person);
        }
        
        System.out.println("\nTest der Vertragserstellung:");
        
        try {
            Contract validContract = manager.createContract(
                adultPerson, 
                smallCar, 
                LocalDate.now(), 
                LocalDate.now().plusDays(5), 
                "Standardmietbedingungen"
            );
            System.out.println("Gültiger Vertrag erstellt: " + validContract);
            
            try {
                Contract minorContract = manager.createContract(
                    youngPerson, 
                    luxuryCar, 
                    LocalDate.now(), 
                    LocalDate.now().plusDays(3), 
                    "Standardmietbedingungen"
                );
                System.out.println("FEHLER: Hätte MinorAgeException werfen sollen");
            } catch (MinorAgeException e) {
                System.out.println("MinorAgeException korrekt geworfen: " + e.getMessage());
            }
            
            try {
                Contract overlappingContract = manager.createContract(
                    adultPerson, 
                    smallCar, 
                    LocalDate.now().plusDays(2), 
                    LocalDate.now().plusDays(7), 
                    "Standardmietbedingungen"
                );
                System.out.println("FEHLER: Hätte LeaseLengthCollisionException werfen sollen");
            } catch (LeaseLengthCollisionException e) {
                System.out.println("LeaseLengthCollisionException korrekt geworfen: " + e.getMessage());
            }
            
            try {
                Contract denylistedContract = manager.createContract(
                    blacklistedPerson, 
                    trailer, 
                    LocalDate.now(), 
                    LocalDate.now().plusDays(4), 
                    "Standardmietbedingungen"
                );
                System.out.println("FEHLER: Hätte DenylistedPersonException werfen sollen");
            } catch (DenylistedPersonException e) {
                System.out.println("DenylistedPersonException korrekt geworfen: " + e.getMessage());
            }
            
        } catch (Exception e) {
            System.out.println("Unerwarteter Fehler: " + e.getMessage());
        }
    }
}
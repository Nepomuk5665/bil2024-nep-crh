import java.time.LocalDate;
import java.time.Period;

public class Person {
    private String name;
    private LocalDate birthDate;
    private String licenseId;
    
    public Person(String name, LocalDate birthDate, String licenseId) {
        this.name = name;
        this.birthDate = birthDate;
        this.licenseId = licenseId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public LocalDate getBirthDate() {
        return birthDate;
    }
    
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
    
    public String getLicenseId() {
        return licenseId;
    }
    
    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
    }
    
    public int getAge() {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
    
    @Override
    public String toString() {
        return "Person: " + name + ", Alter: " + getAge() + ", Führerschein: " + licenseId;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Person person = (Person) obj;
        return licenseId.equals(person.licenseId);
    }
    
    @Override
    public int hashCode() {
        return licenseId.hashCode();
    }
}
import java.util.Scanner;

public class Medium {

    private String titel;
    private int year;
    private String language;

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }


    public static Medium medium() {
        Scanner scanner = new Scanner(System.in);
        Medium medium = new Medium();
        
        System.out.println("Titel: ");
        medium.setTitel(scanner.nextLine());
        
        System.out.println("Year: ");
        medium.setYear(scanner.nextInt());
        scanner.nextLine();
        
        System.out.println("Language: ");
        medium.setLanguage(scanner.nextLine());
        
        return medium;
    }

    public void printMedium() {
        System.out.println("Medium Titel: " + titel);
        System.out.println("Year: " + year);
        System.out.println("Language: " + language);
    }
}

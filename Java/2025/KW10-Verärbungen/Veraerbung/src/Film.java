
import java.util.Scanner;

public class Film extends Medium{
    private String regisseur;
    private int length;
    private String genre;

    public String getRegisseur() {
        return regisseur;
    }

    public void setRegisseur(String regisseur) {
        this.regisseur = regisseur;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
    
    public static Film film() {
        Scanner scanner = new Scanner(System.in);
        Film film = new Film();
        
        Medium medium = Medium.medium();
        film.setTitel(medium.getTitel());
        film.setYear(medium.getYear());
        film.setLanguage(medium.getLanguage());
        
        System.out.println("Regisseur: ");
        film.setRegisseur(scanner.nextLine());
        
        System.out.println("Length in minutes: ");
        film.setLength(scanner.nextInt());
        scanner.nextLine();
        
        System.out.println("Genre: ");
        film.setGenre(scanner.nextLine());
        
        return film;
    }
    
    @Override
    public void printMedium() {
        super.printMedium();
        System.out.println("Regisseur: " + regisseur);
        System.out.println("Length: " + length + " minutes");
        System.out.println("Genre: " + genre);
    }
}

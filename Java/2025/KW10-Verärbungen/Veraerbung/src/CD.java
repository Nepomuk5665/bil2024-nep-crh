
import java.util.Scanner;

public class CD extends Medium{
    private String interpret;
    private int numberOfTracks;
    private String genre;

    public String getInterpret() {
        return interpret;
    }

    public void setInterpret(String interpret) {
        this.interpret = interpret;
    }

    public int getNumberOfTracks() {
        return numberOfTracks;
    }

    public void setNumberOfTracks(int numberOfTracks) {
        this.numberOfTracks = numberOfTracks;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
    
    public static CD cd() {
        Scanner scanner = new Scanner(System.in);
        CD cd = new CD();
        
        Medium medium = Medium.medium();
        cd.setTitel(medium.getTitel());
        cd.setYear(medium.getYear());
        cd.setLanguage(medium.getLanguage());
        
        System.out.println("Interpret: ");
        cd.setInterpret(scanner.nextLine());
        
        System.out.println("Number of Tracks: ");
        cd.setNumberOfTracks(scanner.nextInt());
        scanner.nextLine();
        
        System.out.println("Genre: ");
        cd.setGenre(scanner.nextLine());
        
        return cd;
    }
    
    @Override
    public void printMedium() {
        super.printMedium();
        System.out.println("Interpret: " + interpret);
        System.out.println("Number of Tracks: " + numberOfTracks);
        System.out.println("Genre: " + genre);
    }
}


import java.util.Scanner;

public class Game extends Medium{
    private String plattform;
    private String publisher;
    private String genre;
    private int fsk;

    public String getPlattform() {
        return plattform;
    }

    public void setPlattform(String plattform) {
        this.plattform = plattform;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getFsk() {
        return fsk;
    }

    public void setFsk(int fsk) {
        this.fsk = fsk;
    }
    
    public static Game game() {
        Scanner scanner = new Scanner(System.in);
        Game game = new Game();
        
        Medium medium = Medium.medium();
        game.setTitel(medium.getTitel());
        game.setYear(medium.getYear());
        game.setLanguage(medium.getLanguage());
        
        System.out.println("Plattform: ");
        game.setPlattform(scanner.nextLine());
        
        System.out.println("Publisher: ");
        game.setPublisher(scanner.nextLine());
        
        System.out.println("Genre: ");
        game.setGenre(scanner.nextLine());
        
        System.out.println("FSK: ");
        game.setFsk(scanner.nextInt());
        scanner.nextLine();
        
        return game;
    }
    
    @Override
    public void printMedium() {
        super.printMedium();
        System.out.println("Plattform: " + plattform);
        System.out.println("Publisher: " + publisher);
        System.out.println("Genre: " + genre);
        System.out.println("FSK: " + fsk);
    }
}

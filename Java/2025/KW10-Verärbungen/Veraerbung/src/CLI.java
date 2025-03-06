import java.util.ArrayList;
import java.util.Scanner;

public class CLI {
    private ArrayList<Medium> mediumArray = new ArrayList<>();
    // besserer name
    public void cli() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        
        while(running) {
            System.out.println("========================");
            System.out.println("Hier sind deine Medien:");
            for(Medium m: mediumArray){
                m.printMedium();
                System.out.println("------------------------");
            }
            System.out.println("========================");
            System.out.println("Was willst du machen?");
            System.out.println("1. Medium hinzufügen");
            System.out.println("2. Medium entfernen");
            System.out.println("3. Beenden");
            int input = scanner.nextInt();
            scanner.nextLine();
            
            switch(input){
                case 1:
                    addMedium();
                    break;
                case 2:
                    deleteMedium();
                    break;
                case 3:
                    running = false;
                    break;
                default:
                    System.out.println("Falscher Input");
                    break;
            }
        }
    }
    public void addMedium() {
        System.out.println("Was für ein Medium?");
        System.out.println("1. Buch");
        System.out.println("2. Film");
        System.out.println("3. CD");
        System.out.println("4. Game");
        System.out.println("5. Medium");
        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();
        scanner.nextLine();
        
        switch(input) {
            case 1:
                mediumArray.add(Book.book());
                break;
            case 2:
                mediumArray.add(Film.film());
                break;
            case 3:
                mediumArray.add(CD.cd());
                break;
            case 4:
                mediumArray.add(Game.game());
                break;
            case 5:
                mediumArray.add(Medium.medium());
                break;
            default:
                System.out.println("Ungültige Eingabe");
                break;
        }
    }
    
    public void deleteMedium() {
        if (mediumArray.isEmpty()) {
            System.out.println("Keine Medien vorhanden");
            return;
        }
        
        System.out.println("Welches Medium möchtest du löschen?");
        for (int i = 0; i < mediumArray.size(); i++) {
            System.out.println((i+1) + ". " + mediumArray.get(i).getTitel());
        }
        
        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();
        scanner.nextLine();
        
        if (input > 0 && input <= mediumArray.size()) {
            mediumArray.remove(input - 1);
            System.out.println("Medium gelöscht");
        } else {
            System.out.println("Ungültige Eingabe");
        }
    }
}

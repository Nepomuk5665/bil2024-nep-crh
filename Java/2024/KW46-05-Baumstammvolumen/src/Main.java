import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.println("Länge? in Meter");
        int m = input.nextInt();

        System.out.println("Durchmesser? in cm");
        int cm = input.nextInt();

        double solution = (Math.PI/4)*cm*cm*((double) m /10000);

        System.out.println("Keine Ahnung was die Aufgabe war aber das ist die Lösung: " + solution);


    }
}
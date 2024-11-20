import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Gib eine ganze Zahl ein: ");
        int zahl = scanner.nextInt();

        if (istPalindrom(zahl)) {
            System.out.println(zahl + " ist ein Palindrom.");
        } else {
            System.out.println(zahl + " ist kein Palindrom.");
        }
    }

    public static boolean istPalindrom(int zahl) {
        if (zahl < 0) {
            return false;
        }

        int urspruenglicheZahl = zahl;
        int umgedrehteZahl = 0;

        while (zahl > 0) {
            int letzteZiffer = zahl % 10;
            umgedrehteZahl = umgedrehteZahl * 10 + letzteZiffer;
            zahl = zahl / 10;
        }

        return urspruenglicheZahl == umgedrehteZahl;
    }
}
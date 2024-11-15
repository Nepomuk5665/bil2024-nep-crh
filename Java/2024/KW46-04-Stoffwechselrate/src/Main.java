
import java.util.Scanner;

import static java.lang.Math.round;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);  // Scanner object

        System.out.println("Gewicht? in kg");
        int kg = scanner.nextInt();

        System.out.println("Grösse? in cm");
        int cm = scanner.nextInt();

        System.out.println("Alter? in Jahren");
        int years = scanner.nextInt();


        double maleCalories = 66.47 + 13.7 * kg + 5 * cm - 6.8 * years;
        double femaleCalories = 655.1 + 9.6 * kg + 1.8 * cm - 4.7 * years;

        System.out.println("Mann: " + Math.round(maleCalories * 100.0) / 100.0 + " Kalorien pro Tag");
        System.out.println("Frau: " + Math.round(femaleCalories * 100.0) / 100.0 + " Kalorien pro Tag");
    }
}
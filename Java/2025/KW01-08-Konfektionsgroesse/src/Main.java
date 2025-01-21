import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Mann oder Frau? (M = 1/F = 2");
        int gender = scanner.nextInt();
        int hight = 0;
        if (gender == 2) {
            System.out.print("Höhe: ");
            hight = scanner.nextInt();
        }
        System.out.print("Brustumfang: ");
        int chestCircumference = scanner.nextInt();


        computeGarmentSize(hight, chestCircumference, gender);

    }

    static void computeGarmentSize(int hight, int chestCircumference, int gender) {
        if (gender == 1){

        }else if (gender == 2){

        }else{
            System.out.print("Wrong gender entered");
        }


    }
}
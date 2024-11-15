package ch.noseryoung.blj;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in); // scanner objekt
        int baum = 0;
        personen person1 = new personen();

        System.out.println("Name von der ersten person?");
        String name = input.nextLine();

        System.out.println("Lieblingsfarbe von der ersten person?");
        String color = input.nextLine();

        System.out.println("Eigenschaft von der ersten person?");
        String eigenschaft = input.nextLine();

        System.out.println("Dein zugang? 1 = Read & Write 0 = Read");
        int access = Integer.parseInt(input.nextLine());



        person1.setName(name, access);

        person1.setLieblingsfarbe(color,access);

        person1.setEigenschaft(eigenschaft,access);

        System.out.println("Name: " + person1.getName() + "\nLieblingsfarbe: " + person1.getLieblingsfarbe() + "\nEingenschaft: " + person1.getEigenschaft());

        do{
            System.out.println("Was willst du machen?\n1. Edit your Access\n2. Edit erste Person\n3. Exit");
            String in = input.nextLine();

            switch (in){
                case "1":
                    System.out.println("Dein zugang? 1 = Read & Write 0 = Read");
                    access = Integer.parseInt(input.nextLine());
                    break;
                    case "2":
                        System.out.println("Name von der ersten person?");
                        name = input.nextLine();

                        System.out.println("Lieblingsfarbe von der ersten person?");
                        color = input.nextLine();

                        System.out.println("Eigenschaft von der ersten person?");
                        eigenschaft = input.nextLine();





                        person1.setName(name, access);

                        person1.setLieblingsfarbe(color,access);

                        person1.setEigenschaft(eigenschaft,access);

                        System.out.println("Name: " + person1.getName() + "\nLieblingsfarbe: " + person1.getLieblingsfarbe() + "\nEingenschaft: " + person1.getEigenschaft());

                        break;
                        case "3":
                            System.out.println("Tschau");
                            baum = 1;

            }

        }while(baum == 0);



    }
}
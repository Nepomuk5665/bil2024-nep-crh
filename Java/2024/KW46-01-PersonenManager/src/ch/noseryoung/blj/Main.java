package ch.noseryoung.blj;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in); // Scanner object
        int exitFlag = 0;
        Person person1 = new Person();

        System.out.println("What is the name of the first person?");
        String name = input.nextLine();

        System.out.println("What is the favorite color of the first person?");
        String favoriteColor = input.nextLine();

        System.out.println("What is a characteristic of the first person?");
        String characteristic = input.nextLine();

        System.out.println("Your access level? 1 = Read & Write, 0 = Read");
        int access = Integer.parseInt(input.nextLine());
        person1.setAccessLevel(access);

        person1.setName(name);
        person1.setFavoriteColor(favoriteColor);
        person1.setCharacteristic(characteristic);

        System.out.println("Name: " + person1.getName() +
                "\nFavorite Color: " + person1.getFavoriteColor() +
                "\nCharacteristic: " + person1.getCharacteristic());

        do {
            System.out.println("What would you like to do?\n1. Edit your access level\n2. Edit the first person\n3. Exit");
            String choice = input.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("Your access level? 1 = Read & Write, 0 = Read");
                    access = Integer.parseInt(input.nextLine());
                    person1.setAccessLevel(access);
                    break;
                case "2":
                    System.out.println("What is the name of the first person?");
                    name = input.nextLine();

                    System.out.println("What is the favorite color of the first person?");
                    favoriteColor = input.nextLine();

                    System.out.println("What is a characteristic of the first person?");
                    characteristic = input.nextLine();

                    person1.setName(name);
                    person1.setFavoriteColor(favoriteColor);
                    person1.setCharacteristic(characteristic);

                    System.out.println("Name: " + person1.getName() +
                            "\nFavorite Color: " + person1.getFavoriteColor() +
                            "\nCharacteristic: " + person1.getCharacteristic());
                    break;
                case "3":
                    System.out.println("Goodbye!");
                    exitFlag = 1;
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        } while (exitFlag == 0);
    }
}

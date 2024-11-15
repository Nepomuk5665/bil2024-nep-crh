package ch.noseryoung.blj;


import java.util.Random;

public class figures {

    public void tree(int height) {

        Random random = new Random();
        String[] colors = {"\u001B[31m", "\u001B[32m", "\u001B[33m", "\u001B[34m", "\u001B[35m", "\u001B[36m"};

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < height - 1 - i; j++) {
                System.out.print(" ");
            }

            for (int k = 0; k < 2 * i + 1; k++) {

                String color = colors[random.nextInt(colors.length)];
                System.out.print(color + "*");
            }
            System.out.println("\u001B[0m");
        }

        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < height - 2; j++) {
                System.out.print(" ");
            }
            System.out.println("***");
        }
    }




}

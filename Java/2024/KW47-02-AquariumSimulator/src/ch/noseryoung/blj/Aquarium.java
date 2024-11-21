package ch.noseryoung.blj;

import java.util.ArrayList;
import java.util.Arrays;

public class Aquarium {
    private int[][] aquarium;
    private boolean isSaltwater;
    private ArrayList<Fish> fishes;
    private int height;

    public Aquarium(int width, int height, boolean isSaltwater) {
        this.aquarium = new int[height][width];
        this.isSaltwater = isSaltwater;
        this.fishes = new ArrayList<>();
        this.height = height;

        // setRandomFishes(height, width);
    }


    private void setRandomFishes(int height, int width) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if(Math.random() < 0.5) {
                    aquarium[i][j] = 1;
                    Fish maria = new Fish("Maria", isSaltwater, i, j);
                    fishes.add(maria);

                }
            }
        }
    }

    public void printAquarium() {
        for (int i = 0; i < aquarium.length; i++) {
            System.out.print("▓▓");
        }
        System.out.println("▓");
        String aquariumAsString = Arrays.deepToString(aquarium);
        aquariumAsString = aquariumAsString.replace("[[", "▓").replace("], [", "▓\n▓").replace(", ", " ").replace("]]",
                "▓").replace("0", "\uD83D\uDCA7").replace("1", "\uD83E\uDD88").replace("2",
                "\uD83E\uDEB4").replace("3", "\uD83E\uDEA8");
        System.out.println(aquariumAsString);

        for (int i = 0; i < aquarium.length; i++) {
            System.out.print("▓▓");
        }
        System.out.println("▓");
    }

    public void changePosition(int oldx, int oldy, int newx, int newy) {
        boolean changed = false;
        for (int i = 0; i < fishes.size(); i++) {
            if (fishes.get(i).getPosX() == oldx && fishes.get(i).getPosY() == oldy) {
                fishes.get(i).setPosX(newx);
                fishes.get(i).setPosY(newy);
                aquarium[oldy][oldx] = 0;
                aquarium[newy][newx] = 1;
                changed = true;
            }
            System.out.println("Position changed from X:" + oldx + " Y:" + oldy + " to X:" + newx + " Y:" + newy);
        }

    }

    public String getFishesCount() {
        if (fishes.size() == 0){
            return ("Es hat keine Fische im Aquarium");
        }
        else {
            return ("Es hat " + fishes.size() + (fishes.size() < 2 ? " Fisch im Aquarium" : " Fische im Aquarium"));
        }
    }

    public void addFish(int posX, int posY, String fishName) {
        if(aquarium[posY][posX] == 0) {
            fishes.add(new Fish(fishName, isSaltwater, posX, posY));
            aquarium[posY][posX] = 1;
        }else {
            // wenn schon fisch da ist wird das ausgeführt
        }


    }

    public void removeFish(int posX, int posY) {
        boolean removed = false;
        for (int i = 0; i < fishes.size(); i++) {
            if (fishes.get(i).getPosX() == posX && fishes.get(i).getPosY() == posY) {
                fishes.remove(i);
                aquarium[posY][posX] = 0;
                removed = true;
            }

        }
        if (!removed) {
            // error message no fish found at index
        }
    }
    public void printAquariumInformation() {
        printAquarium();
        System.out.println(getFishesCount());
        System.out.println(isSaltwater ? "The water is salty" : "The water is not salty");
    }

    public void addPlant(int posX) {
        aquarium[height-1][posX] = 2;
    }

    public void addStone(int posX) {
        aquarium[height-1][posX] = 3;
    }

}

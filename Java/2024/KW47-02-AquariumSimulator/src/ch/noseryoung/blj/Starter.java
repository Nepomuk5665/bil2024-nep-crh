package ch.noseryoung.blj;

public class Starter {
    public static void main(String[] args) {

        Aquarium aquarium = new Aquarium(10, 5, true);

        aquarium.printAquariumInformation();

        aquarium.addFish(2,2, "Maria");

        aquarium.printAquariumInformation();

        aquarium.changePosition(2, 2, 1, 1);

        aquarium.addPlant(5);

        aquarium.addStone(2);

        aquarium.printAquariumInformation();




    }
}


package ch.noseryoung.blj;

public class Fish {
    private String name;
    private int posX;
    private int posY;
    private boolean isSaltwaterFish;

    public Fish(String name, boolean isSaltwaterFish, int posX, int posY) {
        this.name = name;
        this.isSaltwaterFish = isSaltwaterFish;
        this.posX = posX;
        this.posY = posY;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
}
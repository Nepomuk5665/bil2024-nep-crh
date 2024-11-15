package ch.noseryoung.blj;

public class Person {
    private String name;
    private String favoriteColor;
    private String characteristic;
    private int accessLevel;

    public String getName() {
        return name;
    }

    public String getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(String characteristic) {
        if (accessLevel == 1) {
            this.characteristic = characteristic;
        } else {
            System.out.println("You are not allowed to chnage characteristic of " + this.name);
        }
    }

    public void setName(String name) {
        if (accessLevel == 1) {
            this.name = name;
        } else {
            System.out.println("You are not allowed change the name of " + this.name);
        }
    }

    public String getFavoriteColor() {
        return favoriteColor;
    }

    public void setFavoriteColor(String favoriteColor) {
        if (accessLevel == 1) {
            this.favoriteColor = favoriteColor;
        } else {
            System.out.println("You are not allowed to change the favorite color of " + this.name);
        }
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }
}

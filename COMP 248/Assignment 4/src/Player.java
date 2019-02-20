// ---------------------------------------------------------------
// Assignment 4
// Written by: Anik Patel - 40091908
// For COMP 248 Section Q - Fall 2018
// ---------------------------------------------------------------

public class Player {
    private String name;
    private Garden garden;

    public Player(String name, int size) {
        this.name = name;
        if (size == 3)
            garden = new Garden();
        else
            garden = new Garden(size);
    }

    public String getPlayerName() {
        return name;
    }

    public int howManyFlowersPossible() {
        return garden.countPossibleFlowers();
    }

    public int howManyTreesPossible() {
        return garden.countPossibleTree();
    }

    public char whatIsPlanted(int r, int c) {
        return garden.getInLocation(r, c);
    }

    public void plantTreeInGarden(int r, int c) {
        garden.plantTree(r, c);
    }

    public void plantFlowerInGarden(int r, int c) {
        garden.plantFlower(r, c);
    }

    //To be able to use the isEmpty method from the garden class, on each players' garden
    public boolean isGardenEmpty() {
        return garden.isEmpty();
    }

    public void eatHere(int r, int c) {
        garden.removeFlower(r, c);
    }

    public boolean isGardenFull() {
        return garden.gardenFull();
    }

    public String showGarden() {
        return garden.toString();
    }
}

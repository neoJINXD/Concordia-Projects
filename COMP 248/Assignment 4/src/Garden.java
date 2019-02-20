// ---------------------------------------------------------------
// Assignment 4
// Written by: Anik Patel - 40091908
// For COMP 248 Section Q - Fall 2018
// ---------------------------------------------------------------


public class Garden {
    //2d array with information on each position in the supposed garden
    private char garden[][];

    //Default constructor, size to 3x3
    public Garden() {
        garden = new char[3][3];
        initializeGarden();
    }
    //Constructor, lets user pick size, still remains a square
    public Garden(int size) {
        garden = new char[size][size];
        initializeGarden();
    }

    //Sets all positions in Garden to empty, '-'
    private void initializeGarden() {
        for (int i = 0; i < garden.length; i++) {
            for (int j = 0; j < garden[i].length; j++) {
                garden[i][j] = '-';
            }
        }
    }

    //Returns the character at position r,c
    public char getInLocation(int r, int c) {
        return garden[r][c];
    }

    //Plants flower character, 'f', at position r,c
    public void plantFlower(int r, int c) {
        garden[r][c] = 'f';
    }

    //Plants the 2x2 tree characters, 't', by choosing the top left corner
    public void plantTree(int r, int c) {
        if ((garden[r][c] == '-') && (garden[r + 1][c] == '-') && (garden[r][c + 1] == '-') && (garden[r + 1][c + 1] == '-')) {
            garden[r][c] = 't';
            garden[r + 1][c] = 't';
            garden[r][c + 1] = 't';
            garden[r + 1][c + 1] = 't';
        } else {
            System.out.println("This place is invalid to place a tree");
        }


    }

    //Checks each elements of the 2d garden array if it is empty
    //If it is empty, the condition will be true
    public boolean isEmpty(){
        boolean condition = true;
        int count = 0;

        for (int i = 0; i < garden.length; i++)
            for (int j = 0; j < garden.length; j++) {
                if (garden[i][j] == '-')
                    continue;
                else {
                    count++;
                }
            }
        if (count > 0)
            condition = false;
        return condition;
    }

    //The action that the rabbit will perform, setting the character at r,c to empty,'-'
    //only if there is something in the garden
    public void removeFlower(int r, int c) {

        if (!isEmpty())
            garden[r][c] = '-';
        else
            System.out.println("Garden is currently Empty");


    }

    //Counts how many 2x2 trees can fit with the current state of the garden
    public int countPossibleTree() {
        int count = 0;
        for (int i = 0; i + 1 < garden.length; i++)
            for (int j = 0; j + 1 < garden[i].length; j++)
                if ((garden[i][j] == '-') && (garden[i + 1][j] == '-') && (garden[i][j + 1] == '-') && (garden[i + 1][j + 1] == '-'))
                    count++;

        return count;
    }

    //Counts how many 1x1 flowers can fit with the current state of the garden
    public int countPossibleFlowers() {
        int count = 0;
        for (int i = 0; i < garden.length; i++)
            for (int j = 0; j < garden[i].length; j++)
                if (garden[i][j] == '-')
                    count++;
        return count;
    }

    //Returns true if all the positions in the garden array are filled with either flowers, 'f',
    //or by trees,'t'
    public boolean gardenFull() {
        boolean condition = true;
        for (int i = 0; i < garden.length; i++)
            for (int j = 0; j < garden.length; j++)
                if (garden[i][j] == '-') {
                    condition = false;
                    break;
                }


        return condition;
    }

    //Returns a string containing the garden array formatted to look a certain way
    public String toString() {
        String board = "";

        board += "  | ";
        for (int i = 0; i < garden.length; i++)
            board += (" " + i + " ");

        board += "\n";
        for (int i = 0; i < garden.length; i++) {
            board += i;
            board += " | ";
            for (int j = 0; j < garden.length; j++)
                board += " " + this.getInLocation(i, j) + " ";
            board += "\n";
        }
        return board;
    }

    //Test code for the garden class
    public static void main(String[] args) {
        Garden test = new Garden(5);
        Garden test1 = new Garden();
        System.out.println(test.countPossibleTree());
        System.out.println(test.countPossibleFlowers());
        System.out.println(test);
        System.out.println(test.isEmpty());
        test.removeFlower(0, 0);
        test.plantFlower(4, 2);
        System.out.println(test);
        System.out.println(test.isEmpty());
        test.plantTree(0, 1);
        System.out.println(test);
        System.out.println(test.countPossibleTree());
        System.out.println(test.getInLocation(2, 1));
        System.out.println(test.getInLocation(1, 2));
        System.out.println(test.gardenFull());
        test1.plantTree(0, 0);
        test1.plantFlower(0, 2);
        test1.plantFlower(1, 2);
        test1.plantFlower(2, 2);
        test1.plantFlower(2, 0);
        test1.plantFlower(2, 1);
        System.out.println(test1);
        System.out.println(test1.gardenFull());

    }
}

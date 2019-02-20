// ---------------------------------------------------------------
// Assignment 4
// Written by: Anik Patel - 40091908
// For COMP 248 Section Q - Fall 2018
// ---------------------------------------------------------------

import java.util.Random;

public class Dice {
    //Parameters for the values of each dice
    private int dice1;
    private int dice2;

    //Default constructor
    public Dice() {
        dice1 = 0;
        dice2 = 0;
    }

    //Accessor methods for each dice
    public int getDice1() {
        return dice1;
    }

    public int getDice2() {
        return dice2;
    }

    //Rolling normal 6 sided dice, to get the total of the 2 dice
    public int rollDice() {
        Random rand = new Random();
        dice1 = (rand.nextInt(6) + 1);
        dice2 = (rand.nextInt(6) + 1);
        return dice1 + dice2;
    }

    //Returns string thhat contains the value of both dice separately
    public String toString() {
        return ("Dice 1: " + getDice1() + " Dice 2: " + getDice2());
    }





    //Test for the Dice class
    public static void main(String[] args) {
        Dice test = new Dice();
        //Default state for Dice
        System.out.println("The default state for the Dice:");
        System.out.println(test.getDice1()+test.getDice2());
        System.out.println("That is the total of "+test.getDice1()+" + "+test.getDice2());
        System.out.println();


        System.out.println("Testing out a few dice rolls:");
        System.out.println(test.rollDice());
        System.out.println("That is the total of "+test.getDice1()+" + "+test.getDice2());

        System.out.println(test.rollDice());
        System.out.println("That is the total of "+test.getDice1()+" + "+test.getDice2());

        System.out.println(test.rollDice());
        System.out.println("That is the total of "+test.getDice1()+" + "+test.getDice2());

    }
}



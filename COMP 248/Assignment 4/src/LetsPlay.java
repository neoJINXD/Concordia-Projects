// ---------------------------------------------------------------
// Assignment 4
// Written by: Anik Patel - 40091908
// For COMP 248 Section Q - Fall 2018
// ---------------------------------------------------------------


import java.util.Scanner;
import java.util.Random;

public class LetsPlay {
    public static void main(String[] args) {
        //Sets up all the variables used
        Scanner input = new Scanner(System.in); //Input Scanner object
        int gardenSize; //Size of the garden
        int playerNumber; //Amount of players
        String tempName;
        int numberOfTurns = 0;
        String winner = ""; //Name of the winner will be stored here eventually
        boolean winnerIsChosen = false;
        Dice dice = new Dice();
        boolean duplicateDetected;
        int row, column; //Row and Column chosen by user
        int currentRoll; //Stores the dice roll of the player playing
        Random rand = new Random(); //Random object to decide the rabbit's movement
        int highestRoll;
        String junk; //Empties the input by using nextLine so that it wont conflict with the next input needed

        //INSTRUCTIONS START
        System.out.println("-----****-------****-------****-------****-----****-----");
        System.out.println("         Welcome to Crazy Nancy's Garden Game!          ");
        System.out.println("-----****-------****-------****-------****-----****-----");
        System.out.println();
        System.out.println("To win this game you need some luck with the dice and a bit of strategy.");
        System.out.println("Your garden is an NxN lot. You can plant flowers or trees. A flower takes one spot. A tree takes 4 spots (2x2).");
        System.out.println("You roll the dice and based on the outcome you get to plant a pre-set number of trees and flowers.");
        System.out.println();
        System.out.println();
        System.out.println("Rolls and their outcome:");
        System.out.println("--------------------------");
        System.out.println("    3: plant a tree (2x2) and a flower (1x1)");
        System.out.println("    6: plant 2 flowers (2 times 1x1)");
        System.out.println("    12: plant 2 trees (2 times 2x2)");
        System.out.println("    5 or 10: The rabbit will eat something that you have planted\n" +
                "- might be a flower or part of a tree (1x1)");
        System.out.println("Any other EVEN rolls: plant a tree (2x2)");
        System.out.println("Any other ODD rolls: plant a flower (1x1)");
        System.out.println();
        System.out.println("Minimum number of players: 2.");
        System.out.println("Minimum garden size: 3x3.");
        System.out.println("You can only plant in empty locations.\n" +
                "To plant a tree you give the top left coordinates of the 2x2 space");
        System.out.println("and I will check to make sure all 4 locations are free.");
        System.out.println("Okay .. Let's start the game! May the best gardener win! (You won't win, I will haHAA)");
        System.out.println();
        //INSTRUCTIONS OVER


        System.out.println();
        System.out.println("The default garden is a 3x3 square. \n" +
                "You can use this default board size or change the size");

        //Gets user input for board size
        System.out.print("Enter 0 to use the default garden size or -1 to enter your own size:");
        do {
            gardenSize = input.nextInt();
            //Setting board to the default size
            if (gardenSize == 0) {
                System.out.println("Default selection chosen, garden is 3x3");
                gardenSize = 3;
                break;
            } else if (gardenSize == -1) {
                do {
                    //Minimum size is the default size
                    System.out.println("What size board would you want? (minimum 3)");
                    gardenSize = input.nextInt();
                    if (gardenSize < 3)
                        System.out.println("Error, garden size too small!");
                    else {
                        System.out.println("User selected size, garden is " + gardenSize + "x" + gardenSize);
                        break;
                    }

                }
                while (true);
                break;
            } else
                System.out.println("I am sorry, but " + gardenSize + " is not a valid choice. Enter your choice");

        }
        while (true);

        //Gets user input for how many players are playing
        System.out.println("How many gardeners will there be? (minimum 2 required, max allowed 10)");
        do {
            playerNumber = input.nextInt();
            if ((playerNumber < 2) || (playerNumber > 10))
                System.out.println("Error, " + playerNumber + " is not a legal amount of players!");
            else {
                System.out.println("Player number set, there are " + playerNumber + " players in the game");
                break;
            }
        }
        while (true);

        //Sets up an initial array of Player objects
        Player[] arrayPlayers = new Player[playerNumber];

        System.out.println();

        //Gets the name for each player
        for (int i = 0; i < playerNumber; i++) {
            System.out.print("--> Name of player " + i + " (no spaces allowed): ");
            tempName = input.next();
            arrayPlayers[i] = new Player(tempName, gardenSize);
        }
        System.out.println();
        System.out.println("Rolling everyone's initial dice rolls");
        //CHOSE PLAYER ORDER
        //Loop to sort all the dice rolls that each player does from lowest to highest
        int[] initDiceRolls;
        do {
            duplicateDetected = false;
            //Sets an array to hold the initial dice rolls
            //The index of the roll corresponds to the index of the Player in the Player array
            initDiceRolls = new int[playerNumber];
            for (int i = 0; i < playerNumber; i++) {
                initDiceRolls[i] = dice.rollDice();
                System.out.println(arrayPlayers[i].getPlayerName() + " rolled a " + initDiceRolls[i]);
            }


            //Starts by assuming highest roll is the first person on the list
            highestRoll = 0;
            for (int i = 1; i < initDiceRolls.length; i++)
                if (initDiceRolls[i] > initDiceRolls[highestRoll])
                    highestRoll = i;


            //Duplicate roll check
            for (int i = 0; i < playerNumber - 1; i++)
                for (int j = i + 1; j < playerNumber; j++)
                    if ((i != j) && (initDiceRolls[i] == initDiceRolls[j]))
                        duplicateDetected = true;

            if (duplicateDetected) {
                System.out.println("\nDuplicate roll detected, rerolling everyone");

            } else {
                System.out.println("No Duplicate rolls detected.\nThe game may now begin!");
                break;
            }

        }
        while (true);

        //Creates a brand new array for Player type with it being sorted based on highest roll going first
        Player[] finalPlayerArray = new Player[playerNumber];
        //Loop to put Player objects into the new array, ordered by their play order, from the old unordered array
        for (int i = highestRoll, count = 0; count < playerNumber; i++, count++) {
            finalPlayerArray[count] = arrayPlayers[i % playerNumber];

        }

        //Prints to the user the order of the game
        System.out.println("\nThe order of the game is the following: ");
        for (int k = 0; k < finalPlayerArray.length; k++) {
            System.out.print((k + 1) + ": ");
            System.out.print(finalPlayerArray[k].getPlayerName());
            System.out.println();
        }
        System.out.println("\n");

        System.out.println("NOW IT IS TIME TO BEGIN THE GAME!");
        System.out.println("----------------------------------\n");
        //LOOP FOR MAIN GAME
        //Loop for each turn of the main game

        do {

            numberOfTurns++;

            System.out.println("We are currently at turn Number " + numberOfTurns);
            //Loop for every individual player
            for (int i = 0; i < playerNumber; i++) {
                currentRoll = dice.rollDice();
                System.out.println("\n\n" + finalPlayerArray[i].getPlayerName() + " you rolled " + currentRoll + " (" + dice + ")");
                System.out.println();
                switch (currentRoll) {
                    //Rolled a 3
                    case 3:
                        //PLANT 1 TREE AND 1 FLOWER
                        System.out.println("You must plant a tree (2x2) and a flower (1x1)");
                        System.out.println(finalPlayerArray[i].showGarden());
                        System.out.println("Let us start with the tree. You have " + finalPlayerArray[i].howManyTreesPossible() + " places to do this action.");
                        //Planting a tree
                        while (true) {
                            if (finalPlayerArray[i].howManyTreesPossible() == 0) {
                                System.out.println("**Sorry but there is no more room for a tree");
                                break;
                            }
                            System.out.println("Enter coordinates as row column: ");
                            row = input.nextInt();
                            column = input.nextInt();
                            junk = input.nextLine();
                            if ((row >= gardenSize) || (column >= gardenSize) || (row + 1 >= gardenSize) || (column + 1 >= gardenSize)) {
                                System.out.println("**Sorry either coordinates are outside the grid or the tree will go beyond the garden\nEnter new coordinates:");

                            } else if ((finalPlayerArray[i].whatIsPlanted(row, column) != '-') || (finalPlayerArray[i].whatIsPlanted(row + 1, column) != '-') || (finalPlayerArray[i].whatIsPlanted(row, column + 1) != '-') || (finalPlayerArray[i].whatIsPlanted(row + 1, column + 1) != '-')) {
                                System.out.println("**Sorry but that location is currently occupied, enter valid coordinates:");

                            } else {
                                finalPlayerArray[i].plantTreeInGarden(row, column);
                                break;
                            }
                        }
                        System.out.println("You must still plant a flower.");
                        System.out.println(finalPlayerArray[i].showGarden());
                        System.out.println("You have " + finalPlayerArray[i].howManyFlowersPossible() + " spots available.\nEnter coordinates as row column: ");
                        while (true) {
                            row = input.nextInt();
                            column = input.nextInt();
                            junk = input.nextLine();
                            if ((row >= gardenSize) || (column >= gardenSize))
                                System.out.println("**Sorry, but the coordinates entered are out of bounds\nEnter new coordinates:");
                            else if ((finalPlayerArray[i].whatIsPlanted(row, column) != '-'))
                                System.out.println("**Sorry but that location is currently occupied\nEnter new coordinates: ");
                            else {
                                finalPlayerArray[i].plantFlowerInGarden(row, column);
                                break;
                            }
                        }
                        break;

                    //Rolled a 6
                    case 6:
                        //PLANT 2 FLOWER
                        System.out.println("You must plant 2 flowers (1x1)");
                        System.out.println("You have " + finalPlayerArray[i].howManyFlowersPossible() + " spots to perform this action");
                        System.out.println(finalPlayerArray[i].showGarden());
                        //Only spots for 1 flower
                        if (finalPlayerArray[i].howManyFlowersPossible() == 1) {
                            System.out.println("You have only 1 spot left for a flower, please enter it's coordinates as row column: ");
                            while (true) {
                                row = input.nextInt();
                                column = input.nextInt();
                                junk = input.nextLine();
                                if (finalPlayerArray[i].whatIsPlanted(row, column) == '-') {
                                    finalPlayerArray[i].plantFlowerInGarden(row, column);
                                    break;
                                } else
                                    System.out.println("**Sorry, please enter the ONLY valid coordinates for a new flower");
                            }
                        }
                        //No spots left
                        else if (finalPlayerArray[i].howManyFlowersPossible() == 0) {
                            System.out.println("**Sorry, there is not more spaces for flowers. TURN SKIPPED!");
                            break;
                        }
                        //Have space for 2 flowers
                        else {
                            //Panting first flower
                            System.out.print("Enter the location of the first flower as row column: ");
                            while (true) {
                                row = input.nextInt();
                                column = input.nextInt();
                                junk = input.nextLine();
                                if ((row >= gardenSize) || (column >= gardenSize))
                                    System.out.println("**Sorry, but the coordinates entered are out of bounds\nEnter new coordinates: ");
                                else if ((finalPlayerArray[i].whatIsPlanted(row, column) != '-'))
                                    System.out.println("**Sorry but that location is currently occupied\nEnter new coordinates:");
                                else {
                                    finalPlayerArray[i].plantFlowerInGarden(row, column);
                                    break;
                                }

                            }
                            //Planting second flower
                            System.out.print("Enter the location of the second flower as row column: ");
                            while (true) {
                                row = input.nextInt();
                                column = input.nextInt();
                                junk = input.nextLine();
                                if ((row >= gardenSize) || (column >= gardenSize))
                                    System.out.println("**Sorry, but the coordinates entered are out of bounds\nEnter new coordinates: ");
                                else if ((finalPlayerArray[i].whatIsPlanted(row, column) != '-'))
                                    System.out.println("**Sorry but that location is currently occupied\nEnter new coordinates: ");
                                else {
                                    finalPlayerArray[i].plantFlowerInGarden(row, column);
                                    break;
                                }
                            }
                        }
                        break;
                    //Rolled a 12
                    case 12:
                        //PLANT 2 TREES
                        System.out.println("You must plant 2 trees (2x2)");
                        System.out.println("You have " + finalPlayerArray[i].howManyTreesPossible() + " spots to perform this action");
                        System.out.println(finalPlayerArray[i].showGarden());
                        //Only spots for 1 tree
                        if (finalPlayerArray[i].howManyTreesPossible() == 1) {
                            System.out.println("You have only 1 spot left for a tree, please enter it's coordinates as row column: ");
                            while (true) {
                                row = input.nextInt();
                                column = input.nextInt();
                                junk = input.nextLine();
                                if ((finalPlayerArray[i].whatIsPlanted(row, column) == '-') && (finalPlayerArray[i].whatIsPlanted(row + 1, column) == '-') && (finalPlayerArray[i].whatIsPlanted(row, column + 1) == '-') && (finalPlayerArray[i].whatIsPlanted(row + 1, column + 1) == '-')) {
                                    finalPlayerArray[i].plantTreeInGarden(row, column);
                                    break;
                                } else
                                    System.out.println("**Sorry, please enter the ONLY valid coordinates for a new tree");
                            }
                            //No spots at all
                        } else if (finalPlayerArray[i].howManyTreesPossible() == 0) {
                            System.out.println("**Sorry, there is no more spaces for trees. TURN SKIPPED!");
                            break;
                        } else {
                            //CAN PLANT MORE THAN 1 TREE
                            //Planting first tree
                            System.out.print("Enter the location of the first tree as row column: ");
                            while (true) {
                                row = input.nextInt();
                                column = input.nextInt();
                                junk = input.nextLine();
                                if ((row >= gardenSize) || (column >= gardenSize) || (row + 1 >= gardenSize) || (column + 1 >= gardenSize)) {
                                    System.out.println("**Sorry either coordinates are outside the grid or the tree will go beyond the garden\nEnter new coordinates: ");

                                } else if ((finalPlayerArray[i].whatIsPlanted(row, column) != '-') || (finalPlayerArray[i].whatIsPlanted(row + 1, column) != '-') || (finalPlayerArray[i].whatIsPlanted(row, column + 1) != '-') || (finalPlayerArray[i].whatIsPlanted(row + 1, column + 1) != '-')) {
                                    System.out.println("**Sorry but that location is currently occupied, enter valid coordinates: ");

                                } else {
                                    finalPlayerArray[i].plantTreeInGarden(row, column);
                                    break;
                                }
                            }
                            if (finalPlayerArray[i].howManyTreesPossible() == 0) {
                                System.out.println("There is no more place to plant trees, so the turn is now over");
                                break;
                            }
                            //Planting second tree
                            System.out.print("Enter the location of the second tree as row column: ");
                            while (true) {
                                row = input.nextInt();
                                column = input.nextInt();
                                junk = input.nextLine();
                                if ((row >= gardenSize) || (column >= gardenSize) || (row + 1 >= gardenSize) || (column + 1 >= gardenSize)) {
                                    System.out.println("**Sorry either coordinates are outside the grid or the tree will go beyond the garden\nEnter new coordinates: ");

                                } else if ((finalPlayerArray[i].whatIsPlanted(row, column) != '-') || (finalPlayerArray[i].whatIsPlanted(row + 1, column) != '-') || (finalPlayerArray[i].whatIsPlanted(row, column + 1) != '-') || (finalPlayerArray[i].whatIsPlanted(row + 1, column + 1) != '-')) {
                                    System.out.println("**Sorry but that location is currently occupied, enter valid coordinates:");

                                } else {
                                    finalPlayerArray[i].plantTreeInGarden(row, column);
                                    break;
                                }
                            }
                        }
                        break;

                    //Rolled a 10 or a 5
                    case 10:
                    case 5:
                        //The rabbit comes out and eats something
                        System.out.println(finalPlayerArray[i].showGarden());
                        System.out.println("OH NO, THE RABBIT HAS GONE LOOSE");
                        //If garden is empty then it does nothing and the turn is skipped
                        if (finalPlayerArray[i].isGardenEmpty()) {
                            System.out.println("The garden is empty, so the rabbit goes back to sleep. (zzz)");
                            break;
                        } else {
                            //Rabbit eats something out of the garden
                            //Loop to keep choosing a spot until the rabbit actually picks a spot where something is planted
                            while (true) {
                                row = rand.nextInt(gardenSize);
                                column = rand.nextInt(gardenSize);
                                junk = input.nextLine();
                                if (finalPlayerArray[i].whatIsPlanted(row, column) == '-')
                                    continue;
                                else
                                    break;
                            }
                            System.out.println("The rabbit ate whatever was planted at (" + column + "," + row + ")");
                            finalPlayerArray[i].eatHere(row, column);
                            System.out.println(finalPlayerArray[i].showGarden());
                            break;
                        }


                    default:
                        //All other even numbers
                        //Plants 1 tree
                        if (currentRoll % 2 == 0) {
                            System.out.println("You must plant a tree (2x2)");
                            System.out.println(finalPlayerArray[i].showGarden());
                            System.out.println("and have " + finalPlayerArray[i].howManyTreesPossible() + " spots to perform this action");
                            //No more room
                            if (finalPlayerArray[i].howManyTreesPossible() == 0) {
                                System.out.println("**Sorry you have no room left to plant a tree - TURN SKIPPED!");
                                break;
                            }
                            System.out.println("Enter coordinates as row column: ");
                            while (true) {
                                row = input.nextInt();
                                column = input.nextInt();
                                junk = input.nextLine();
                                if ((row >= gardenSize) || (column >= gardenSize) || (row + 1 >= gardenSize) || (column + 1 >= gardenSize)) {
                                    System.out.println("**Sorry either coordinates are outside the grid or the tree will go beyond the garden\nEnter new coordinates: ");

                                } else if ((finalPlayerArray[i].whatIsPlanted(row, column) != '-') || (finalPlayerArray[i].whatIsPlanted(row + 1, column) != '-') || (finalPlayerArray[i].whatIsPlanted(row, column + 1) != '-') || (finalPlayerArray[i].whatIsPlanted(row + 1, column + 1) != '-')) {
                                    System.out.println("**Sorry but that location is currently occupied");
                                } else {
                                    finalPlayerArray[i].plantTreeInGarden(row, column);
                                    break;
                                }
                            }

                        }
                        //All other odd numbers
                        //Plants 1 flower
                        else if (currentRoll % 2 == 1) {
                            System.out.println("You must plant a flower (1x1)");
                            System.out.println(finalPlayerArray[i].showGarden());
                            System.out.println("You have " + finalPlayerArray[i].howManyFlowersPossible() + " spots to perform this action");
                            System.out.println("Enter coordinates as row column: ");
                            while (true) {
                                row = input.nextInt();
                                column = input.nextInt();
                                junk = input.nextLine();
                                if ((row > gardenSize) || (column > gardenSize))
                                    System.out.println("**Sorry but the coordinates you gave are not in the garden");
                                else if (finalPlayerArray[i].whatIsPlanted(row, column) != '-')
                                    System.out.println("**Sorry but the coordinates given are occupied by a " + finalPlayerArray[i].whatIsPlanted(row, column));
                                else {
                                    finalPlayerArray[i].plantFlowerInGarden(row, column);
                                    break;
                                }
                                System.out.println("Enter a new set of coordinates");
                            }
                        }
                }

                //After each player plays, checks if they won
                if (finalPlayerArray[i].isGardenFull()) {
                    winner = finalPlayerArray[i].getPlayerName();
                    break;
                }
            }


            //Sets main game loop's condition to true to kill the game loop
            if (!winner.isEmpty())
                winnerIsChosen = true;
            System.out.println("\n");
        }
        while (!winnerIsChosen);

        //Final results of the game
        System.out.println("FINAL RESULTS!");
        System.out.println("-------------------");
        System.out.println("Here are how the gardens look after " + numberOfTurns + " turns.");
        //Loop to print out and show everyone's garden
        for (int i = 0; i < playerNumber; i++) {
            System.out.println(finalPlayerArray[i].getPlayerName() + "'s garden:");
            System.out.println(finalPlayerArray[i].showGarden());
        }

        //The game is over, closing Scanner and displaying winner
        input.close();
        System.out.println("\n\nAnd the winner is ........ " + winner);
        System.out.println("This is a beautiful garden you have");
        System.out.println();

        //Closing message
        System.out.println("GGWP everyone, good game and well played");
        System.out.println("The game is over, have a great rest of your day");

    }

}


// ---------------------------------------------------------------
// Assignment 2
// Written by: Anik Patel - 40091908
// For COMP 248 Section Q - Fall 2018
// ---------------------------------------------------------------

/*
 * This program take a number from the user, of at most 7 digits, and returns to the user the sum of all the digits composing the number
 * and all the integer divisors for resulting sum. Afterward, it prompts the user to see if they want to repeat with another number.
 */

import java.util.Scanner;

public class Question_2 {



    public static void main (String[] args)
    {

        Scanner input = new Scanner(System.in);

        //Welcome message
        System.out.println("                Welcome to The Calculator!                ");
        System.out.println("----------------------------------------------------------");

        String condition = "yes";
        while (condition.equalsIgnoreCase("yes"))
        {
            //Prompts for first input
            System.out.print("\nPlease input a number with at most 7 digits: ");
            int number = input.nextInt();
            //Method to convert the Integer input into a String
            String numberString = Integer.toString(number);

            //While loop to continuously ask the user for a new input if the number is more than 7 digits
            while (numberString.length() > 7)
            {
                System.out.println("ERROR, VALUE TOO BIG");
                System.out.print("\nPlease input a number with at most 7 digits: ");
                number = input.nextInt();
                numberString = Integer.toString(number);
            }

            //Calculates the sum of the digits of the input

            //The remainder of any number divided by 10 will give the first digit on the right side and the integer division by 10 will get rid of it since Java
            //always rounds down when a integer division is done
            int sum = 0;
            int numberTemp = number;
            while (numberTemp != 0)
            {
                sum += numberTemp % 10;
                numberTemp /= 10;
            }

            System.out.println("\nThe sum of the digits of " + number + " is: " + sum + "\n");



            //Finds and prints all the divisors of the input

            System.out.println("These are the numbers that can divise " + sum + ":");

            //Tries dividing the sum by every number between 1 and it. If the modulo is 0, then said number is a divisor
            // Uses "divisor <= sum" so that the sum itself is eventually used, since every number is divisible by itself
            for (int divisor = 1;divisor <= sum;++divisor)

                if ((sum % divisor) == 0)
                    System.out.print(divisor + " ");



            //Prompts user to repeat loop, repeats the while loop if yes is entered and loop is broken if no is entered
            while (!(condition.equalsIgnoreCase("no")))
            {

                System.out.println("\n\nWould you like to repeat this with another number? (enter yes if you do, no if you want to stop)");
                condition = input.next();

                //Checks the input of the user, will break out of this loop if yes or no is entered and will keep asking the question otherwise
                if (condition.equalsIgnoreCase("no"))
                    break;
                else if (!(condition.equalsIgnoreCase("yes")))
                    System.out.println("ERROR, ENTER A VALID OPTION!");
                else
                    break;

            }


        }



        //Closing message
        System.out.println("\n\nThank you for using The Calculator.\nHave a great day!");

        input.close();

    }
}


// ---------------------------------------------------------------
// Assignment 2
// Written by: Anik Patel - 40091908
// For COMP 248 Section Q - Fall 2018
// ---------------------------------------------------------------

/*
 * This program is will ask the user for scores gotten in the
 * IELTS test and will figure out if the user is eligible or
 * not to apply for Concordia University (in this case)
 */

import java.util.Scanner;

public class Question_1 {

    public static void main (String[] args)
    {
        //Initializes user input
        Scanner input = new Scanner(System.in);
        //Initializes variables used in the program
        double listen, speak, read, write, overall = 0, temp;

        //Welcome message with initial user choice
        System.out.println(" ----------------------------------------------------------------------------------------------- ");
        System.out.println("Welcome to the Language Proficiency Evaluator for Concordia University, based on the IELTS exam");
        System.out.println("                                                                            coded by Anik Patel");
        System.out.println(" ----------------------------------------------------------------------------------------------- ");
        System.out.println("\n\n");

        //While loop used to keep asking for an input from the user until the program is given a valid input,
        //will keep asking the user for an input if one of the valid options are not chosen
        while (overall==0)
        {


            System.out.println("These are the possible actions for me to accomplish:\n");
            System.out.println("              1 - Language Proficiency Requirements");
            System.out.println("              2 - \"What is my eligibility?\"");
            System.out.print("\nPlease enter the number of what you would like to do: ");
            int choice = input.nextInt();



            switch(choice)
            {


                case 1:
                    //Gives explanation of what is needed as requirements
                    System.out.println("\nOverall score needs to be equal or higher than 6.5 and the writing and reading scores should be at least 6.0 to be accepted.\nIf overall score is 6.0 and reading and writing scores are at least 6.0, you will be accepted conditionally, meaning you will have to take an English course.\nIf either the overall score or either the reading or writing scores are below 6.0, you have failed and must retake the exam.\n\n");

                    overall = 1;
                    break;


                case 2:
                    //Gets the user input for each score and calculates if the user is accepted or not

                    System.out.println("Please input your listening score: ");
                    listen = input.nextDouble();
                    System.out.println("Please input your speaking score: ");
                    speak = input.nextDouble();
                    System.out.println("Please input your reading score: ");
                    read = input.nextDouble();
                    System.out.println("Please input your writing score: ");
                    write = input.nextDouble();
                    //Overall score is the average of all the scores
                    overall = (listen+speak+read+write)/4;
                    temp = overall - (int)overall;


                    //Rounds up or down the overall score to the closest 0.5 multiple
                    if (temp < 0.25)
                        overall = (int)overall;
                    else if (temp < 0.75)
                        overall = (int)overall+0.5;
                    else
                        overall = (int)overall+1;


                    //Did you succeed or fail?
                    System.out.println("\n\nAlright, this is what we got for your scores: \n");
                    System.out.println("	Your Overall Score was calculated to: " + overall);
                    System.out.println("	Your Reading Score, given, was:       " + read);
                    System.out.println("	Your Writing Score, given, was:       " + write);
                    System.out.println("\n");

                    if (!(overall < 6.5))
                    {
                        if ((read < 6.0)&&(write < 6.0))
                        {
                            System.out.println("You are eligible for Conditional Acceptance, thus you must take an extra English course");
                            break;
                        }

                        else
                        {
                            System.out.println("CONGRATULATIONS!!! You are eligible for Admission. Welcome to Concordia!");
                            break;
                        }

                    }
                    else
                    {
                        if ((overall == 6.0)&&(!(read< 6.0)&&!(write< 6.0)))
                        {
                            System.out.println("You are eligible for Conditional Acceptance, thus you must take an extra English course");
                            break;
                        }

                        else
                        {
                            System.out.println("I am sorry, but you are not eligible to be admitted to Concordia. You must retake the exam in order to get another chance.");
                            break;
                        }

                    }


                default:
                    //If neither 1, nor 2 is input, the program lets the user know their mistake and asks them again for a valid input
                    System.out.println("\nINVALID INPUT!!!");
                    System.out.println("Please enter one of the 2 valid values!\n\n");

            }


        }
        input.close();
        System.out.println("\nThank you for using the Language Proficiency Evaluator. Have a great day!");
    }

}

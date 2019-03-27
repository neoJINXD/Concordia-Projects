// ----------------------------------------------------
// Assignment 4
// Question: 1 - ArrayList & File I/O
// Written by: Anik Patel - 40091908
// -----------------------------------------------------

import java.util.ArrayList;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Parser {
    public static void main(String[] args) {
        boolean success = true;
        //Setting up all the ArrayLists needed
        ArrayList<String> read = new ArrayList<>();
        ArrayList<String> dictionary = new ArrayList<>();

        //Setting up Scanners for the inputs and the PrintWriter for the output
        Scanner input = new Scanner(System.in);
        Scanner reader = null;
        PrintWriter writer = null;

        System.out.println("Welcome to the Text Parser!\n");
        //Getting file name from the user
        System.out.print("Please enter the name of the file that you want to use: ");
        String file = input.nextLine();
        //String file = "PersonOfTheCentury.txt";
        input.close();

        //Try-Catch to get info from input file, Reading with windows-1252 character set otherwise the apostrophes appear as invalid
        try {
            System.out.println("Time to read the chosen file...");
            reader = new Scanner(new FileInputStream(file), "windows-1252");
            while (reader.hasNext()) {
                String temp = reader.next();
                temp = temp.toUpperCase();
                //Removing the apostrophes
                if (temp.contains("’") || temp.contains("'"))
                    read.add(temp.substring(0, temp.length() - 2));
                    //Removing all punctuation
                else if (temp.contains(";") || temp.contains(":") || temp.contains(".") || temp.contains("!") || temp.contains("?") || temp.contains(","))
                    read.add(temp.substring(0, temp.length() - 1));
                    //Ignoring =
                else if (temp.contains("="))
                    continue;
                else
                    read.add(temp);
            }
            System.out.println("File has been successfully read and the info is stored!");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            success = false;
        } finally {
            reader.close();
        }

        //Adds everything that was read from the file into the final ArrayList ignoring anything that contains numbers, words that were already added
        // and ignoring single characters if they are not words
        for (String i : read) {
            if (!(i.matches(".*[0-9]+.*") || dictionary.contains(i)) && i.length() > 1)
                dictionary.add(i);
            else {
                if ((i.matches("A") || i.matches("I") || i.matches("O")) && !(dictionary.contains(i)))
                    dictionary.add(i);
            }
        }

        //Sorts the ArrayList alphabetically
        dictionary.sort(null);

        //Length of the ArrayList
        int len = dictionary.toArray().length;

        //Try-Catch to write the file a specific format
        try {
            System.out.println("Time to separate all the words and print to output...");
            writer = new PrintWriter(new FileOutputStream("SubDictionary.txt"));
            writer.println("The document produced this sub-dictionary, which includes " + len + " entries.\n");
            //Initial value, different type to the one in the for loop
            int i = 0;
            for (char c = 'A'; c <= 'Z'; c++) {
                //Check to skip letters that have no words
                if (dictionary.get(i).charAt(0) != c)
                    continue;
                writer.println(c);
                writer.println("==");
                while (dictionary.get(i).charAt(0) == c) {
                    writer.println(dictionary.get(i));
                    ++i;
                    //Stops iterating after the last value
                    if (i == len)
                        break;
                }
                writer.println();
            }
            System.out.println("Output file has been successfully created!");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            success = false;
        } finally {
            writer.close();
        }
        //Closing message for the program
        if (success)
            System.out.println("\nThe program ran successfully and the proper output file was created!\nThanks you for using the program!");
        else
            System.out.println("\nThe program failed to run!!!");

    }
}




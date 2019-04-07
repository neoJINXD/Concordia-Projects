// ----------------------------------------------------
// Assignment 4
// Question: 2 - Singly Linked Lists
// Written by: Anik Patel - 40091908
// -----------------------------------------------------


import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.ArrayList;

public class CellListUtilization {
    public static void main(String[] args) {
        System.out.println("Welcome to the test run for the CellList Linked List system!\n");

        ArrayList<Cellphone> ting = new ArrayList<>();
        Scanner reader = null;
        String read = "";
        try {
            reader = new Scanner(new FileInputStream("Cell_Info.txt"));

            while (reader.hasNextLine()) {
                read += reader.next();
                read += " ";
            }
        } catch (FileNotFoundException e) {
            System.out.println("File containing the information for the cellphone could not be found");
        }

        String[] split = read.split("\\s");

        for (int i = 0; i < split.length; i += 4) {
            long serial = Long.parseLong(split[i]);
            double price = Double.parseDouble(split[i + 2]);
            int year = Integer.parseInt(split[i + 3]);
            Cellphone cell = new Cellphone(serial, split[i + 1], year, price);
            ting.add(cell);
        }
        CellList list1 = new CellList();
        CellList list2;

        for (int i = 0; i < ting.toArray().length; i++) {
            if (list1.contains(ting.get(i).getSerialNum()))
                continue;
            list1.addToStart(ting.get(i));
            //System.out.println("added");
        }

        try {
            assert list1.getSize() == 23 : "Added too many or too little Cellphones to Linked List, Check duplicate checks";

            System.out.println("Original starting Linked List...");
            list1.showContents();
            list2 = new CellList(list1);
            assert list1.equals(list2) : "Either the copy constructor failed to copy everything or the equals method failed";

            list1.insertAtIndex(new Cellphone(69696969, "Google", 2016, 100.42), 14);
            assert list1.contains(69696969) && list1.getSize() == 24 : "InsertAtIndex failed!";

            list1.addToStart(new Cellphone(115115, "LG", 2012, 399.55));
            assert list1.contains(115115) && list1.getSize() == 25 : "AddToStart failed!";

            list1.deleteFromIndex(1);
            assert !list1.contains(1119000) && list1.getSize() == 24 : "DeleteFromIndex failed!";

            list1.deleteFromIndex(2);
            assert !list1.contains(5909887) && list1.getSize() == 23 : "InsertAtIndex failed!";

            list1.replaceAtIndex(new Cellphone(9876543, "BananaPhone", 2025, 469.95), list1.getSize() - 1);
            assert list1.contains(9876543) && list1.getSize() == 23 : "ReplaceAtIndex failed to add new Cellphone!";
            assert !list1.contains(3890909) && list1.getSize() == 23 : "ReplaceAtIndex failed to remove previous Cellphone at index";

            System.out.println("To find the Cellphone with serial number \"69696969\"...");
            System.out.println(list1.find(69696969));

            System.out.println("\nFinal Linked lists after every test...");
            list1.showContents();

        } catch (AssertionError e) {
            System.out.println("Failed the assert test");
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\nThe tests have ended.");

        System.out.println("Please enter a few serial number that you wish to look for, enter -1 to stop:");


        //User input to search through the linked list for specific serial numbers
        Scanner input = new Scanner(System.in);
        ArrayList<String> serials = new ArrayList<>();
        while (true) {
            String in = input.next();
            serials.add(in);
            if (Long.parseLong(in) < 0)
                break;
        }
        for (String i : serials) {
            Long serial = Long.parseLong(i);
            System.out.println("Looking for serial number : " + serial + "...");
            System.out.println("Found at --> " + list1.find(serial));
            System.out.println();
        }


        System.out.println("\nProgram has terminated. Thank you for using this program!\nHave a nice day!");
    }
}

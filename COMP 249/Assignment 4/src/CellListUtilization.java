// ----------------------------------------------------
// Assignment 4
// Question: 2 - Singly Linked Lists
// Written by: Anik Patel - 40091908
// -----------------------------------------------------

import java.io.*;
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
        ArrayList<Cellphone> added = new ArrayList<>();
        CellList list1 = new CellList();
        CellList list2;
//        for (Cellphone i : ting)
//            System.out.println(i);

        for (int i = 0; i < ting.toArray().length; i++) {
            if (list1.contains(ting.get(i).getSerialNum()))
                continue;
            list1.addToStart(ting.get(i));
//            System.out.println("added");
        }

        try {
            assert list1.getSize() == 23 : "Added too many or too little Cellphones to Linked List, Check duplicate checks";

            list1.showContents();
            list2 = new CellList(list1);
            list2.showContents();
            //System.out.println(list2.getSize());
        } catch (AssertionError e){
            System.out.println(e.getMessage());
        }

        System.out.println("\nThe tests have ended, thank you for using this program!\n Have a nice day!");
    }
}

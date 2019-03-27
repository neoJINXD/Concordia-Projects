import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

public class CellListUtilization {
    public static void main(String[] args) {
        Random rand = new Random();
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
//        for(String i : split)
//            System.out.println(i);
        //System.out.println(read);

        for (int i = 0; i < split.length; i += 4) {
            long serial = Long.parseLong(split[i]);
            double price = Double.parseDouble(split[i + 2]);
            int year = Integer.parseInt(split[i + 3]);
            Cellphone cell = new Cellphone(serial, split[i + 1], year, price);
            ting.add(cell);
        }
        ArrayList<Cellphone> added = new ArrayList<>();
        CellList list1 = new CellList();
        CellList list2 = new CellList();
//        int addedIterator = 0;
        for (Cellphone i : ting)
            System.out.println(i);


        //FIX THIS SHIT NOTHING IS GETTING ADDED
        for (int i = 0; i < ting.toArray().length; i++) {

            //System.out.println(i);
//                if (added.isEmpty()) {
//                    list1.addToStart(ting.get(i));
//                    added.add(ting.get(i));
//                    continue;
//                }

            list1.addToStart(ting.get(i));
            //added.add(ting.get(i));
            System.out.println("added");


        }
        //list1.addToStart(ting.get(0));
        //list1.addToStart(ting.get(1));
        //list1.addToStart(ting.get(2));
        //list1.addToStart(ting.get(3));
        //list1.addToStart(ting.get(4));
        list1.showContents();



    }
}

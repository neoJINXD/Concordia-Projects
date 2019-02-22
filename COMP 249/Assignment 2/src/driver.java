//--------------------------------------------------------
//  Assignment 2
//  Part I
//  Written by: Anik Patel - 40091908
//--------------------------------------------------------

import Air.AirCraft;
import Land.CityBus;
import Land.Metro;
import Land.Tram;
import Water.Ferry;
import publicTransport.PublicTransportation;

public class driver {
    /**
     * Method to copy all the CityBus objects from a given array into another new array
     * @param array Input array that contains all sorts of PublicTransportation objects
     * @return New array conataining all the CitBus objects
     */
    public static PublicTransportation[] copyCityBus(PublicTransportation[] array) {
        PublicTransportation[] output = new PublicTransportation[array.length];
        CityBus compared = new CityBus();
        CityBus added;
        for (int i = 0, location = 0; i < array.length; i++) {
            if (array[i].getClass() == compared.getClass()) {
                //Casting the element from the PublicTransport array into a CityBus object
                //Will not work otherwise since the input array contains PublicTransports
                added = new CityBus((CityBus) array[i]);
                //Adds to the output array
                output[location] = added;
                //Increments the output array
                location++;
            }
        }
        return output;
    }

    public static void main(String[] args) {

        System.out.println("Starting Program");

        //Creating objects
        PublicTransportation pt1 = new PublicTransportation(70, 100);

        CityBus cb1 = new CityBus(11.15, 2, 4020, 2018, "MTL-TORONTO", "Johnny Babbish");
        CityBus cb2 = new CityBus(3.25, 28, 491, 2005, "EXPRESS Downtown", "Bob Marley");
        CityBus cb3 = new CityBus(16, 7, 12345, 2020, "Accross the Globe", "Ivan Azimov");
        CityBus cb4 = new CityBus(0, 1, 0713, 2014, "BattleBus", "Epic Games");
        CityBus cb5 = new CityBus(100, 100, 9999, 0, "Bus of Life", "Bing Bang");

        Tram t1 = new Tram(1, 10, 54443, 1000, "Morning Accross Town", "Chase Borne", 10000);
        Tram t2 = new Tram(5, 7, 69, 1960, "Tourist", "Jasmin Galvan", 100);

        Metro m1 = new Metro(3.25, 15, 6969, 1973, "GREEN LINE", "Izayah Harrington", 10, "Montreal");

        Ferry f1 = new Ferry(15, 2, 1800, "S.S.Anne");
        Ferry f2 = new Ferry(f1);

        AirCraft ac1 = new AirCraft(1000000, 1, AirCraft.Class.Helicopter, AirCraft.Maintenance.Monthly);

        //Setting up array
        PublicTransportation[] array = {pt1, cb1, cb2, cb3, cb4, cb5, t1, t2, m1, f1, f2, ac1};

        //Activating copyCityBus method
        PublicTransportation[] copied = copyCityBus(array);

        //Printing original array
        System.out.println("Now printing all the created objects\n");
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
        System.out.println();
        //Printing copyCityBus'ed array
        for (int i = 0; i < copied.length; i++) {
            if (copied[i] == null)
                continue;
            else
                System.out.println(copied[i]);
        }

        System.out.println();
        System.out.println("The program is now done, Have a great day!");
    }

}

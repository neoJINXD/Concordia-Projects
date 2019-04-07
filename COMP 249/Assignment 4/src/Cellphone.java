// ----------------------------------------------------
// Assignment 4
// Question: 2 - Linked Lists - File 1
// Written by: Anik Patel - 40091908
// -----------------------------------------------------

import java.util.Scanner;

/**
 * Object that will contain all the information for every individual Cellphones
 * @author  Anik Patel - 40091908
 * @version Part II
 */
public class Cellphone {
    private long serialNum;
    private String brand;
    private int year;
    private double price;

    /**
     * Parametrized constructor
     * @param serialNum Individual serial number of type long unique to the phone
     * @param brand String of the company that made it
     * @param year Int of year of manufacturing
     * @param price Double of the cost to buy it
     */
    public Cellphone(long serialNum, String brand, int year, double price){
        this.serialNum = serialNum;
        this.brand = brand;
        this.year = year;
        this.price = price;
    }

    /**
     * Copy constructor that performs a deep copy
     * @param cell Cellphone object to copy from
     * @param value New unique long for the serial number
     */
    public Cellphone(Cellphone cell, long value){
        Cellphone newPhone = new Cellphone(value, cell.getBrand(), cell.getYear(), cell.getPrice());
        this.serialNum = newPhone.getSerialNum();
        this.brand = newPhone.getBrand();
        this.year = newPhone.getYear();
        this.price = newPhone.getPrice();
    }

    /**
     * Clone method, Asks the user for a new serial number of type long
     * @return a deep copy of Cellphone
     */
    public Cellphone clone(){
        Scanner input = new Scanner(System.in);
        System.out.println("Please input a nwe serial number: ");
        int value = input.nextInt();
        input.close();
        return new Cellphone(this, value);
    }

    /**
     * Method to check equality of 2 Cellphones, ignoring serial numbers that are unique
     * @param o Object to compare to
     * @return boolean, true if all the values other than the serial number are the same
     */
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Cellphone))
            return false;
        Cellphone cellphone = (Cellphone) o;
        return (this.year==cellphone.year)&&(this.price==cellphone.price)&&(this.brand.equals(cellphone.brand));
    }

    /**
     * toString method to print the object
     * @return String with all the information for each Cellphone
     */
    public String toString() {
        return "Cellphone{" +
                "serialNum = " + serialNum +
                ", brand = '" + brand + '\'' +
                ", year = " + year +
                ", price = " + price +
                '}';
    }

    /**
     * Accessor method for the serial number
     * @return long, the serial number
     */
    public long getSerialNum() {
        return serialNum;
    }

    /**
     * Mutator method for the serial number
     * @param serialNum new long number for the serial number
     */
    public void setSerialNum(long serialNum) {
        this.serialNum = serialNum;
    }

    /**
     * Accessor method for the brand
     * @return String, the company that made the Cellphone
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Mutator method for the brand
     * @param brand new String containing the new brand
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Accessor the method for the year of manufacturing
     * @return Int, the year of manufacturing
     */
    public int getYear() {
        return year;
    }

    /**
     * Mutator method for the year
     * @param year new int for the manufacturing year
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Accessor method for the price
     * @return Double, price of cost for the Cellphone
     */
    public double getPrice() {
        return price;
    }

    /**
     * Mutator method for the price
     * @param price new double for the price of the Cellphone
     */
    public void setPrice(double price) {
        this.price = price;
    }


}

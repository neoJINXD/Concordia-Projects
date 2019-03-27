// ----------------------------------------------------
// Assignment 4
// Question: 2 - Linked Lists - File 1
// Written by: Anik Patel - 40091908
// -----------------------------------------------------

import java.util.Scanner;

public class Cellphone {
    private long serialNum;
    private String brand;
    private int year;
    private double price;

    public Cellphone(long serialNum, String brand, int year, double price){
        this.serialNum = serialNum;
        this.brand = brand;
        this.year = year;
        this.price = price;
    }

    public Cellphone(Cellphone cell, long value){
        Cellphone newPhone = new Cellphone(value, cell.getBrand(), cell.getYear(), cell.getPrice());
        this.serialNum = newPhone.getSerialNum();
        this.brand = newPhone.getBrand();
        this.year = newPhone.getYear();
        this.price = newPhone.getPrice();
    }

    public Cellphone clone(){
        Scanner input = new Scanner(System.in);
        System.out.println("Please input a nwe serial number: ");
        int value = input.nextInt();
        return new Cellphone(this, value);
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Cellphone))
            return false;
        Cellphone cellphone = (Cellphone) o;
        return (this.year==cellphone.year)&&(this.price==cellphone.price)&&(this.brand.equals(cellphone.brand));
    }

    public String toString() {
        return "Cellphone{" +
                "serialNum = " + serialNum +
                ", brand = '" + brand + '\'' +
                ", year = " + year +
                ", price = " + price +
                '}';
    }

    public long getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(long serialNum) {
        this.serialNum = serialNum;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


}

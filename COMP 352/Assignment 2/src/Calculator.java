/*
Name:   Anik Patel
ID:     40091908
COMP 352 - Assignment 2
Due Date: May 29th, 2019
*/

import java.io.FileInputStream;
import java.util.Scanner;

public class Calculator{
    private static ArrayStack<Integer> values = new ArrayStack<>();
    private static ArrayStack<String> operations = new ArrayStack<>();
    private static ArrayStack<Integer> st = new ArrayStack<>();

    public static String calculate(int first, int second, String op){
        String result = "";
        if (op.equals("^"))
            result += MyMath.power(first, second);
        else if (op.equals("*"))
            result += MyMath.multiplication(first, second);
        else if (op.equals("/"))
            result += MyMath.division(first, second);
        else if (op.equals("+"))
            result += MyMath.addition(first, second);
        else if (op.equals("-"))
            result += MyMath.substraction(first, second);
        else if (op.equals("<"))
            result += MyMath.lessThan(first, second);
        else if (op.equals(">"))
            result += MyMath.moreThan(first, second);
        else if (op.equals("<="))
            result += MyMath.lessThanOrEqual(first, second);
        else if (op.equals(">="))
            result += MyMath.moreThanOrEqual(first, second);
        else if (op.equals("=="))
            result += MyMath.Equals(first, second);
        else if (op.equals("!="))
            result += MyMath.notEquals(first, second);
        else{
            System.out.printf("Error, operator not valid\nExitting...\n");
            System.exit(-1);
        }
        return result;
    }
    public static void main(String[] args){
        
        System.out.println(calculate(4, 4, "<="));
        
    }
}
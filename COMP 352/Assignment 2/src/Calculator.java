/*
Name:   Anik Patel
ID:     40091908
COMP 352 - Assignment 2
Due Date: May 29th, 2019
*/

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

public class Calculator{
    // Global variables
    private static ArrayStack<Double> values = new ArrayStack<>();
    private static ArrayStack<String> operations = new ArrayStack<>();
    private static final int TRUE = 1111111111;
    private static final int FALSE = 1000000000;
    private static final String inFile = "equations.txt";
    private static final String outFile = "out.txt";

    /** Local function to get the Precedence level of the operator
     * 
     * @param op Operator to check for precedence
     * @return Integer number with the precedence, higher is more important
     */
    private static int getPrecedence(String op){
        if (op.equals("^"))
            return 5;
        else if (op.equals("*") || op.equals("/"))
            return 4;
        else if (op.equals("+") || op.equals("-"))
            return 3;
        else if (op.equals(">") || op.equals("<") || op.equals("<=") || op.equals(">="))
            return 2;
        else if (op.equals("==") || op.equals("!=") || op.equals("!") || op.equals("="))
            return 1;
        return 0;
    }   

    /** Local function to perform the operation given
     * 
     * @param first first number
     * @param second second number
     * @param op operation to perform
     * @return result of the operation
     */
    private static String calculate(double first, double second, String op){
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

    /** Local function to perform an operation from the stack
     * 
     * @param op operator to be passed
     */
    private static void doOp(String op){
        double y = values.pop();
        double x = values.pop();
        if (op.equals("=")) op = operations.pop() + op;

        String result = calculate(x, y, op);
        values.push(Double.parseDouble(result));
    }

    /** Local function to check the precedence of an operator before pushing it to the stack
     * 
     * @param current operator to be checked
     */
    private static void precedenceCheck(String current){
        int insidePrecedence, outsidePrecedence;

        if (current.equals("(")){
            operations.push(current);
            return;
        }
        if (operations.isEmpty()) insidePrecedence = 0;
        else insidePrecedence = getPrecedence(operations.top());

        outsidePrecedence = getPrecedence(current);
                    
        if (insidePrecedence < outsidePrecedence) operations.push(current);
        else { 
            if (current.equals(")") && operations.top().equals("(")){
                    operations.pop();
                    return;
            } else if (current.equals("=") && operations.top().equals("=")){// TODO BROKE
                operations.push(current);
                return;
            } else {
                doOp(operations.pop());
                precedenceCheck(current);
            }
        }
    }

    /** Main Method, has the calculator
     * 
     * @param args
     */
    public static void main(String[] args){
        //ArrayList just to read through the file and hold each line
        ArrayList<String> lineHolder = new ArrayList<>();

        //IO objects
        Scanner file = null;
        PrintWriter pw = null;

        System.out.printf("Welcome, welcome!\nThis is, |THE CALCULATOR|\n");
        System.out.printf("It will calculate all the equations in \"equations.txt\"\n\n");

        try{
            file = new Scanner(new FileInputStream(inFile));
            pw = new PrintWriter(new FileOutputStream(outFile));   
            
            //Reads and stores each line seperately
            while (file.hasNextLine()){
                String temp = file.nextLine();
                lineHolder.add(temp);
                // System.out.println(temp);
            }

            //Iterates through each line
            for (String eq : lineHolder){
                char[] eqChar = eq.toCharArray();
                System.out.printf("\nYOUR NEXT LINE WILL BE: %s\n", Arrays.toString(eqChar));

                String numHolder = ""; //Acts as a Buffer
                //Iterate through each character
                for (char i : eqChar){
                    if (i == ' ') continue;
                    //Converts a character to a String
                    String holder = String.valueOf(i);

                    //Checks if the character is a number
                    if (holder.matches("^\\d+$"))
                        numHolder += holder;
                    else {
                        if (!numHolder.equals("")){
                            values.push(Double.parseDouble(numHolder));
                            numHolder = "";
                        }
                        precedenceCheck(holder);
                    }
                }
                //Pushes the remaining number in the buffer
                if (!numHolder.equals(""))
                    values.push(Double.parseDouble(numHolder));
                //If there are any operations left, perform them
                while (!operations.isEmpty())
                    doOp(operations.pop());

                // Prints out the result of the equations to a file
                double finalResult = values.pop();

                if (finalResult == TRUE)
                    pw.printf("The result of %s\n is: \t%b\n", eq, true);
                else if (finalResult == FALSE)
                    pw.printf("The result of %s\n is: \t%b\n", eq, false);
                else
                    pw.printf("The result of %s\n is: \t%.2f\n", eq, finalResult);
                
                // Printing the remaining values from the Stack, for Debugging purposes
                while (!values.isEmpty()){
                    System.out.printf("ERROR! THE VALUE STACK STILL HAS %.2f\n", values.pop());
                }
                while (!operations.isEmpty()){
                    System.out.printf("ERROR! THE OPERATIONS STACK STILL HAS %s\n", operations.pop());
                }
                pw.printf("\n");
            }
        } catch (FileNotFoundException e){
            System.out.println(e.getMessage());

        } finally {
            //Closing Scanner and PrintWriter
            if (file != null) file.close();
            if (pw != null) pw.close();

            System.out.printf("\nThis is the end of the program\n");
        }
    }
}
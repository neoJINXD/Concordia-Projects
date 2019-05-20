/*
Name:   Anik Patel
ID:     40091908
COMP 352 - Assignment 1
Due Date: May 19th, 2019
*/


import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;

/**
 * Coding parts to the Programming Questions
 * @author Anik Patel - 40091908
 * @version Assignment 1
 */
public class A1Tetranacci {

    // Global Variables to use
    static long exponentialTime;
    static long trTime;
    final static String FILE = "out.txt";

    /** Exponential version of the algorithm for Tetranacci
     * 
     * @param n Looking for the n'th number in the Tetranacci set
     * @return The n'th number in the Tetranacci set
     */
    public static long ExponentialTetranacci(int n) {
        // Base cases
        if (n == 0 || n == 1 || n == 2)
            return 0;
        if (n == 3)
            return 1;
        // Recursive step
        return ExponentialTetranacci(n - 1) + ExponentialTetranacci(n - 2) + ExponentialTetranacci(n - 3)
                + ExponentialTetranacci(n - 4);

        /* Pseudo Code
        ExponentialTetranacci(n):
            INPUT: Integer n
            OUTPUT: The n'th tetranacci number
            START:
                if n = 0 then return 0
                if n = 1 then return 0
                if n = 2 then return 0
                if n = 3 then return 1

                return ExponentialTetranacci(n-1) + ExponentialTetranacci(n-2) + ExponentialTetranacci(n-3) + ExponentialTetranacci(n-4)
            END
        */
    }

    /** Tail Recusive version of the algorithm for Tetranacci
     * 
     * @param n Looking for the n'th number in the Tetranacci set
     * @param base0 Starts as the base result for the first number, becomes the n-4'th result
     * @param base1 Starts as the base result for the second number, becomes the n-3'th result
     * @param base2 Starts as the base result for the third number, becomes the n-2'th result
     * @param base3 Starts as the base result for the fourth number, becomes the n-1'th result
     * @return The n'th number in the Tetranacci set
     */
    public static long TRTetranacci(int n, long base0, long base1, long base2, long base3) {
        // Base cases
        if (n == 0)
            return base0;
        if (n == 1)
            return base1;
        if (n == 2)
            return base2;
        if (n == 3)
            return base3;
        // Recursive step
        return TRTetranacci(n - 1, base1, base2, base3, base3 + base2 + base1 + base0);

        /* Pseudo Code
        TRTetranacci(n, base0, base1, base2, base3):
            INPUT: Integer n and the previoud 4 calculations, starting with the base cases
            OUTPUT: The n'th tetranacci number
            BEGIN:
                if n = 0 then return base0
                if n = 1 then return base1
                if n = 2 then return base2
                if n = 3 then return base3

                TRTetranacci(n - 1, base1, base2, base3, base0 + base1 + base2 + base3)
            END
        */

    }

    /** Method to write the time taken for the used algorithms
     * 
     * @param writer PrintWriter object to write the info of the algorithm performed
     * @param item Which number in the set
     * @param result Value of the Tetranacci number calculated
     */
    public static void writeInfo(PrintWriter writer, int item, long result) throws FileNotFoundException {
        writer.printf("This is the %dth number in the Tetranacci set\n", item);
        writer.printf("The result is %d\n", result);
        writer.printf("Time taken for Exponential: %dns\n", exponentialTime);
        writer.printf("Time taken for TR: %dns\n\n", trTime);
    }

    /** Method to write the time taken for the used algorithms
     * 
     * @param writer PrintWriter object to write the info of the algorithm performed
     * @param item Which number in the set
     * @param result Value of the Tetranacci number calculated
     */
    public static void writeInfoTR(PrintWriter writer, int item, long result) throws FileNotFoundException {
        writer.printf("This is the %dth number in the Tetranacci set\n", item);
        writer.printf("The result is %d\n", result);
        writer.printf("Time taken for TR: %dns\n\n", trTime);
    }


    /**
     * Main Method
     * @param args
     */
    public static void main(String[] args) {
        // Initializing the PrintWriter
        PrintWriter writer = null;

        // Initializes local variable for calculating elapsed times
        long startTimeExpo, endTimeExpo, startTimeTR, endTimeTR;
        // Initializes result variable
        long resultExpo, resultTR;

        // Start Message
        System.out.printf("Hello, It is time to test some Tetranacci Algorithms\n");

        try {
            writer = new PrintWriter(new FileOutputStream(FILE, true));

            for (int i = 5; i < 41; i += 5){
                System.out.printf("Performing Tetranacci for %d\n", i);
                // Calculated Exponential Timing
                System.out.printf("Performing Exponential Calculation...\n");
                startTimeExpo = System.nanoTime();
                resultExpo = ExponentialTetranacci(i);
                endTimeExpo = System.nanoTime();
                exponentialTime = endTimeExpo - startTimeExpo;
                // Calculating Tail Recursive Timing
                System.out.printf("Performing Tail Recursion Calculation...\n");
                startTimeTR = System.nanoTime();
                resultTR = TRTetranacci(i, 0, 0, 0, 1);
                endTimeTR = System.nanoTime();
                trTime = endTimeTR - startTimeTR;

                // Checks if both methods get the same result, Force quit if they are not equal
                if (resultExpo != resultTR){
                    System.out.printf("ERROR, BOTH METHODS PRODUCED DIFFERENT ANSWERS!");
                    writer.close();
                    System.exit(1);
                }
                // Writes timings to a file
                writeInfo(writer, i, resultTR);
                System.out.println();
            }

            writer.printf("\nFrom now on, ExponentialTetranacci will take too long to computer\nSo we will just calculate TRTetranacci from now on\n\n");

            for (int i = 45; i < 101; i += 5){
                System.out.printf("Performing Tetranacci for %d\n", i);
                System.out.printf("Performing Tail Recursion Calculation...\n");
                startTimeTR = System.nanoTime();
                resultTR = TRTetranacci(i, 0, 0, 0, 1);
                endTimeTR = System.nanoTime();
                trTime = endTimeTR - startTimeTR;

                writeInfoTR(writer, i, resultTR);
                System.out.println();
            }
        } catch (FileNotFoundException e) {
            e.getMessage();
        } finally {
            writer.close();
        }
        // End Message
        System.out.printf("Program has ended, Terminating Console!");
    }
}

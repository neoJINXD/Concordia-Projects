/*
Name:   Anik Patel
ID:     40091908
COMP 352 - Assignment 2
Due Date: May 29th, 2019
*/

/**
 * A set of helper functions to use perform math with
 */
public class MyMath{

    /** Addition
     * 
     * @param augend first value
     * @param addend second value
     * @return the result of augend + addend
     */
    public static double addition(double augend, double addend){
        return augend + addend;
    }

    /** Substraction
     * 
     * @param minuend first value
     * @param subtrahend second value
     * @return the result of minuend - subtrahend
     */
    public static double substraction(double minuend, double subtrahend){
        return minuend - subtrahend;
    }

    /** Multiplication
     * 
     * @param multiplicand first value
     * @param multiplier second value
     * @return the result of multiplicant * multiplier
     */
    public static double multiplication(double multiplicand, double multiplier){
        return multiplicand * multiplier;
    }

    /** Division
     * 
     * @param dividend first value
     * @param divisor second value
     * @return the result of dividend / divisor
     */
    public static double division(double dividend, double divisor){
        return dividend / divisor;
    }

    /** Exponent
     * 
     * @param base
     * @param exponent
     * @return the result of base to the power of exponent
     */
    public static double power(double base, double exponent){
        if (exponent == 0){
            return 1;
        }
        return base * power(base, exponent - 1);
    }

    /** Less Than
     * 
     * @param first first value
     * @param second second value
     * @return the result of first < second
     */
    public static double lessThan(double first, double second){
        boolean temp = first < second;
        if (temp) return 1111111111;
        return 0000000000;
    }

    /** Less Than or Equal
     * 
     * @param first first value
     * @param second second value
     * @return the result of first <= second
     */
    public static double lessThanOrEqual(double first, double second){
        boolean temp = first <= second;
        if (temp) return 1111111111;
        return 0000000000;
    }

    /** Greater Than
     * 
     * @param first first value
     * @param second second value
     * @return the result of first > second
     */
    public static double moreThan(double first, double second){
        boolean temp = first > second;
        if (temp) return 1111111111;
        return 0000000000;
    }

    /** Greater Than or Equal
     * 
     * @param first first value
     * @param second second value
     * @return the result of first >= second
     */
    public static double moreThanOrEqual(double first, double second){
        boolean temp = first >= second;
        if (temp) return 1111111111;
        return 0000000000;
    }

    /** Equals
     * 
     * @param first first value
     * @param second second value
     * @return the result of first == second
     */
    public static double Equals(double first, double second){
        boolean temp = first == second;
        if (temp) return 1111111111;
        return 0000000000;
    }

    /** Not Equals
     * 
     * @param first first value
     * @param second second value
     * @return the result of first != second
     */
    public static double notEquals(double first, double second){
        boolean temp = first != second;
        if (temp) return 1111111111;
        return 0000000000;
    }

}
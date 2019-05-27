public class MyMath{
    public static int addition(int augend, int addend){
        return augend + addend;
    }

    public static int substraction(int minuend, int subtrahend){
        return minuend - subtrahend;
    }

    public static int multiplication(int multiplicand, int multiplier){
        return multiplicand * multiplier;
    }

    public static int division(int dividend, int divisor){
        return dividend / divisor;
    }

    public static int power(int base, int exponent){
        if (exponent == 0){
            return 1;
        }
        return base * power(base, exponent - 1);
    }

    public static boolean lessThan(int first, int second){
        return first < second;
    }

    public static boolean lessThanOrEqual(int first, int second){
        return first <= second;
    }

    public static boolean moreThan(int first, int second){
        return first > second;
    }

    public static boolean moreThanOrEqual(int first, int second){
        return first >= second;
    }

    public static boolean Equals(int first, int second){
        return first == second;
    }

    public static boolean notEquals(int first, int second){
        return first != second;
    }
}
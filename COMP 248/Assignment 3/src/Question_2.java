// ---------------------------------------------------------------
// Assignment 3
// Written by: Anik Patel - 40091908
// For COMP 248 Section Q - Fall 2018
// ---------------------------------------------------------------

/*  This is the test code for the House class that we have written in the House.java file.
    This file contains tests for all 4 constructor methods, for each accessor methods,
    for the estimatedPrice method, for each of the mutator methods, for the toString method for
    printing the object, the equality check method, and the "Less than" and "Greater than" methods
 */

public class Question_2
{

    public static void main (String[] args)
    {
        //Constructor with no parameters
        House H1 = new House();
        //Constructor with 1 parameter
        House H2 = new House(100000);
        //Constructor with 2 parameters
        House H3 = new House(100000, 4);
        //Constructor with 3 parameters
        House H4 = new House(200000, 2, "detached");

        //Initial printing of each house
        System.out.println("House H1: "+H1);
        System.out.println("House H2: "+H2);
        System.out.println("House H3: "+H3);
        System.out.println("House H4: "+H4);

        //Accessor method to access the age, cost and type
        System.out.println("\nAccessor Method: The housetype for house H4 is "+H4.getType()+", its age is "+H4.getAge()+", and it costs $"+H4.getCost());

        //estimatedPrice method based on type and age
        System.out.println("\nThe estimated price of house H3 is $"+H3.estimatePrice());
        System.out.println("The estimated price of house H4 is $"+H4.estimatePrice());

        //Mutator method for just changing the age
        H3.setAge(5);
        System.out.println("\nMutator Method: The new age for house H3 is "+H3.getAge());
        //Mutator method for just changing the type
        H3.setType("semi-detached");
        System.out.println("Mutator Method: The new housetype for house H3 is "+H3.getType());
        //Mutator method for just changing the cost
        H3.setCost(240000);
        System.out.println("Mutator Method: The new cost for house H3 is "+H3.getCost());
        //Mutator method to change both age and cost
        H3.setAgeCost(6, 245000);
        System.out.println("Mutator Method: The new house H3 age is "+H3.getAge()+" and its new cost is "+H3.getCost());
        //Mutator method to change all 3 parameters
        H3.setAgeCostType(14, 260000, "semi-detached");
        System.out.println("Mutator Method: The new housetype for house H3 is "+H3.getType()+", its new age is "+H3.getAge()+" and its cost is "+H3.getCost());

        //String printing for House objects
        System.out.println("\ntoString: "+H3);

        //Equality check for House objects
        System.out.println("\nHouses H1 and H2 are equal is "+H1.equals(H2));
        System.out.println("Houses H1 and H4 are equals is "+H1.equals(H4));

        //"Greater than" and "Less than" check for House objects
        System.out.println("\nHouse H4 is less than H3 is "+H4.isLessThan(H3));
        System.out.println("\nHouse H1 is greater than H3 is "+H1.isGreaterThan(H3));


    }
}

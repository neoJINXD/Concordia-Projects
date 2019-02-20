// ---------------------------------------------------------------
// Assignment 3
// Written by: Anik Patel - 40091908
// For COMP 248 Section Q - Fall 2018
// ---------------------------------------------------------------

/*  This file contains the House class that forms the House objects that have an age as an integer,
    a cost as a double and a type as a String.
    This file contains all the methods that were needed: constructors, accessors, mutators, toStrings,
    equality check for the type and age, and the "Less than" and "Greater than" check for price

 */

public class House {

    private int age;
    private String type;
    private double cost;

    //The 4 different constructor methods depending on how many parameters are given
    //No parameters
    public House()
    {
        age=50;
        type="attached";
        cost=100000;
    }
    //1 parameter given - The cost
    public House(double cost)
    {
        age = 50;
        type="attached";
        this.cost=cost;
    }
    //2 parameters given - The cost and the age
    public House(double cost, int age)
    {
        this.age=age;
        type="attached";
        this.cost=cost;
    }
    //All 3 parameters given - The cost, the age and the type
    public House(double cost, int age, String type)
    {
        this.age=age;
        this.type=type;
        this.cost=cost;
    }



    //The 3 different accessor methods for the class House
    //Accessor method for the age
    public int getAge()
    {
        return age;
    }
    //Accessor method for the type
    public String getType()
    {
        return type;
    }
    //Accessor method for the cost
    public double getCost()
    {
        return cost;
    }

    //The 5 mutator methods
    //Mutator for the age
    public void setAge(int newAge)
    {
        if (newAge<0)
        {
            System.out.println("Error, age cannot be negative");
            System.exit(0);
        }
        else
            age = newAge;
    }
    //Mutator for the cost
    public void setCost(double newCost)
    {
        if (newCost<0)
        {
            System.out.println("Error, price cannot be negative");
            System.exit(0);
        }
        else
            cost = newCost;
    }
    //Mutator for the type
    public void setType(String newType)
    {
        if ((newType.equalsIgnoreCase("attached"))||(newType.equalsIgnoreCase("detached"))||(newType.equalsIgnoreCase("semi-detached")))
            type = newType;

        else
        {
            System.out.println("Error, type given is unavailable for House");
            System.exit(0);
        }

    }
    //Mutator for both age and cost
    public void setAgeCost(int newAge, double newCost)
    {
        if ((newAge<0)||(newCost<0))
        {
            System.out.println("Error, neither the age nor the cost can be negative");
            System.exit(0);
        }
        age = newAge;
        cost = newCost;
    }
    //Mutator for all 3 parameters
    public void setAgeCostType (int newAge, double newCost, String newType)
    {

        if ((newAge<0)||(newCost<0))
        {
            System.out.println("Error, neither the age nor the cost can be negative");
            System.exit(0);
        }
        if ((newType.equalsIgnoreCase("attached"))||(newType.equalsIgnoreCase("detached"))||(newType.equalsIgnoreCase("semi-detached")))
        {
            age = newAge;
            cost = newCost;
            type = newType;
        }
        else
        {
            System.out.println("Error, type given is unavailable for House");
            System.exit(0);
        }
    }



    //Public method to estimate price of a House based on age and type
    public double estimatePrice()
    {
        double estimatedCost=0;
        //Checks for type first
        if (type.equalsIgnoreCase("attached"))
        {
            if (age>=5)
            {
                int extraYears = age-5;
                estimatedCost = (100000*(1.05)*(1+(extraYears*0.02)));
            }
            else
                estimatedCost = (100000*(1+(age*0.01)));
        }
        else if (type.equalsIgnoreCase("semi-detached"))
        {
            if (age>=5)
            {
                int extraYears = age-5;
                estimatedCost = (150000*(1.10)*(1+(age*0.03)));
            }
            else
                estimatedCost = (150000*(1+(age*0.02)));
        }
        else if (type.equalsIgnoreCase("detached"))
        {
            estimatedCost = (200000*(1+(age*0.02)));
        }
        return estimatedCost;
    }


    //toString method to display the information for the House object
    public String toString()
    {
        return ("This House is type "+type+". Its age is "+age+" and costs $"+cost);
    }


    public boolean equals(House otherHouse)
    {
        return ((otherHouse.age == this.age)&&(otherHouse.type.equalsIgnoreCase( this.type)));
    }


    public boolean isLessThan(House otherHouse)
    {
        return (this.cost<otherHouse.cost);
    }


    public boolean isGreaterThan(House otherHouse)
    {
        return (this.cost>otherHouse.cost);
    }




}

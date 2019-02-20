// -----------------------------------------------------
// COMP249
// Assignment 1
// Question: Part I
// Written by: Anik Patel, 40091908
// Due Friday, February 1, 2019
// -----------------------------------------------------

import java.util.Scanner;


/**
 * The driver class that contains the main method to run code the bookstore software while using the Book.class file
 *
 * @author Anik Patel - 40091908
 * Assignment 1
 * @version Part II
 * COMP249
 * Due Date February 1, 2019
 */
public class driver {

    /** This method will return the total amount of Books objects written by a given author while printing them out for the user to see.
     *  The array inv is the array that stores the inventory of the bookstore.
     *  The authorName is whose books we are looking for.
     *
     * @param inv Array of the inventory
     * @param authorName Name of the author to search
     * @return The total count of how many books are made by a given author
     */
    public static int findBookBy(Book[] inv, String authorName) {
        int count = 0;
        for (int i = 0; i < inv.length - 2; i++) {
            if (inv[i].getAuthor().equals(authorName)) {
                System.out.println(inv[i]);
                System.out.println();
                count++;
            }
        }
        return count;
    }

    /** This method will return the total amount of Books objects below a given price while printing them out for the user to see.
     *  The array inv is the array that stores the inventory of the bookstore.
     *  The value is the max price to look for in the bookstore.
     *
     * @param inv  Array of the inventory
     * @param value Max value for the price
     * @return The total count of how many books are bellow a given price
     */
    public static int findCheaperThan(Book[] inv, double value) {
        int count = 0;
        for (int i = 0; i < inv.length - 2; i++) {
            if (inv[i].getPrice() < value) {
                System.out.println(inv[i]);
                System.out.println();
                count++;
            }
        }
        return count;
    }

    /** Main method to run the Bookstore's application.
     *
     * @param args
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int space, choice, choiceBook, entry, givenIndex;
        int currentIndex = 0;
        boolean continueDo = true;
        final String PASSWORD = "password";
        int pwFailCount = 0;
        int securityFailCount = 0;
        //String junk;
        String title, author;
        long isbn;
        double price;
        String searchAuthor;
        double searchPrice;
        String passIn;

        System.out.println("Welcome to the BookStore Program!");
        System.out.println("Please enter the total amount of book your store can hold: ");

        space = input.nextInt();

        Book[] inventory = new Book[space];
        //for (int i = 0; i < space; i++)
        //    inventory[i] = new Book();

        do {
            if (securityFailCount == 3) {
                System.out.println("Program detected suspicious activities and will terminate immediately!");
                break;
            }
            System.out.print("\nWhat would you like to do?\n\t1. Enter new books (password required)\n\t2. Change information of a book (password required)\n\t3. Display all books by a specific author\n\t4. Display all books under a certain price\n\t5. Quit\nPlease enter your choice >> ");
            choice = input.nextInt();
            input.nextLine();
            System.out.println();
            pwFailCount = 0;
            switch (choice) {
                case 1:
                    if ((space - Book.findNumberOfCreatedBooks()) == 0) {
                        System.out.println("There is no more space left for books\nReturning to main menu...");
                        break;
                    }
                    System.out.print("Please enter your password>> ");
                    do {
                        passIn = input.nextLine();
                        if (!passIn.equals(PASSWORD)) {
                            pwFailCount++;
                            System.out.println("Invalid password");
                            System.out.print("Please re-enter password>> ");
                        } else {
                            do {
                                System.out.print("\nHow many books would you like to enter?>> ");
                                entry = input.nextInt();
                                if (entry > (space - Book.findNumberOfCreatedBooks()))
                                    System.out.println("There is not enough room for your action, there is only " + (space - Book.findNumberOfCreatedBooks()) + " places left");
                            } while (entry > (space - Book.findNumberOfCreatedBooks()));

                            for (int i = 1; i <= entry; i++) {
                                System.out.println("\nPlease enter the values for book " + i + " to add: ");
                                System.out.print("Enter the Title>> ");
                                input.nextLine();
                                title = input.nextLine();
                                System.out.print("Enter the Author>> ");
                                author = input.nextLine();
                                System.out.print("Enter the ISBN>> ");
                                isbn = input.nextLong();
                                System.out.print("Enter the Price(this format #.##)>> ");
                                price = input.nextDouble();

                                inventory[currentIndex] = new Book(title, author, isbn, price);
                                currentIndex++;
                                System.out.println();
                            }
                            break;
                        }
                    } while (pwFailCount < 3);
                    if (pwFailCount == 3) {
                        System.out.println("Password check failed, returning to main menu");
                        securityFailCount++;
                    }
                    break;
                case 2:
                    System.out.print("Please enter your password>> ");
                    passIn = input.nextLine();
                    if (!passIn.equals(PASSWORD)) {
                        pwFailCount++;
                        System.out.println("Invalid password");
                    } else {
                        do {
                            System.out.print("Which book would you like to change>> ");
                            givenIndex = input.nextInt();
                            if (input.nextInt()==-1)
                                break;

                            if (givenIndex >= space)
                                System.out.println("Given number is outside limits");
                            if (inventory[givenIndex] == null)
                                System.out.println("There is no book in this spot, enter -1 to exit");
                        } while ((givenIndex >= space) || (inventory[givenIndex] == null));


                        do {
                            System.out.println("Book #" + givenIndex + "\n" + inventory[givenIndex]);
                            System.out.print("\nWhat information would you like to change?\n\t1. Author\n\t2. Title\n\t3. ISBN\n\t4. Price\n\t5. Quit\nEnter your choice>> ");

                            choiceBook = input.nextInt();
                            switch (choiceBook) {
                                case 1:
                                    System.out.println("What is the new author?");
                                    String newAuthor = input.nextLine();
                                    inventory[givenIndex].setAuthor(newAuthor);
                                    break;
                                case 2:
                                    System.out.println("What is the new title?");
                                    String newTitle = input.nextLine();
                                    inventory[givenIndex].setTitle(newTitle);
                                    break;
                                case 3:
                                    System.out.println("What is the new ISBN?");
                                    inventory[givenIndex].setISBN(input.nextInt());
                                    break;
                                case 4:
                                    System.out.println("What is the new price?");
                                    inventory[givenIndex].setPrice(input.nextDouble());
                                    break;
                                case 5:
                                    System.out.println("Stopping changes");
                                    break;
                                default:
                                    System.out.println("Error, Invalid choice, please try again");
                                    break;

                            }
                        } while (choiceBook != 5);
                    }
                    if (pwFailCount == 3) {
                        System.out.println("Password check failed, returning to main menu");
                    }
                    break;
                case 3:
                    System.out.print("What author would you like to search up>> ");

                    searchAuthor = input.nextLine();
                    System.out.println("Searching for " + searchAuthor);
                    if (findBookBy(inventory, searchAuthor) == 0)
                        System.out.println("No books found with that author");
                    //findBookBy(inventory, searchAuthor);
                    break;
                case 4:
                    System.out.print("What is the max price you are looking for>> ");
                    searchPrice = input.nextDouble();
                    System.out.println("Searching for books under " + searchPrice + "$");
                    if (findCheaperThan(inventory, searchPrice) == 0)
                        System.out.println("No books under this price");
                    findCheaperThan(inventory, searchPrice);
                    break;
                case 5:
                    System.out.println("Program Terminated");
                    System.out.println("Thank you for using the program, have a nice day!");
                    continueDo = false;
                    break;
                default:
                    System.out.println("Error, Invalid choice, please try again");
                    break;
            }
        } while (continueDo);


    }
}

// -----------------------------------------------------
// COMP249
// Assignment 1
// Question: Part I
// Written by: Anik Patel, 40091908
// Due Friday, February 1, 2019
// -----------------------------------------------------

/**
 * Book class that allows the creation of Book objects to contain information about books
 *
 * @author Anik Patel - 40091908
 * Assignment 1
 * @version Part I
 * COMP249
 * Due Date February 1, 2019
 */

public class Book {
    private String title;
    private String author;
    private long ISBN;
    private double price;
    private static int NbOfBooks = 0;

    /**
     * Default constructor for the Book class.
     * Sets:
     * title to "No Title"
     * author to "No Name"
     * ISBN to 0
     * price to 0.00
     */
    public Book() {
        NbOfBooks++;
        title = "No Title";
        author = "No Name";
        ISBN = 0;
        price = 0.00;
    }

    /**
     * Constructor with given parameters for all values
     *
     * @param newTitle title given to the book
     * @param newAuthor author that made the book
     * @param newISBN ISBN for the book
     * @param newPrice price for which the book is selling for
     */
    public Book(String newTitle, String newAuthor, long newISBN, double newPrice) {
        NbOfBooks++;
        title = newTitle;
        author = newAuthor;
        ISBN = newISBN;
        price = newPrice;
    }

    /**
     * Accessor method for the title of Book objects
     *
     * @return a String containing the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Accessor method for the author of Book objects
     *
     * @return a String containing the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Accessor method for the ISBN of Book objects
     *
     * @return an integer containing the ISBN
     */
    public long getISBN() {
        return ISBN;
    }

    /**
     * Accessor method for the price of Book objects
     *
     * @return a double containing the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Mutator method to give a Book object a new title
     *
     * @param newTitle a String containing the new name
     */
    public void setTitle(String newTitle) {
        title = newTitle;
    }

    /**
     * Mutator method to give a Book object a new author
     *
     * @param newAuthor a String containing the new author
     */
    public void setAuthor(String newAuthor) {
        author = newAuthor;
    }

    /**
     * Mutator method to give a Book object a new ISBN
     *
     * @param newISBN an integer containing the new ISBN value
     */
    public void setISBN(long newISBN) {
        ISBN = newISBN;
    }

    /**
     * Mutator method to give a Book object a new price
     *
     * @param newPrice a double containing the new price
     */
    public void setPrice(double newPrice) {
        price = newPrice;
    }

    /**
     * toString method for when a Book object is being printed
     *
     * @return a String containing all the parameters of the given Book object in a readable form
     */
    public String toString() {
        return "Title: " + title + "\nAuthor: " + author + "\nISBN: " + ISBN + "\nPrice: " + price;
    }

    /**
     * static method used to give the total amount of Book objects that were created.
     * This value is stored as a astatic variable and is iterated upon each time a new Book is created.
     *
     * @return static variable that holds count of how many Book were created
     */
    public static int findNumberOfCreatedBooks() {
        return NbOfBooks;
    }

    /**
     * equals method used to compare the ISBN value and hte price of two Book objects
     *
     * @param other a second Book object to compare with
     * @return a boolean value that is true if the ISBN and price are the same
     */
    public boolean equals(Book other) {
        return (ISBN == other.getISBN()) && (price == other.getPrice());
    }

    /**
     * main method used to test all the methods that were created for Book objects
     *
     * @param args
     */
    public static void main(String[] args) {
        Book a = new Book();
        Book b = new Book("Warp Star", "Satoru", 123456789, 9.99);
        Book c = new Book("Warp Star", "Satoru", 123456789, 9.99);
        Book d = new Book("Warp Star", "Satoru", 123456789, 10.99);
        Book e = new Book("Mario", "Satoru", 123456789, 9.99);
        Book f = new Book("Warp Star", "Satoru", 12346789, 9.99);
        System.out.println(a.getPrice());
        System.out.println(Book.findNumberOfCreatedBooks());
        System.out.println(a.equals(e));
        System.out.println(e.equals(b));
        System.out.println(a);
        System.out.println(b);
        System.out.println("\n\n");
        Book[] test = new Book[5];
        for (int i = 0; i < 5; i++)
            test[i] = new Book();
        System.out.println(test[1]);
    }

}


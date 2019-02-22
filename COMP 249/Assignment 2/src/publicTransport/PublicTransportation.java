//--------------------------------------------------------
//  COMP 249
//  Assignment 2
//  Part I
//  Written by: Anik Patel - 40091908
//  Due Sunday, February 24, 2019
//--------------------------------------------------------


//Making all the attributes private will allow only the classes and files that have the appropriate permissions to access the values that may be stored in memory while the code it memory

package publicTransport;

/**
 * Base PublicTransportation file that will be used as parents for all the other classes
 *
 * @author Anik Patel - 40091908
 * @version Part I
 */
public class PublicTransportation {
    private double ticketPrice;
    private int nbOfStops;

    /**
     * Default constructor for PublicTransportation
     */
    public PublicTransportation() {
        this.ticketPrice = 0;
        this.nbOfStops = 0;
    }

    /**
     * Parametrized constructor for PublicTransportation
     * @param price Price for the transport
     * @param stops Number of stops for the transport
     */
    public PublicTransportation(double price, int stops) {
        this.ticketPrice = price;
        this.nbOfStops = stops;
    }

    /**
     * Copy constructor for PublicTransportation
     * @param other PublicTransportation object to copy from
     */
    public PublicTransportation(PublicTransportation other) {
        this.ticketPrice = other.ticketPrice;
        this.nbOfStops = other.nbOfStops;
    }

    /**
     * Accessor method for the price
     * @return Value of the price
     */
    public double getTicketPrice() {
        return ticketPrice;
    }

    /**
     * Accessor method for the number of stops
     * @return Number of stops
     */
    public int getNbOfStops() {
        return nbOfStops;
    }

    /**
     * Mutator method for the price
     * @param ticketPrice New price
     */
    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    /**
     * Mutator method for the number of stops
     * @param nbOfStops New number of stops
     */
    public void setNbOfStops(int nbOfStops) {
        this.nbOfStops = nbOfStops;
    }

    /**
     * Equals method to compare 2 objects
     * @param o Other object to compare to
     * @return Boolean value, true if they are equivalent
     */
    public boolean equals(Object o) {
        //Checks location
        if (this == o)
            return true;
        //Checks type
        if (!(o instanceof PublicTransportation))
            return false;
        //Checks nullity
        if (o == null)
            return false;

        PublicTransportation that = (PublicTransportation) o;
        return (ticketPrice == that.ticketPrice) && (nbOfStops == that.nbOfStops);
    }

    /**
     * toString method to print the object
     * @return String of the attributes of the PublicTransportation
     */
    public String toString() {
        return "PublicTransportation \t{ " +
                "ticketPrice=" + ticketPrice +
                ", nbOfStops=" + nbOfStops +
                " } ";
    }

    public static void main(String[] args) {
        PublicTransportation t = new PublicTransportation(50, 10);
        PublicTransportation n = new PublicTransportation(50, 10);
        System.out.println(t);
        System.out.println(t.equals(n));
    }
}

//--------------------------------------------------------
//  Assignment 2
//  Part I
//  Written by: Anik Patel - 40091908
//--------------------------------------------------------

package Water;

/**
 * First level child form PublicTransport
 * @author Anik Patel - 40091908
 * @version Part I
 */
public class Ferry extends publicTransport.PublicTransportation {
    private int buildYear;
    private String shipName;

    /**
     * Default constructor for Ferry
     */
    public Ferry() {
        super();
        this.buildYear = 0;
        this.shipName = "";
    }

    /**
     * Parametrized constructor for Ferry
     * @param price Price for the transport
     * @param stops Number of stops for the transport
     * @param buildYear Value of the year ferry was built
     * @param shipName Name of the ship
     */
    public Ferry(double price, int stops, int buildYear, String shipName) {
        super(price, stops);
        this.buildYear = buildYear;
        this.shipName = shipName;
    }

    /**
     * Copy constructor for Ferry
     * @param other Ferry object to copy from
     */
    public Ferry(Ferry other) {
        super(other);
        this.buildYear = other.buildYear;
        this.shipName = other.shipName;
    }

    /**
     * Accessor method for the build year
     * @return Value of the year the ship was built
     */
    public int getBuildYear() {
        return buildYear;
    }

    /**
     * Accessor method for the name of the ship
     * @return Name of the ship
     */
    public String getShipName() {
        return shipName;
    }

    /**
     * Mutator method for the build year
     * @param buildYear Value of the year the ship was built
     */
    public void setBuildYear(int buildYear) {
        this.buildYear = buildYear;
    }

    /**
     * Mutator method for the name of the ship
     * @param shipName Name of the ship
     */
    public void setShipName(String shipName) {
        this.shipName = shipName;
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
        if (!(o instanceof Ferry))
            return false;
        //Checks nullity
        if (o == null)
            return false;

        Ferry ferry = (Ferry) o;
        return (super.equals(ferry)) && (buildYear == ferry.buildYear) && (shipName.equals(ferry.shipName));
    }

    /**
     * toString method to print the object
     * @return String of the attributes of the Ferry
     */
    public String toString() {
        return "Ferry \t\t\t\t\t{ " +
                "ticketPrice=" + this.getTicketPrice() +
                ", nbOfStops=" + this.getNbOfStops() +
                ". buildYear=" + buildYear +
                ", shipName=\"" + shipName + "\"" +
                " } ";
    }
}

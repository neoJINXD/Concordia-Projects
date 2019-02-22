//--------------------------------------------------------
//  Assignment 2
//  Part I
//  Written by: Anik Patel - 40091908
//--------------------------------------------------------

package Land;

/**
 * Second level child class from CityBus
 * @author Anik Patel - 40091908
 * @version Part I
 */
public class Tram extends CityBus {
    private int maxSpeed;

    /**
     * Default constructor for Tram
     */
    public Tram() {
        this.maxSpeed = 0;
    }

    /**
     * Parametrized constructor for Tram
     * @param ticketPrice Price for the transport
     * @param nbOfStops Number of stops for the transport
     * @param routeNb ID Number for the transport
     * @param beginYear Year when the transport started
     * @param name Name of the transport
     * @param driverName Name of the person who drives the transport
     * @param maxSpeed Value for the max speed
     */
    public Tram(double ticketPrice, int nbOfStops, long routeNb, int beginYear, String name, String driverName, int maxSpeed) {
        super(ticketPrice, nbOfStops, routeNb, beginYear, name, driverName);
        this.maxSpeed = maxSpeed;
    }

    /**
     * Copy constructor for Tram
     * @param other Tram object to copy from
     */
    public Tram(Tram other) {
        super(other);
        this.maxSpeed = other.maxSpeed;
    }

    /**
     * Accessor method for the max speed
     * @return Number of the max speed
     */
    public int getMaxSpeed() {
        return maxSpeed;
    }

    /**
     * Mutator method for the max speed
     * @param maxSpeed Number of the max speed
     */
    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
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
        if (!(o instanceof Tram))
            return false;
        //Checks nullity
        if (o == null)
            return false;

        Tram tram = (Tram) o;
        return (super.equals(tram)) && (maxSpeed == tram.maxSpeed);
    }


    /**
     * toString method to print object
     * @return String of the attributes of the Tram
     */
    public String toString() {
        return "Tram \t\t\t\t\t{ " +
                "ticketPrice=" + this.getTicketPrice() +
                ", nbOfStops=" + this.getNbOfStops() +
                ", routeNb=" + this.getRouteNb() +
                ", beginYear=" + this.getBeginYear() +
                ", name=\"" + this.getName() + "\"" +
                ", driverName=\"" + this.getDriverName() + "\"" +
                ", maxSpeed=" + maxSpeed +
                " } ";
    }

    public static void main(String[] args) {
        Tram t = new Tram();
        Tram n = new Tram();
        System.out.println(t);
        System.out.println(t.equals(n));
    }
}

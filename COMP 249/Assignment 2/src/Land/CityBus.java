//--------------------------------------------------------
//  Assignment 2
//  Part I
//  Written by: Anik Patel - 40091908
//--------------------------------------------------------

package Land;

/**
 * First level child class from publicTransportation
 *  @author Anik Patel - 40091908
 *  @version Part I
 */
public class CityBus extends publicTransport.PublicTransportation {
    private long routeNb;
    private int beginYear;
    private String name;
    private String driverName;

    /**
     * Default constructor for CityBus
     */
    public CityBus() {
        super();
        this.routeNb = 0;
        this.beginYear = 0;
        this.name = "";
        this.driverName = "";
    }

    /**
     * Parametrized constructor for CityBus
     * @param ticketPrice Price for the transport
     * @param nbOfStops Number of stops for the transport
     * @param routeNb ID Number for the transport
     * @param beginYear Year when the transport started
     * @param name Name of the transport
     * @param driverName Name of the person who drives the transport
     */
    public CityBus(double ticketPrice, int nbOfStops, long routeNb, int beginYear, String name, String driverName) {
        super(ticketPrice, nbOfStops);
        this.routeNb = routeNb;
        this.beginYear = beginYear;
        this.name = name;
        this.driverName = driverName;
    }

    /**
     * Copy constructor for CityBus
     * @param other CityBus object to copy from
     */
    public CityBus(CityBus other) {
        super(other);
        this.routeNb = other.routeNb;
        this.beginYear = other.beginYear;
        this.name = other.name;
        this.driverName = other.driverName;
    }

    /**
     * Mutator method for the route number
     * @param routeNb Number of the route
     */
    public void setRouteNb(long routeNb) {
        this.routeNb = routeNb;
    }

    /**
     * Mutator method for the start year
     * @param beginYear Start year for the transport
     */
    public void setBeginYear(int beginYear) {
        this.beginYear = beginYear;
    }

    /**
     * Mutator method for the name
     * @param name Name for the transport
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Mutator method for the driver's name
     * @param driverName Name of the person driving the transport
     */
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    /**
     * Accessor method for the route number
     * @return Value for the number of the route
     */
    public long getRouteNb() {
        return routeNb;
    }

    /**
     * Accessor method for the start year
     * @return Value of the year that the transport began
     */
    public int getBeginYear() {
        return beginYear;
    }

    /**
     * Accessor method for the name
     * @return Name value for the name of the transport
     */
    public String getName() {
        return name;
    }

    /**
     * Accessor method for the driver's name
     * @return Value for the name of the driver
     */
    public String getDriverName() {
        return driverName;
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
        if (!(o instanceof CityBus))
            return false;
        //Checks nullity
        if (o == null)
            return false;

        CityBus cityBus = (CityBus) o;
        return (super.equals(cityBus)) && (routeNb == cityBus.routeNb) && (beginYear == cityBus.beginYear) && (name.equals(cityBus.name)) && (driverName.equals(cityBus.driverName));
    }


    /**
     * toString method to print the object
     * @return String of the attributes of the CityBus
     */
    public String toString() {
        return "CityBus \t\t\t\t{ " +
                "ticketPrice=" + this.getTicketPrice() +
                ", nbOfStops=" + this.getNbOfStops() +
                ", routeNb=" + routeNb +
                ", beginYear=" + beginYear +
                ", name=\"" + name + "\"" +
                ", driverName=\"" + driverName + "\"" +
                " } ";
    }

    public static void main(String[] args) {
        CityBus t = new CityBus();
        CityBus n = new CityBus();
        System.out.println(t);
        System.out.println(n.equals(t));
    }
}

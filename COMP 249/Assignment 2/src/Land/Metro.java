//--------------------------------------------------------
//  Assignment 2
//  Part I
//  Written by: Anik Patel - 40091908
//--------------------------------------------------------

package Land;

/**
 * Second level child from CityBus
 * @author Anik Patel - 40091908
 * @version Part I
 */
public class Metro extends CityBus {
    private int nbOfVehicles;
    private String cityName;

    /**
     * Default constructor for Metro
     */
    public Metro() {
        super();
        this.nbOfVehicles = 0;
        this.cityName = "";
    }

    /**
     * Parametrized constructor for Metro
     * @param ticketPrice Price for the transport
     * @param nbOfStops Number of stops for the transport
     * @param routeNb ID Number for the transport
     * @param beginYear Year when the transport started
     * @param name Name of the transport
     * @param driverName Name of the person who drives the transport
     * @param nbOfVehicles Value for the amount of vehicles on a metro line
     * @param cityName Name of the city where the metro is
     */
    public Metro(double ticketPrice, int nbOfStops, long routeNb, int beginYear, String name, String driverName, int nbOfVehicles, String cityName) {
        super(ticketPrice, nbOfStops, routeNb, beginYear, name, driverName);
        this.nbOfVehicles = nbOfVehicles;
        this.cityName = cityName;
    }

    /**
     * Copy constructor for Metro
     * @param other Metro object to copy from
     */
    public Metro(Metro other) {
        super(other);
        this.nbOfVehicles = other.nbOfVehicles;
        this.cityName = other.cityName;
    }

    /**
     * Accessor method for the number of vehicles
     * @return Number of the amount of vehicles
     */
    public int getNbOfVehicles() {
        return nbOfVehicles;
    }

    /**
     * Accessor method for the city name
     * @return Value of the city's name
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * Mutator method for the number of vehicles
     * @param nbOfVehicles Number of the amount of vehicles
     */
    public void setNbOfVehicles(int nbOfVehicles) {
        this.nbOfVehicles = nbOfVehicles;
    }

    /**
     * Mutator method for the city's name
     * @param cityName String of the city's name
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
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
        if (!(o instanceof Metro))
            return false;
        //Checks nullity
        if (o == null)
            return false;

        Metro metro = (Metro) o;
        return (super.equals(metro)) && (nbOfVehicles == metro.nbOfVehicles) && (cityName.equals(metro.cityName));
    }

    /**
     * toString method to print the object
     * @return String of the attributes of the Metro
     */
    public String toString() {
        return "Metro \t\t\t\t\t{ " +
                "ticketPrice=" + this.getTicketPrice() +
                ", nbOfStops=" + this.getNbOfStops() +
                ", routeNb=" + this.getRouteNb() +
                ", beginYear=" + this.getBeginYear() +
                ", name=\"" + this.getName() + "\"" +
                ", driverName=\"" + this.getDriverName() + "\"" +
                ", nbOfVehicles=" + nbOfVehicles +
                ", cityName=\"" + cityName + "\"" +
                " } ";
    }

    public static void main(String[] args) {
        Metro t = new Metro();
        Metro n = new Metro();
        System.out.println(t);
        System.out.println(t.equals(n));
    }
}

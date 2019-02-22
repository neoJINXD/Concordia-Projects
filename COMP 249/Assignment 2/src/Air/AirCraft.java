//--------------------------------------------------------
//  Assignment 2
//  Part I
//  Written by: Anik Patel - 40091908
//--------------------------------------------------------

package Air;

/**
 * First level child from PublicTransport
 * @author Anik Patel - 40091908
 * @version Part I
 */
public class AirCraft extends publicTransport.PublicTransportation {
    /**
     * The different "classes" that an AirCraft object can be
     */
    public enum Class {
        Helicopter, Airline, Glider, Balloon
    }

    /**
     * The different maintenance type an AirCraft can be
     */
    public enum Maintenance {
        Weekly, Monthly, Yearly
    }

    private Class vehicleClass;
    private Maintenance maintenanceType;

    /**
     * Default constructor for AirCraft
     */
    public AirCraft() {
        super();
        vehicleClass = null;
        maintenanceType = null;
    }

    /**
     * Parametrized constructor for AirCraft
     * @param price Price for the transport
     * @param stops Number of stops for the transport
     * @param vehicleClass Enum class type for transport
     * @param maintenanceType Enum maintenance type for transport
     */
    public AirCraft(double price, int stops, Class vehicleClass, Maintenance maintenanceType) {
        super(price, stops);
        this.vehicleClass = vehicleClass;
        this.maintenanceType = maintenanceType;
    }

    /**
     * Copy constructor for AirCraft
     * @param other Aircraft object to copy from
     */
    public AirCraft(AirCraft other) {
        super(other);
        this.vehicleClass = other.vehicleClass;
        this.maintenanceType = other.maintenanceType;
    }

    /**
     * Accessor method for the vehicle class
     * @return Enumerated type for the vehicle type
     */
    public Class getVehicleClass() {
        return vehicleClass;
    }

    /**
     * Accessor method for the maintenance type
     * @return Enumrated type for the maintenance type
     */
    public Maintenance getMaintenanceType() {
        return maintenanceType;
    }

    /**
     * Mutator method for the vehicle class
     * @param vehicleClass Enumerated type for the vehicle type
     */
    public void setVehicleClass(Class vehicleClass) {
        this.vehicleClass = vehicleClass;
    }

    /**
     * Mutator method for the maintenance type
     * @param maintenanceType Enumrated type for the maintenance type
     */
    public void setMaintenanceType(Maintenance maintenanceType) {
        this.maintenanceType = maintenanceType;
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
        if (!(o instanceof AirCraft))
            return false;
        //Checks nullity
        if (o == null)
            return false;

        AirCraft airCraft = (AirCraft) o;
        return (super.equals(airCraft)) && (vehicleClass == airCraft.vehicleClass) && (maintenanceType == airCraft.maintenanceType);

    }

    /**
     * toString method to print the object
     * @return String of the attributes of the AirCraft
     */
    public String toString() {
        return "AirCraft \t\t\t\t{ " +
                "ticketPrice=" + this.getTicketPrice() +
                ", nbOfStops=" + this.getNbOfStops() +
                ", vehicleClass= \"" + vehicleClass + "\"" +
                ", maintenanceType= \"" + maintenanceType + "\"" +
                " } ";
    }

    public static void main(String[] args) {
        AirCraft t = new AirCraft(500, 2, Class.Glider, Maintenance.Yearly);
        AirCraft n = new AirCraft();
        System.out.println(t);
        System.out.println(t.equals(n));
    }
}



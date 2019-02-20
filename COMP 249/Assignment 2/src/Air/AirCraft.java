package Air;

public class AirCraft extends publicTransport.PublicTransportation {
    enum Class {
        Helicopter, Airline, Glider, Balloon
    }

    enum Maintenance {
        Weekly, Monthly, Yearly
    }

    Class vehicleClass;
    Maintenance maintenanceType;

    public AirCraft() {
        super();
        vehicleClass = null;
        maintenanceType = null;
    }

    public AirCraft(double price, int stops, Class vehicleClass, Maintenance maintenanceType) {
        super(price, stops);
        this.vehicleClass = vehicleClass;
        this.maintenanceType = maintenanceType;
    }

    public AirCraft(AirCraft other) {
        super(other);
        this.vehicleClass = other.getVehicleClass();
        this.maintenanceType = other.getMaintenanceType();
    }

    public Class getVehicleClass() {
        return vehicleClass;
    }

    public Maintenance getMaintenanceType() {
        return maintenanceType;
    }

    public void setVehicleClass(Class vehicleClass) {
        this.vehicleClass = vehicleClass;
    }

    public void setMaintenanceType(Maintenance maintenanceType) {
        this.maintenanceType = maintenanceType;
    }
}

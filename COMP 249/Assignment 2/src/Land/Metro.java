package Land;

public class Metro extends CityBus{
    int nbOfVehicles;
    String cityName;

    public Metro(){
        super();
        this.nbOfVehicles=0;
        this.cityName="";
    }

    public Metro(double ticketPrice, int nbOfStops, long routeNb, int beginYear, String name, String driverName, int nbOfVehicles, String cityName) {
        super(ticketPrice, nbOfStops, routeNb, beginYear, name, driverName);
        this.nbOfVehicles = nbOfVehicles;
        this.cityName = cityName;
    }

    public Metro(Metro other) {
        super(other);
        this.nbOfVehicles = other.getNbOfVehicles();
        this.cityName = other.getCityName();
    }

    public int getNbOfVehicles() {
        return nbOfVehicles;
    }

    public String getCityName() {
        return cityName;
    }

    public void setNbOfVehicles(int nbOfVehicles) {
        this.nbOfVehicles = nbOfVehicles;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}

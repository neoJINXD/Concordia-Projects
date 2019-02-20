package Land;

public class CityBus extends publicTransport.PublicTransportation {
    long routeNb;
    int beginYear;
    String name;
    String driverName;

    public CityBus() {
        super();
        this.routeNb = 0;
        this.beginYear = 0;
        this.name = "";
        this.driverName = "";
    }

    public CityBus(double ticketPrice, int nbOfStops, long routeNb, int beginYear, String name, String driverName) {
        super(ticketPrice, nbOfStops);
        this.routeNb = routeNb;
        this.beginYear = beginYear;
        this.name = name;
        this.driverName = driverName;
    }

    public CityBus(CityBus other) {
        super(other);
        this.routeNb = other.getRouteNb();
        this.beginYear = other.getBeginYear();
        this.name = other.getName();
        this.driverName = other.getDriverName();
    }

    public void setRouteNb(long routeNb) {
        this.routeNb = routeNb;
    }

    public void setBeginYear(int beginYear) {
        this.beginYear = beginYear;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public long getRouteNb() {
        return routeNb;
    }

    public int getBeginYear() {
        return beginYear;
    }

    public String getName() {
        return name;
    }

    public String getDriverName() {
        return driverName;
    }
}

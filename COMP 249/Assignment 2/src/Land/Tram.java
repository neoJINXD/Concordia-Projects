package Land;

public class Tram extends CityBus{
    int maxSpeed;

    public Tram(){
        this.maxSpeed=0;
    }

    public Tram(double ticketPrice, int nbOfStops, long routeNb, int beginYear, String name, String driverName, int maxSpeed) {
        super(ticketPrice, nbOfStops, routeNb, beginYear, name, driverName);
        this.maxSpeed = maxSpeed;
    }

    public Tram(Tram other){
        super(other);
        this.maxSpeed=other.getMaxSpeed();
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
}

package Water;

public class Ferry extends publicTransport.PublicTransportation {
    int buildYear;
    String shipName;

    public Ferry() {
        super();
        this.buildYear=0;
        this.shipName="";
    }

    public Ferry(double price, int stops, int buildYear, String shipName) {
        super(price, stops);
        this.buildYear = buildYear;
        this.shipName = shipName;
    }

    public Ferry(Ferry other){
        super(other);
        this.buildYear=other.getBuildYear();
        this.shipName=other.getShipName();
    }

    public int getBuildYear() {
        return buildYear;
    }

    public String getShipName() {
        return shipName;
    }

    public void setBuildYear(int buildYear) {
        this.buildYear = buildYear;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }
}

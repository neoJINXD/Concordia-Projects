package publicTransport;

public class PublicTransportation{
    double ticketPrice;
    int nbOfStops;

    public PublicTransportation(){
        this.ticketPrice=0;
        this.nbOfStops=0;
    }
    public PublicTransportation(double price, int stops){
        this.ticketPrice=price;
        this.nbOfStops=stops;
    }
    public PublicTransportation(PublicTransportation other){
        this.ticketPrice=other.getTicketPrice();
        this.nbOfStops=other.getNbOfStops();
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public int getNbOfStops() {
        return nbOfStops;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public void setNbOfStops(int nbOfStops) {
        this.nbOfStops = nbOfStops;
    }

    
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PublicTransportation that = (PublicTransportation) o;

        if (Double.compare(that.ticketPrice, ticketPrice) != 0) return false;
        return nbOfStops == that.nbOfStops;
    }


    public String toString() {
        return "PublicTransportation{" +
                "ticketPrice=" + ticketPrice +
                ", nbOfStops=" + nbOfStops +
                '}';
    }
}

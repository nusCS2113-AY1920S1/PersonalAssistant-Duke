package duke.model.locations;

import duke.commons.enumerations.Constraint;
import duke.model.locations.Venue;

public abstract class RouteNode extends Venue {
    private Constraint type;
    private String address;
    private String description;

    /**
     * Creates a RouteNode object.
     * @param type The type of transport of node.
     * @param latitude The latitude of node.
     * @param longitude The longitude of node.
     * @param address The name of node.
     * @param description The description of node.
     */
    public RouteNode(Constraint type, String address, String description, double latitude, double longitude) {
        super(address, latitude, longitude, 0, 0);
        this.type = type;
        this.address = address;
        this.description = description;
    }

    @Override
    public String toString() {
        return address + " (" + getLatitude() + ", " + getLongitude() + ")";
    }

    public Constraint getType() {
        return type;
    }

    public double getLatitude() {
        return super.getLatitude();
    }

    public double getLongitude() {
        return super.getLongitude();
    }

    public String getCoordinate() {
        return getLatitude() + ", " + getLongitude();
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public void setLatitude(int latitude) {
        this.setLatitude(latitude);
    }

    public void setLongitude(int latitude) {
        this.setLatitude(latitude);
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setType(Constraint type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

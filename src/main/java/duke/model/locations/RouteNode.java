package duke.model.locations;

import duke.commons.enumerations.Constraint;

/**
 * Class representing a Node in a Route.
 */
public abstract class RouteNode extends Venue {
    private Constraint type;
    private String address;
    private String description;

    /**
     * Creates a RouteNode object.
     *
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

    public Constraint getType() {
        return type;
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

    public double getLatitude() {
        return super.getLatitude();
    }

    public double getLongitude() {
        return super.getLongitude();
    }

    /**
     * Sets the latitude of the RouteNode.
     *
     * @param latitude The latitude to set.
     */
    public void setLatitude(int latitude) {
        super.setLatitude(latitude);
    }

    /**
     * Sets the longitude of the RouteNode.
     *
     * @param longitude The latitude to set.
     */
    public void setLongitude(int longitude) {
        super.setLongitude(longitude);
    }

    /**
     * Sets the address of the RouteNode.
     *
     * @param address The latitude to set.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Sets the type of the RouteNode.
     *
     * @param type The latitude to set.
     */
    public void setType(Constraint type) {
        this.type = type;
    }

    /**
     * Sets the description of the RouteNode.
     *
     * @param description The latitude to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Converts the RouteNode to a String format.
     *
     * @return The RouteNode in String format.
     */
    @Override
    public String toString() {
        return address + " (" + getLatitude() + ", " + getLongitude() + ")";
    }
}

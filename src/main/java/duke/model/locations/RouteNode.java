package duke.model.locations;

import duke.commons.enumerations.Constraint;

/**
 * Represents one transportation node - bus stop / train station.
 */
public abstract class RouteNode extends Venue {
    private Constraint type;
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
        this.description = description;
    }

    /**
     * Gets the RouteNode details for display in Route Selector.
     *
     * @return The details of the RouteNode.
     */
    public String getDisplayInfo() {
        return getAddress() + "\n" + getDescription() + "\n" + "(" + getLatitude() + ", " + getLongitude() + ")";
    }

    /**
     * Gets the coordinates of the RouteNode (latitude, longitude).
     *
     * @return The Coordinates.
     */
    public String getCoordinate() {
        return getLatitude() + ", " + getLongitude();
    }

    /**
     * Gets the type of Constraint (bus / train / custom).
     *
     * @return type The type of the RouteNode.
     */
    public Constraint getType() {
        return type;
    }

    /**
     * Gets the description of the RouteNode.
     *
     * @return description The description of the RouteNode.
     */
    public String getDescription() {
        if (!(description == null || description.equals("null"))) {
            return description;
        } else {
            return "";
        }
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
        return getAddress() + " (" + getLatitude() + ", " + getLongitude() + ")";
    }
}

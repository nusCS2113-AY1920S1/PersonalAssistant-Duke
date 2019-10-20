package duke.model.transports;

import duke.commons.enumerations.Constraint;
import duke.model.locations.Venue;

public class RouteNode extends Venue {
    private Constraint type;
    private String name;
    private String description;

    /**
     * Creates a RouteNode object.
     * @param type The type of transport of node.
     * @param latitude The latitude of node.
     * @param longitude The longitude of node.
     * @param name The name of node.
     * @param description The description of node.
     */
    public RouteNode(Constraint type, String name, String description, double latitude, double longitude) {
        super(name, latitude, longitude, 0, 0);
        this.type = type;
        this.name = name;
        this.description = description;
    }

    /**
     * Alternative constructor of a routeNode object, with address name.
     * @param type The type of transport of node.
     * @param latitude The latitude of node.
     * @param longitude The longitude of node.
     * @param name The name of node.
     * @param description The description of node.
     */
    public RouteNode(Constraint type, String name, String address, String description,
                     double latitude, double longitude) {
        super(address, latitude, longitude, 0, 0);
        this.type = type;
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return name + " (" + getLatitude() + ", " + getLongitude() + ")";
    }

    public Constraint getType() {
        return type;
    }

    public double getLatitude() {
        return getLatitude();
    }

    public double getLongitude() {
        return getLongitude();
    }

    public String getCoordinate() {
        return getLatitude() + ", " + getLongitude();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}

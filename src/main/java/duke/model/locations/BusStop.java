package duke.model.locations;

import duke.commons.enumerations.Constraint;

import java.util.HashSet;
import java.util.Set;

public class BusStop extends RouteNode {
    private String busCode;
    private String description;
    private Set<String> buses;

    /**
     * Creates a BusStop object.
     *
     */
    public BusStop(String busCode, String description, String address, double latitude, double longitude) {
        super(Constraint.valueOf("BUS"), address, busCode, latitude, longitude);
        this.busCode = busCode;
        this.description = description;
        this.buses = new HashSet<>();
    }

    public String getDescription() {
        return description;
    }

    public void addBuses(String bus) {
        buses.add(bus);
    }

    public Set<String> getBuses() {
        return buses;
    }

    public String getBusCode() {
        return busCode;
    }
}


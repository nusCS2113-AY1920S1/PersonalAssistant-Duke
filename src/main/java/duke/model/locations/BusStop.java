package duke.model.locations;

import duke.commons.enumerations.Constraint;
import duke.model.Model;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
<<<<<<< HEAD
 * Class representing a Bus Stop.
=======
 * Represents a bus stop.
>>>>>>> 20921c7839efb1481af98f3b5c7e0465ff5bb57e
 */
public class BusStop extends RouteNode {
    private String busCode;
    private Set<String> buses;

    /**
     * Creates a BusStop object.
     *
     * @param busCode The bus code.
     * @param address The address.
     * @param description The description.
     * @param latitude The latitude.
     * @param longitude The longitude.
     */
    public BusStop(String busCode, String address, String description, double latitude, double longitude) {
        super(Constraint.valueOf("BUS"), address, description, latitude, longitude);
        this.busCode = busCode;
        this.buses = new HashSet<>();
    }

    public Set<String> getBuses() {
        return buses;
    }

    public String getBusCode() {
        return busCode;
    }

    /**
     * Adds a bus in the form of a String to buses.
     *
     * @param bus The bus to add.
     */
    public void addBuses(String bus) {
        buses.add(bus);
    }

    /**
     * Fetches data from model and updates the bus stop.
     *
     * @param model The model.
     */
    public void fetchData(Model model) {
        HashMap<String, BusStop> allBus = model.getMap().getBusStopMap();
        if (allBus.containsKey(this.busCode)) {
            this.setAddress(allBus.get(this.busCode).getAddress());
            this.setLatitude(allBus.get(this.busCode).getLatitude());
            this.setLongitude(allBus.get(this.busCode).getLongitude());
        }
    }
}


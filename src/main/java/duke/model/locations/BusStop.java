package duke.model.locations;

import duke.commons.enumerations.Constraint;
import duke.model.Model;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class BusStop extends RouteNode {
    private String busCode;
    private Set<String> buses;

    /**
     * Creates a BusStop object.
     *
     */
    public BusStop(String busCode, String address, String description, double latitude, double longitude) {
        super(Constraint.valueOf("BUS"), address, description, latitude, longitude);
        this.busCode = busCode;
        this.buses = new HashSet<>();
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

    /**
     * Fetch data from model.
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


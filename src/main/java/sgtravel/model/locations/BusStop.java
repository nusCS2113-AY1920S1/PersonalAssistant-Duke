package sgtravel.model.locations;

import sgtravel.commons.enumerations.Constraint;
import sgtravel.commons.exceptions.QueryFailedException;
import sgtravel.model.Model;
import sgtravel.model.transports.TransportationMap;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a bus stop.
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

    /**
     * Alternative constructor which automatically uses fetchData().
     *
     * @param busCode The bus code.
     * @param model The model object containing information about the user.
     */
    public BusStop(String busCode, Model model) throws QueryFailedException {
        super(Constraint.valueOf("BUS"), "", "", 0, 0);
        this.busCode = busCode;
        fetchData(model);
    }

    /**
     * Gets the Set of bus services of the bus stop in String.
     *
     * @return buses The Set of bus services in String.
     */
    public Set<String> getBuses() {
        return buses;
    }

    /**
     * Gets the bus code of the Bus Stop in String.
     *
     * @return busCode The bus code of the bus stop in String.
     */
    public String getBusCode() {
        return busCode;
    }

    /**
     * Adds a bus service in the form of a String to buses.
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
     * @throws QueryFailedException If the bus stop is not found.
     */
    public void fetchData(Model model) throws QueryFailedException {
        TransportationMap map = model.getMap();
        HashMap<String, BusStop> allBus = map.getBusStopMap();

        if (allBus.containsKey(busCode)) {
            BusStop busStop = allBus.get(busCode);
            this.setAddress(busStop.getAddress());
            this.setDescription(busStop.getDescription());
            this.setLatitude(busStop.getLatitude());
            this.setLongitude(busStop.getLongitude());
            return;
        }

        throw new QueryFailedException(busCode);
    }
}


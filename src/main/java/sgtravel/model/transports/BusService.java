package sgtravel.model.transports;

import sgtravel.commons.enumerations.Direction;

import java.util.ArrayList;

/**
 * Represents a Bus and its route.
 */
public class BusService {
    private String bus;
    private ArrayList<String> forward;
    private ArrayList<String> backward;

    /**
     * Create bus object.
     */
    public BusService(String bus) {
        this.bus = bus;
        this.forward = new ArrayList<>();
        this.backward = new ArrayList<>();
    }

    /**
     * Add the bus stop code to the route which the bus would travel to.
     *
     * @param busCode Code of bus stop
     * @param direction Direction of travel to next bus stop
     */
    public void addRoute(String busCode, Direction direction) {
        if (direction == Direction.FORWARD) {
            this.forward.add(busCode);
        } else {
            this.backward.add(busCode);
        }
    }

    /**
     * Gets the route of bus in direction indicated.
     *
     * @param direction Direction of travel.
     * @return All bus stops in direction of travel in an ArrayList.
     */
    public ArrayList<String> getDirection(Direction direction) {
        if (direction == Direction.FORWARD) {
            return forward;
        } else {
            return backward;
        }
    }

    /**
     * Gets the bus service number.
     *
     * @return bus The bus service number.
     */
    public String getBus() {
        return bus;
    }
}


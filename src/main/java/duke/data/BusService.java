package duke.data;

import java.util.ArrayList;

public class BusService {
    private ArrayList<String> direction1;
    private ArrayList<String> direction2;

    /**
     * Create bus object.
     */
    public BusService() {
        this.direction1 = new ArrayList<>();
        this.direction2 = new ArrayList<>();
    }

    /**
     * Add the bus stop code to the route which the bus would travel to.
     *
     * @param busCode Code of bus stop
     * @param direction Direction of travel to next bus stop
     */
    public void addRoute(String busCode, int direction) {
        if (direction == 1) {
            this.direction1.add(busCode);
        } else {
            this.direction2.add(busCode);
        }
    }

    /**
     * get the route of bus in direction indicated.
     * @param direction direction of travel
     * @return All bus stop in direction of travel
     */
    public ArrayList<String> getDirection(int direction) {
        if (direction == 1) {
            return direction1;
        } else {
            return direction2;
        }
    }
}

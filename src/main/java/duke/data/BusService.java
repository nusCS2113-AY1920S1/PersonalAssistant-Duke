package duke.data;

import java.util.ArrayList;

public class BusService {
    private String service;
    private ArrayList<String> direction1;
    private ArrayList<String> direction2;

    /**
     * Create bus object.
     */
    public BusService(String service) {
        this.service = service;
        this.direction1 = new ArrayList<>();
        this.direction2 = new ArrayList<>();
    }

    /**
     * Add the bus stop code to the route which the bus would travel to.
     *
     * @param buscode Code of bus stop
     * @param direction Direction of travel to next bus stop
     */
    public void addRoute(String buscode, int direction) {
        if (direction == 1) {
            this.direction1.add(buscode);
        } else {
            this.direction2.add(buscode);
        }
    }

}

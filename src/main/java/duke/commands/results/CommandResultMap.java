package duke.commands.results;

import duke.model.locations.BusStop;

import java.util.ArrayList;

public class CommandResultMap extends CommandResult {
    private ArrayList<BusStop> route;

    /**
     * Constructs a basic CommandResultMap object.
     *
     * @param message Message for ui to display.
     */
    public CommandResultMap(String message) {
        this.message = message;
    }


    public ArrayList<BusStop> getRoute() {
        return route;
    }

    public void setRoute(ArrayList<BusStop> route) {
        this.route = route;
    }
}

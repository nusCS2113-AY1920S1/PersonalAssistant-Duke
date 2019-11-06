package sgtravel.logic.commands.results;

import sgtravel.model.locations.RouteNode;
import sgtravel.model.transports.Route;

import java.util.ArrayList;

/**
 * Defines the command result of a command needing a map.
 */
public class CommandResultMap extends CommandResult implements Routeable {
    private ArrayList<RouteNode> route;

    /**
     * Constructs a basic CommandResultMap object.
     *
     * @param message Message for ui to display.
     */
    public CommandResultMap(String message) {
        this.message = message;
    }

    /**
     * Alternative constructor for a basic CommandResultMap object.
     *
     * @param route The route object.
     */
    public CommandResultMap(Route route) {
        this.route = route.getNodes();
    }

    @Override
    public ArrayList<RouteNode> getRoute() {
        return route;
    }

    @Override
    public void setRoute(ArrayList<RouteNode> route) {
        this.route = route;
    }
}

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
     * @param route The route object.
     */
    public CommandResultMap(Route route) {
        this.route = route.getNodes();
    }

    /**
     * Gets the Route represented by the Map.
     *
     * @return route The Route represented by the Map.
     */
    @Override
    public ArrayList<RouteNode> getRoute() {
        return route;
    }

    /**
     * Sets the Route in the Map.
     *
     * @param route The Route to set.
     */
    @Override
    public void setRoute(ArrayList<RouteNode> route) {
        this.route = route;
    }
}

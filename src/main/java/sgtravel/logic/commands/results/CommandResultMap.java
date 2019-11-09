package sgtravel.logic.commands.results;

import sgtravel.model.locations.RouteNode;
import sgtravel.model.transports.Route;

import java.util.ArrayList;

/**
 * Defines the command result of a command needing a map.
 */
public class CommandResultMap extends CommandResult implements Routeable {
    private ArrayList<RouteNode> routes;

    /**
     * Constructs a basic CommandResultMap object.
     *
     * @param routes The route object.
     */
    public CommandResultMap(Route routes) {
        this.routes = routes.getNodes();
    }

    /**
     * Gets the Route represented by the Map.
     *
     * @return route The Route represented by the Map.
     */
    @Override
    public ArrayList<RouteNode> getRoutes() {
        return routes;
    }

    /**
     * Sets the Route in the Map.
     *
     * @param routes The Route to set.
     */
    @Override
    public void setRoutes(ArrayList<RouteNode> routes) {
        this.routes = routes;
    }
}

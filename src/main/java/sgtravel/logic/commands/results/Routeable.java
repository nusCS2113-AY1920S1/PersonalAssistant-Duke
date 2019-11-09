package sgtravel.logic.commands.results;

import sgtravel.model.locations.RouteNode;

import java.util.ArrayList;

/**
 * Interface representing a route.
 */
public interface Routeable {

    /**
     * Gets the ArrayList of Routes.
     *
     * @return The ArrayList of Routes.
     */
    ArrayList<RouteNode> getRoutes();

    /**
     * Sets the ArrayList of Routes.
     *
     * @param routes The ArrayList of Routes.
     */
    void setRoutes(ArrayList<RouteNode> routes);
}

package sgtravel.logic.commands.results;

import sgtravel.model.locations.RouteNode;

import java.util.ArrayList;

/**
 * Interface representing a route.
 */
public interface Routeable {

    ArrayList<RouteNode> getRoute();

    void setRoute(ArrayList<RouteNode> route);
}

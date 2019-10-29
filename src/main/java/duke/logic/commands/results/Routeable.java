package duke.logic.commands.results;

import duke.model.locations.RouteNode;

import java.util.ArrayList;

/**
 * Interface representing a route.
 */
public interface Routeable {

    ArrayList<RouteNode> getRoute();

    void setRoute(ArrayList<RouteNode> route);
}

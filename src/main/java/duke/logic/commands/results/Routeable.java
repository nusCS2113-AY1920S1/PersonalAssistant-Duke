package duke.logic.commands.results;

import duke.model.locations.RouteNode;
import duke.model.locations.Venue;
import duke.model.transports.Route;

import java.util.ArrayList;

/**
 * Interface representing a route.
 */
public interface Routeable {

    ArrayList<RouteNode> getRoute();

    void setRoute(ArrayList<RouteNode> route);
}

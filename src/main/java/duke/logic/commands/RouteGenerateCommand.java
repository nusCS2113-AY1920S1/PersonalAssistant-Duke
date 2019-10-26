package duke.logic.commands;

import duke.commons.Messages;
import duke.commons.enumerations.Constraint;
import duke.commons.exceptions.ApiNullRequestException;
import duke.commons.exceptions.ApiTimeoutException;
import duke.commons.exceptions.CorruptedFileException;
import duke.commons.exceptions.FileNotSavedException;
import duke.commons.exceptions.QueryFailedException;
import duke.commons.exceptions.RouteDuplicateException;
import duke.commons.exceptions.RouteNodeDuplicateException;
import duke.logic.PathFinder;
import duke.logic.api.ApiParser;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;
import duke.model.locations.BusStop;
import duke.model.locations.RouteNode;
import duke.model.locations.TrainStation;
import duke.model.locations.Venue;
import duke.model.transports.Route;

import java.util.ArrayList;
import java.util.Collections;

import static duke.logic.PathFinder.generateInbetweenNodes;

/**
 * Generates a route based on 2 locations.
 */
public class RouteGenerateCommand extends Command {
    private String startPoint;
    private String endPoint;
    private Constraint type;

    /**
     * Creates a new RouteGenerateCommand with the given node.
     *
     * @param startPoint The starting point of the route.
     * @param endPoint The ending point of the route.
     */
    public RouteGenerateCommand(String startPoint, String endPoint, Constraint type) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.type = type;
    }

    @Override
    public CommandResultText execute(Model model) throws ApiNullRequestException, ApiTimeoutException,
            QueryFailedException, RouteNodeDuplicateException, FileNotSavedException, CorruptedFileException,
            RouteDuplicateException {
        Venue startVenue = ApiParser.getLocationSearch(startPoint);
        Venue endVenue = ApiParser.getLocationSearch(endPoint);
        PathFinder pathFinder = new PathFinder(model.getMap());
        ArrayList<Venue> venueList = pathFinder.execute(startVenue, endVenue, type);
        Collections.reverse(venueList);

        Route route = new Route(startPoint + " to " + endPoint + "  (" + type.toString() + ")",
                "by " + type.toString());
        Venue previousVenue = null;

        for (Venue venue: venueList) {
            if ((previousVenue instanceof BusStop && venue instanceof BusStop)
                    || (previousVenue instanceof TrainStation && venue instanceof TrainStation)) {
                ArrayList<Venue> inBetweenNodes = PathFinder.generateInbetweenNodes(previousVenue, venue, model);
                for (Venue inbetweenVenue: inBetweenNodes) {
                    route.addNode((RouteNode) inbetweenVenue);
                }
            }

            if (venue instanceof BusStop || venue instanceof TrainStation) {
                route.addNode((RouteNode) venue);
            } else {
                route.addNode(PathFinder.generateCustomRouteNode(venue));
            }

            previousVenue = venue;
        }

        for (RouteNode routeNode: route.getNodes()) {
            if (routeNode instanceof BusStop) {
                ((BusStop) routeNode).fetchData(model);
            } else if (routeNode instanceof TrainStation) {
                ((TrainStation) routeNode).fetchData(model);
            }
        }

        model.getRoutes().add(route);
        model.save();

        return new CommandResultText(Messages.PROMPT_ROUTE_GENERATE_SUCCESS + "\n" + route.getName());
    }
}

package sgtravel.logic.commands;

import sgtravel.commons.Messages;
import sgtravel.commons.enumerations.Constraint;
import sgtravel.commons.exceptions.ApiException;
import sgtravel.commons.exceptions.FileNotSavedException;
import sgtravel.commons.exceptions.QueryFailedException;
import sgtravel.commons.exceptions.DuplicateRouteException;
import sgtravel.commons.exceptions.RouteGenerateFailException;
import sgtravel.commons.exceptions.DuplicateRouteNodeException;
import sgtravel.commons.exceptions.UnknownConstraintException;
import sgtravel.logic.PathFinder;
import sgtravel.logic.api.ApiParser;
import sgtravel.logic.commands.results.CommandResultText;
import sgtravel.model.Model;
import sgtravel.model.locations.BusStop;
import sgtravel.model.locations.CustomNode;
import sgtravel.model.locations.RouteNode;
import sgtravel.model.locations.TrainStation;
import sgtravel.model.locations.Venue;
import sgtravel.model.transports.Route;

import java.util.ArrayList;
import java.util.Collections;

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
    public CommandResultText execute(Model model) throws ApiException, UnknownConstraintException,
            RouteGenerateFailException, QueryFailedException, DuplicateRouteNodeException,
            DuplicateRouteException, FileNotSavedException {
        Venue startVenue = ApiParser.getLocationSearch(startPoint);
        Venue endVenue = ApiParser.getLocationSearch(endPoint);
        PathFinder pathFinder = new PathFinder(model.getMap());

        if (type == Constraint.MIXED) {
            throw new UnknownConstraintException();
        }

        ArrayList<Venue> venueList = pathFinder.execute(startVenue, endVenue, type);

        try {
            if (type == Constraint.BUS) {
                Collections.reverse(venueList);
            }
        } catch (NullPointerException e) {
            throw new RouteGenerateFailException();
        }

        Route route = new Route(startPoint + " to " + endPoint + "  (" + type.toString() + ")", "");
        Venue previousVenue = null;

        for (Venue venue: venueList) {
            if ((previousVenue instanceof BusStop && venue instanceof BusStop)
                    || (previousVenue instanceof TrainStation && venue instanceof TrainStation)) {
                ArrayList<Venue> inBetweenNodes = PathFinder.generateInbetweenNodes(previousVenue, venue, model);

                for (Venue inbetweenVenue: inBetweenNodes) {
                    try {
                        if (inbetweenVenue instanceof CustomNode) {
                            String description = route.getDescription();
                            description += inbetweenVenue.getAddress() + "/";
                            route.setDescription(description);
                        }
                        route.add((RouteNode) inbetweenVenue);
                    } catch (DuplicateRouteNodeException e) {
                        route = pruneDuplicateRoute(route, inbetweenVenue);
                    }
                }
            }

            if (venue instanceof BusStop || venue instanceof TrainStation) {
                route.add((RouteNode) venue);
            } else {
                route.add(PathFinder.generateCustomRouteNode(venue));
            }

            previousVenue = venue;
        }

        for (RouteNode node : route.getNodes()) {
            if (node instanceof TrainStation) {
                node.setAddress(node.getDescription());
            }
        }

        updateRouteNodes(route, model);

        model.getRoutes().add(route);
        model.save();

        return new CommandResultText(Messages.PROMPT_ROUTE_ADD_SUCCESS + "\n" + route.getName());
    }

    /**
     * Finds the duplicate RouteNode in a route, and removes the unneeded routes.
     *
     * @param route The route object.
     * @param target The RouteNode that has a duplicate.
     * @return The route object.
     * @throws RouteGenerateFailException If the Route fails to generate.
     */
    private Route pruneDuplicateRoute(Route route, Venue target) throws RouteGenerateFailException {
        try {
            for (int i = route.size() - 1; i >= 0; i--) {
                if (!route.getNode(i).equals(target)) {
                    route.remove(i);
                } else {
                    break;
                }
            }

            return route;
        } catch (IndexOutOfBoundsException e) {
            throw new RouteGenerateFailException();
        }
    }

    /**
     * Updates the RouteNodes in a route by fetching data from the model if possible.
     *
     * @param route The Route object.
     * @param model The model.
     * @throws RouteGenerateFailException If the Route fails to generate.
     */
    private void updateRouteNodes(Route route, Model model) throws RouteGenerateFailException {
        try {
            for (RouteNode routeNode : route.getNodes()) {
                if (routeNode instanceof BusStop) {
                    ((BusStop) routeNode).fetchData(model);
                } else if (routeNode instanceof TrainStation) {
                    ((TrainStation) routeNode).fetchData(model);
                }
            }
        } catch (QueryFailedException e) {
            throw new RouteGenerateFailException();
        }
    }
}

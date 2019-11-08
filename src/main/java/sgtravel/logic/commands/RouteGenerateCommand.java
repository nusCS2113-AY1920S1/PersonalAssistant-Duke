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
import sgtravel.model.lists.RouteList;
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

    /**
     * Executes this command and creates a new Route.
     *
     * @param model The model object containing information about the user.
     * @return The CommandResultText.
     * @throws UnknownConstraintException If the constraint is unknown.
     * @throws RouteGenerateFailException If the Route fails to generate.
     * @throws DuplicateRouteException If the new Route exists.
     * @throws FileNotSavedException If the file cannot be saved.
     */
    @Override
    public CommandResultText execute(Model model) throws UnknownConstraintException,
            RouteGenerateFailException, DuplicateRouteException, FileNotSavedException {
        try {
            ArrayList<Venue> venueList = generateRoute(model);

            try {
                if (type == Constraint.BUS) {
                    Collections.reverse(venueList);
                }
            } catch (NullPointerException e) {
                throw new RouteGenerateFailException();
            }

            Route route = new Route(startPoint + " to " + endPoint + "  (" + type.toString() + ")", "");
            Venue previousVenue = null;
            try {
                for (Venue venue : venueList) {
                    addInbetweenNodes(model, route, previousVenue, venue);
                    addEndingNode(route, venue);
                    previousVenue = venue;
                }
            } catch (QueryFailedException | DuplicateRouteNodeException e) {
                throw new RouteGenerateFailException();
            }

            RouteList routes = model.getRoutes();
            routes.add(route);
            model.save();

            return new CommandResultText(Messages.PROMPT_ROUTE_ADD_SUCCESS + "\n" + route.getName());
        } catch (ApiException e) {
            return new CommandResultText(Messages.ERROR_API_FAIL);
        }
    }

    /**
     * Generates a Route via PathFinder.
     *
     * @param model The model object containing information about the user.
     * @return The Route that is generated.
     * @throws ApiException If the API call fails.
     * @throws RouteGenerateFailException If the Route fails to generate.
     */
    private ArrayList<Venue> generateRoute(Model model) throws ApiException, RouteGenerateFailException {
        Venue startVenue = ApiParser.getLocationSearch(startPoint);
        Venue endVenue = ApiParser.getLocationSearch(endPoint);
        PathFinder pathFinder = new PathFinder(model.getMap());

        return pathFinder.execute(startVenue, endVenue, type);
    }

    /**
     * Adds the ending RouteNode to the Route.
     *
     * @param route The Route to add to.
     * @param venue The Venue to add to.
     * @throws DuplicateRouteNodeException If the RouteNode is a duplicate.
     */
    private void addEndingNode(Route route, Venue venue) throws DuplicateRouteNodeException {
        if (venue instanceof BusStop || venue instanceof TrainStation) {
            route.add((RouteNode) venue);
        } else {
            route.add(PathFinder.generateCustomRouteNode(venue));
        }
    }

    /**
     * Adds any RouteNodes that are inbetween 2 Venues.
     *
     * @param model The model object containing information about the user.
     * @param route The Route.
     * @param startVenue The starting Venue.
     * @param endVenue The ending Venue.
     * @throws QueryFailedException If the RouteNode cannot be found in local storage.
     * @throws RouteGenerateFailException If the Route fails to generate.
     */
    private void addInbetweenNodes(Model model, Route route, Venue startVenue, Venue endVenue)
            throws QueryFailedException, RouteGenerateFailException {
        if ((startVenue instanceof BusStop && endVenue instanceof BusStop)
                || (startVenue instanceof TrainStation && endVenue instanceof TrainStation)) {
            ArrayList<Venue> inBetweenNodes = PathFinder.generateInbetweenNodes(startVenue, endVenue, model);

            for (Venue inbetweenVenue : inBetweenNodes) {
                try {
                    addNodeToRoute(route, (RouteNode) inbetweenVenue, model);
                } catch (DuplicateRouteNodeException e) {
                    pruneDuplicateRoute(route, inbetweenVenue);
                }
            }
        }
    }

    /**
     * Finds the duplicate RouteNode in a route, and removes the unneeded routes.
     *
     * @param route The route object.
     * @param target The RouteNode that has a duplicate.
     * @throws RouteGenerateFailException If the Route fails to generate.
     */
    private void pruneDuplicateRoute(Route route, Venue target) throws RouteGenerateFailException {
        try {
            for (int i = route.size() - 1; i >= 0; i--) {
                RouteNode node = route.getNode(i);
                if (!node.equals(target)) {
                    route.remove(i);
                } else {
                    break;
                }
            }

        } catch (IndexOutOfBoundsException e) {
            throw new RouteGenerateFailException();
        }
    }

    /**
     * Updates the RouteNode in a route by fetching data from the model if possible.
     *
     * @param node The RouteNode object.
     * @param model The model.
     * @throws RouteGenerateFailException If the Route fails to generate.
     */
    private void updateRouteNode(RouteNode node, Model model) throws RouteGenerateFailException {
        try {
            if (node instanceof BusStop) {
                ((BusStop) node).fetchData(model);
            } else if (node instanceof TrainStation) {
                ((TrainStation) node).fetchData(model);
            }
        } catch (QueryFailedException e) {
            throw new RouteGenerateFailException();
        }
    }

    /**
     * Adds a RouteNode to a Route, and if it is a CustomNode, update the description instead.
     *
     * @param route The Route to add the RouteNode to.
     * @param node The RouteNode.
     * @param model The model object containing information about the user.
     * @throws RouteGenerateFailException If the Route fails to generate.
     * @throws DuplicateRouteNodeException If there is a duplicate RouteNode.
     */
    private void addNodeToRoute(Route route, RouteNode node, Model model) throws RouteGenerateFailException,
            DuplicateRouteNodeException {
        if (node instanceof CustomNode) {
            String description = route.getDescription();
            description += node.getAddress() + "/";
            route.setDescription(description);
        } else {
            if (node instanceof TrainStation) {
                node.setAddress(node.getDescription());
            }
            updateRouteNode(node, model);
            route.add(node);
        }
    }
}

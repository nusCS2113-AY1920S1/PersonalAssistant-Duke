package duke.logic.commands;

import duke.commons.exceptions.QueryOutOfBoundsException;
import duke.logic.commands.results.CommandResultMap;
import duke.commons.enumerations.Constraint;
import duke.logic.PathFinder;
import duke.model.Model;
import duke.model.Event;
import duke.model.locations.RouteNode;
import duke.model.locations.Venue;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Finds a path between two Venues.
 */
public class FindPathCommand extends Command {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private Constraint constraint;
    private int startPointIndex;
    private int endPointIndex;
    private static final String MESSAGE_FIND_PATH = "Path is found, map is opening...";

    /**
     * Constructor to initialise FindPathCommand.
     *
     * @param constraint The constraint of the location request.
     * @param startPointIndex Index of starting location of trip.
     * @param endPointIndex Index of ending location of trip.
     */
    public FindPathCommand(String constraint, int startPointIndex, int endPointIndex) {
        switch (constraint) {
        case "onlyMRT":
            this.constraint = Constraint.MRT;
            break;
        case "onlyBus":
            this.constraint = Constraint.BUS;
            break;
        /*
        case "Hybrid":
            this.constraint = Constraint.MIXED;
            break;
         */
        default:
            this.constraint = Constraint.CAR;
            break;
        }
        this.endPointIndex = endPointIndex;
        this.startPointIndex = startPointIndex;
        logger.log(Level.FINE, constraint + startPointIndex + " " + endPointIndex);
    }

    /**
     * Executes this command and returns a map result.
     *
     * @param model The model object containing event list.
     */
    @Override
    public CommandResultMap execute(Model model) throws QueryOutOfBoundsException {
        try {
            Event startPoint = model.getEvents().get(startPointIndex);
            Venue startLocation = startPoint.getLocation();
            Event endPoint = model.getEvents().get(endPointIndex);
            Venue endLocation = endPoint.getLocation();

            // calculate the shortest path using algorithm with 2 locations as parameters

            PathFinder pathFinder = new PathFinder(model.getMap());
            ArrayList<Venue> route = pathFinder.execute(startLocation, endLocation, this.constraint);
            ArrayList<RouteNode> nodes = pathFinder.convertToRouteNode(route);

            CommandResultMap commandResult = new CommandResultMap(MESSAGE_FIND_PATH);
            commandResult.setRoute(nodes);
            return commandResult;
        } catch (IndexOutOfBoundsException e) {
            throw new QueryOutOfBoundsException("EVENT");
        }
    }
}

package duke.logic.commands;

import duke.commons.exceptions.QueryOutOfBoundsException;
import duke.logic.commands.results.CommandResultMap;
import duke.commons.enumerations.Constraint;
import duke.logic.PathFinder;
import duke.commons.exceptions.DukeException;
import duke.model.Model;
import duke.model.Event;
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
        case "Hybrid":
            this.constraint = Constraint.MIXED;
            break;
        default:
            this.constraint = Constraint.CAR;
            break;
        }
        this.endPointIndex = endPointIndex;
        this.startPointIndex = startPointIndex;
        logger.log(Level.INFO, constraint + startPointIndex + " " + endPointIndex);
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     */
    @Override
    public CommandResultMap execute(Model model) throws DukeException {
        try {
            Event startPoint = model.getEvents().get(startPointIndex);
            Venue startLocation = startPoint.getLocation();
            Event endPoint = model.getEvents().get(endPointIndex);
            Venue endLocation = endPoint.getLocation();

            // calculate the shortest path using algorithm with 2 locations as parameters

            PathFinder pathFinder = new PathFinder(model.getMap());
            ArrayList<Venue> route = pathFinder.execute(startLocation, endLocation, this.constraint);

            CommandResultMap commandResult = new CommandResultMap(MESSAGE_FIND_PATH);
            commandResult.setRoute(route);
            return commandResult;
        } catch (IndexOutOfBoundsException e) {
            throw new QueryOutOfBoundsException("EVENT");
        }
    }
}

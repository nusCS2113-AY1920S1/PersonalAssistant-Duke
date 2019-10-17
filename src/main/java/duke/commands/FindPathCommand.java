package duke.commands;

import duke.commands.results.CommandResultMap;
import duke.commons.enumerations.Constraint;
import duke.logic.PathFinder;
import duke.commons.Messages;
import duke.commons.exceptions.DukeException;
import duke.logic.api.ApiConstraintParser;
import duke.model.Model;
import duke.model.TaskList;
import duke.model.events.Event;
import duke.model.events.Task;
import duke.model.locations.BusStop;
import duke.model.locations.Venue;

import java.util.ArrayList;

/**
 * Class representing a command to send the test URL connection.
 */
public class FindPathCommand extends Command {
    private Constraint constraint;
    private String startPointIndex;
    private String endPointIndex;
    private static final String MESSAGE_FIND_PATH = "Path is found, map is opening...";

    /**
     * Constructor to initialise FindPathCommand.
     *
     * @param constraint The constraint of the location request.
     * @param startPointIndex Index of starting location of trip.
     * @param endPointIndex Index of ending location of trip.
     */

    public FindPathCommand(String constraint, String startPointIndex, String endPointIndex) {
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
    }

    private static Event getHoliday(String index, TaskList t) throws DukeException {
        Task t1 = t.get(Integer.parseInt(index) - 1);
        if (t1 instanceof Event) {
            return (Event) t1;
        }
        throw new DukeException(Messages.TASK_NOT_HOLIDAY);
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     */
    @Override
    public CommandResultMap execute(Model model) throws DukeException {

        Event startPoint = getHoliday(this.startPointIndex, model.getTasks());
        Venue startLocation = startPoint.getLocation();
        Event endPoint = getHoliday(this.endPointIndex, model.getTasks());
        Venue endLocation = endPoint.getLocation();
        startPoint = ApiConstraintParser.getConstraintLocation(startPoint, this.constraint);
        endPoint = ApiConstraintParser.getConstraintLocation(endPoint, this.constraint);

        // calculate the shortest path using algorithm with 2 locations as parameters

        PathFinder pathFinder = new PathFinder(model.getMap());
        ArrayList<BusStop> route = pathFinder.execute(startLocation, endLocation);

        CommandResultMap commandResult = new CommandResultMap(MESSAGE_FIND_PATH);
        commandResult.setRoute(route);
        return commandResult;
    }

}

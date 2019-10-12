package duke.commands;

import duke.PathFinder;
import duke.commons.Enumerations.Constraint;
import duke.data.BusStop;
import duke.data.Location;
import duke.commons.Messages;
import duke.commons.exceptions.DukeException;
import duke.data.UniqueTaskList;
import duke.data.tasks.Holiday;
import duke.data.tasks.Task;
import duke.parsers.api.ApiConstraintParser;
import duke.storage.Storage;
import duke.ui.Ui;

import java.util.ArrayList;

/**
 * Class representing a command to send the test URL connection.
 */
public class FindPathCommand extends Command {
    private Constraint constraint;
    private String startPointIndex;
    private String endPointIndex;

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

    private static Holiday getHoliday(String index, UniqueTaskList t) throws DukeException {
        Task t1 = t.get(Integer.parseInt(index) - 1);
        if (t1 instanceof Holiday) {
            return (Holiday) t1;
        }
        throw new DukeException(Messages.TASK_NOT_HOLIDAY);
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param ui The user interface displaying events on the task list.
     * @param storage The duke.storage object containing task list.
     */
    @Override
    public void execute(Ui ui, Storage storage) throws DukeException {

        Holiday startPoint = getHoliday(this.startPointIndex, storage.getTasks());
        Location startLocation = startPoint.getLocation();
        Holiday endPoint = getHoliday(this.endPointIndex, storage.getTasks());
        Location endLocation = endPoint.getLocation();
        startPoint = ApiConstraintParser.getConstraintLocation(startPoint, this.constraint);
        endPoint = ApiConstraintParser.getConstraintLocation(endPoint, this.constraint);

        // calculate the shortest path using algorithm with 2 locations as parameters

        PathFinder pathFinder = new PathFinder();
        ArrayList<BusStop> route = pathFinder.execute(startLocation, endLocation);

        ui.show("Found Path:");
        for (BusStop busStop : route) {
            ui.show(busStop.getBusCode());
        }

        ui.showMap(route);
    }

}

package duke.commands;

import duke.commons.DukeException;
import duke.data.UniqueTaskList;
import duke.data.tasks.Holiday;
import duke.data.tasks.Task;
import duke.parsers.api.ApiConstraintParser;
import duke.storage.Storage;
import duke.ui.Ui;

/**
 * Class representing a command to send the test URL connection.
 */
public class FindPathCommand extends Command {
    private String constraint;
    private String startPointIndex;
    private String endPointIndex;

    /**
     * Constructor to initialise FindPathCommand.
     *
     * @param constraint The constraint of the location request
     * @param startPointIndex Index of starting location of trip
     * @param endPointIndex Index of ending location of trip
     */

    public FindPathCommand(String constraint, String startPointIndex, String endPointIndex) {
        this.constraint = constraint;
        this.endPointIndex = endPointIndex;
        this.startPointIndex = startPointIndex;
    }

    /**
     * Executes this command with given param.
     */
    @Override
    public void execute(Ui ui, Storage storage) throws DukeException {

        Holiday startPoint = getHoliday(this.startPointIndex, storage.getTasks());
        Holiday endPoint = getHoliday(this.endPointIndex, storage.getTasks());

        startPoint = ApiConstraintParser.getConstraintLocation(startPoint, this.constraint);
        endPoint = ApiConstraintParser.getConstraintLocation(endPoint, this.constraint);

        // calculate the shortest path using algorithm with 2 locations as parameters

        /* CustomAlgorithm.calculateShortestPath(endPoint.getLocation(), startPoint.getLocation()); */

        //show a map to ui

        ui.show("some map");
    }


    private static Holiday getHoliday(String index, UniqueTaskList t) throws DukeException {
        Task t1 = t.get(Integer.parseInt(index) - 1);
        if (t1 instanceof Holiday) {
            return (Holiday) t1;
        }
        throw new DukeException("Sorry the numbers you entered are not a holiday destination");
    }
}

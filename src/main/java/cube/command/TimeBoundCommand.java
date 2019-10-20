package cube.command;

import cube.task.TimeBound;
import cube.ui.*;
import cube.util.Storage;
import cube.task.TaskList;
import cube.exception.DukeException;

public class TimeBoundCommand implements Command{
    private String description;
    private String timeBound;

    /**
     * Default Constructor.
     * Calls another constructor with (null) as argument.
     */
    public TimeBoundCommand() {
        this(null);
    }

    /**
     * Constructor with one argument.
     * Calls another constructor with additional argument date == null.
     *
     * @param description the description of the task.
     */

    public TimeBoundCommand(String description) {
        this(description, null);
    }

    /**
     * Constructor with two arguments.
     *
     * @param description the description of the task.
     * @param timeBound the time period within which the task needs to be completed.
     */

    public TimeBoundCommand(String description, String timeBound) {
        this.description = description;
        this.timeBound = timeBound;
    }

    /**
     * Returns true if no error in the arguments of deadline command, otherwise throws DukeException.
     *
     * @return true if deadline is valid.
     * @throws DukeException printout warning when description or time period of the task is empty.
     */

    private boolean isValid() throws DukeException {
        if (description == null) {
            throw new DukeException(Message.EMPTY_DESCRIPTION);
        }
        if (timeBound == null) {
            throw new DukeException(Message.EMPTY_DATE); //check this pls
        }
        return true;
    }

    /**
     * Always returns false since this is not an exit command.
     *
     * @return false
     */

    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * Adds a task to the task list and stores it.
     * If the adding task command is not valid, a DukeException will be thrown.
     * UI is included to notify the user.
     *
     * @param tasks the list of tasks.
     * @param ui the user interface to output message.
     * @param storage storage of Duke.
     * @throws DukeException exception happens during storage, or if the command is not valid.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        if (isValid()) {
            TimeBound tb = new TimeBound(description, timeBound);
            tasks.add(tb);
            //storage.append(tb);
        }
    }
}

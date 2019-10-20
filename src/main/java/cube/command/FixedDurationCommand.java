package cube.command;

import cube.exception.CubeLoadingException;
import cube.task.FixedDuration;
import cube.ui.*;
import cube.util.Storage;
import cube.task.TaskList;
import cube.exception.DukeException;


public class FixedDurationCommand implements Command {
    private String description;
    private String fixedDuration;

    /**
     * Default Constructor.
     * Calls another constructor with null as argument.
     */
    public FixedDurationCommand() {
        this(null);
    }

    /**
     * Constructor with one argument.
     * Calls another constructor with additional argument date = null.
     *
     * @param description the description of the task.
     */

    public FixedDurationCommand(String description) {
        this(description, null);
    }

    /**
     * Constructor with two arguments.
     *
     * @param description the description of the task
     * @param fixedDuration the duration of the task
     */
    public FixedDurationCommand(String description, String fixedDuration) {
        this.description = description;
        this.fixedDuration = fixedDuration;
    }

    /**
     * Returns true if no error in the arguments of deadline command, otherwise throws DukeException.
     *
     * @return true if deadline is valid.
     * @throws DukeException printout warning when description or duration of the task is empty.
     */

    private boolean isValid() throws DukeException {
        if (description == null) {
            throw new DukeException(Message.EMPTY_DESCRIPTION);
        }
        if (fixedDuration == null) {
            throw new DukeException(Message.EMPTY_DURATION);
        }
        return true;
    }

    /**
     * Always returns false since this is not an exit command.
     *
     * @return false.
     */

    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * Adds a task to task list and stores it.
     * If the adding task command is not valid, a DukeException will be thrown.
     * UI is included to notify the user.
     *
     * @param tasks the list of tasks.
     * @param ui the user interface to output message.
     * @param storage storage of Duke.
     * @throws DukeException exception happens during storage, or if the command is not valid.
     */

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws CubeLoadingException, DukeException {
        if (isValid()) {
            FixedDuration fd = new FixedDuration(description, fixedDuration);
            tasks.add(fd);
            storage.append(fd);
        }
    }
}
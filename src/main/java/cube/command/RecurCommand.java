package cube.command;

import cube.exception.CubeLoadingException;
import cube.task.DoAfter;
import cube.task.Recur;
import cube.ui.*;
import cube.util.Storage;
import cube.task.TaskList;
import cube.exception.DukeException;

public class RecurCommand implements Command{
    private String description;
    private String frequency;

    /**
     * Default Constructor.
     * Calls another constructor with (null) as argument.
     */
    public RecurCommand() {
        this(null);
    }

    /**
     * Constructor with one argument.
     * Calls another constructor with additional argument date=null.
     *
     * @param description the description of the task.
     */
    public RecurCommand(String description) {
        this(description, null);
    }

    /**
     * Constructor with two argument.
     *
     * @param description the description of the task.
     * @param frequency the deadline date of the task.
     */
    public RecurCommand(String description, String frequency) {
        this.description = description;
        this.frequency = frequency;
    }

    /**
     * Returns true if no error in the arguments of deadline command, otherwise throws DukeException.
     *
     * @return true if deadline is valid.
     * @throws DukeException printout warning when description or date of the deadline command is empty.
     */
    private boolean isValid() throws DukeException{
        if (description == null) {
            throw new DukeException(Message.EMPTY_DESCRIPTION);
        }
        if (frequency == null) {
            throw new DukeException(Message.EMPTY_DATE);
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
        // interface can clearer, but parent class can reduce repeating code, which is better?
        return false;
    }

    /**
     * Adds a deadline task to task list and stores it.
     * If the adding deadline command is not valid, an DukeException will be thrown.
     * UI is included to notify the user.
     *
     * @param tasks the list of tasks.
     * @param ui the user interface to output message.
     * @param storage storage of Duke.
     * @throws DukeException exception happens during storage, or if the command is not valid.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException, CubeLoadingException {
        if (isValid()) {
            Recur a = new Recur(description, frequency);
            tasks.add(a);
            storage.append(a);
        }
    }
}
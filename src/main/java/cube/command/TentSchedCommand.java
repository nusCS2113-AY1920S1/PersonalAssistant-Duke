//@@author LL-Pengfei
/**
 * Allow tentative scheduling of a task.
 */
package cube.command;

import cube.exception.DukeException;
import cube.task.TaskList;
import cube.task.TentSched;
import cube.ui.Message;
import cube.ui.Ui;
import cube.util.Storage;

import java.util.Date;

/**
 * The Class TentSchedCommand, allowing for tentative scheduling of a task.
 */
public class TentSchedCommand implements Command {
    private String description;
    private String tentsched;
    private Date date;

    /**
     * the Default Constructor
     *
     * @param description The description of the tentatively scheduled task.
     * @param date The date of the tentatively scheduled task.
     */
    public TentSchedCommand(String description, Date date) {
        String[] temp_input = description.split(" ", 2);
        this.description = temp_input[0];
        this.tentsched = temp_input[1];
        this.date = date;
    }

    /**
     * Returns true if no error in the arguments of tentative scheduling command, otherwise throws DukeException.
     *
     * @return true if input description is valid.
     * @throws DukeException printout warning when description or
     *         the default date of the tentative scheduling command is empty.
     */
    private boolean isValid() throws DukeException {
        if (description == null) {
            throw new DukeException(Message.EMPTY_DESCRIPTION);
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
     * @param tasks   the list of tasks.
     * @param ui      the user interface to output message.
     * @param storage storage of Duke.
     * @throws DukeException exception happens during storage, or if the command is not valid.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        if (isValid()) {
            TentSched ts = new TentSched(description, tentsched, date);
            tasks.add(ts);
            //storage.append(ts);
        }
    }
}

//future update needed: need to allow rescheduling of task
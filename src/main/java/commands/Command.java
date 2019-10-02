package commands;

import controlpanel.Ui;
import controlpanel.Storage;
import controlpanel.DukeException;
import tasks.TaskList;

import java.text.ParseException;

/**
 * The abstract class to represent all types of the commands.
 */
public abstract class Command {

    /**
     * The default constructor.
     */
    public Command(){

    }

    /**
     * This method labels whether this command means ceasing the overall program.
     * @return Whether this command means ceasing the overall program.
     */
    public abstract boolean isExit();

    /**
     * The methods to execute the command based on its type.
     * @param tasks The task list object to interact with the checklist.
     * @param ui To print something needed in user interface.
     * @param storage To re-save the data in local disk if necessary.
     * @throws ParseException When there is something wrong with the parsing.
     * @throws DukeException When the command line is not qualified.
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException, ParseException;
}

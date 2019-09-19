package duke.command;

import duke.task.TaskList;
import duke.task.DukeException;
import duke.task.Ui;
import duke.task.Storage;


/**
 * Represents the various commands to be executed.
 */
public class Command {
    public boolean isExit;
    protected String input;

    /**
     * Takes in a flag to represent if it should exit and the input given by the User.
     * @param isExit True if the program should exit after running this command, false otherwise
     * @param input Input given by the user
     */
    public Command(boolean isExit, String input) {
        this.isExit = isExit;
        this.input = input;
    }

    /**
     * Main method to be executed for each subclass that will complete its necessary tasks
     * such as adding a deadline or deleting a task.
     * @param taskList Task List containing the initialized lists of the task on run
     * @param ui Ui for which any input and output will be given to
     * @param storage Storage for storing and writing of the data to disk
     * @throws DukeException thrown when any error occurs such as invalid input
     */
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {

    }
}

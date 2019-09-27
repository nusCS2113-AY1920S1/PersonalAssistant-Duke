package duke.commands;
import duke.tasks.TaskList;
import duke.ui.Ui;
import duke.storage.Storage;

/**
 * Command is the abstract base class for all the command objects
 * which allow the child class to specify which command (e.g. add, delete, etc) to use.
 * @author Ivan Andika Lie
 */
public abstract class Command {
    /**
     * this class is an abstract class the will the specific command specified
     * @param tasks the TaskList object in which the task is supposed to be added
     * @param ui the ui object to display the user interface of an "add" command
     * @param storage the storage object that stores the list of tasks
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage);

    public boolean isExit() {
        return false;
    }
}

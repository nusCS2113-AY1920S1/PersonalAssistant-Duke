package command;

import exception.DukeException;
import storage.Storage;
import task.Task;
import task.TaskList;
import ui.Ui;

/**
 * Marks a task as ignorable or not ignorable.
 *
 * @author Fauzan and Tan Yi Xiang
 * @version v1.3
 */
public class IgnoreCommand extends Command {

    private int indexOfTask;
    private boolean isIgnore;

    private static final String IGNORED = "Noted. This task has been marked as ignored:\n";
    private static final String UNIGNORED = "Noted. This task is no longer ignored:\n";

    /**
     * Initializes the different parameters when adding the location of a task.
     *
     * @param indexOfTask Holds the index of the task to be commented on.
     * @param isIgnore Holds a boolean of whether a task isIgnorable.
     */
    public IgnoreCommand(Integer indexOfTask, boolean isIgnore) {
        this.indexOfTask = indexOfTask;
        this.isIgnore = isIgnore;
    }

    /**
     * Marks a task as ignorable and saves the updated TaskList to persistent storage.
     *
     * @param tasks   Holds the list of all the tasks the user has.
     * @param storage Allows the saving of the file to persistent storage.
     */
    @Override
    public void execute(TaskList tasks, Storage storage) throws DukeException {

        if (isIndexValid(indexOfTask, tasks.getSize())) {
            if (isIgnore) {
                Task task = tasks.markAsIgnorable(indexOfTask);
                storage.saveFile(tasks.getTasks());
                Ui.printOutput(IGNORED + task.toString());
            } else {
                Task task = tasks.markAsUnignorable(indexOfTask);
                storage.saveFile(tasks.getTasks());
                Ui.printOutput(UNIGNORED + task.toString());
            }
        }
    }
}

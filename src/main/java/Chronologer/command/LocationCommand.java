package chronologer.command;

import chronologer.exception.ChronologerException;
import chronologer.storage.Storage;
import chronologer.task.Task;
import chronologer.task.TaskList;
import chronologer.ui.Ui;
import chronologer.ui.UiTemporary;

/**
 * Adds a location to an existing task as a comment.
 *
 * @author Sai Ganesh Suresh
 * @version 1.3
 */
public class LocationCommand extends Command {
    private int indexOfTask;
    private String locationOfTask;

    /**
     * Initializes the different parameters when adding the location of a task.
     *
     * @param indexOfTask Holds the index of the task to be commented on.
     * @param userInputLocation Holds the location for the task as given by the user.
     */
    public LocationCommand(int indexOfTask, String userInputLocation) {
        this.indexOfTask = indexOfTask;
        this.locationOfTask = userInputLocation;
    }

    /**
     * Adds the location to the task as a comment and saves the updated TaskList it
     * to persistent storage.
     *
     * @param tasks   Holds the list of all the tasks the user has.
     * @param storage Allows the saving of the file to persistent storage.
     */
    @Override
    public void execute(TaskList tasks, Storage storage) throws ChronologerException {
        if (isIndexValid(indexOfTask, tasks.getSize())) {
            Task taskWithLocation = tasks.addLocation(indexOfTask, locationOfTask);
            storage.saveFile(tasks.getTasks());
            UiTemporary.printOutput("Noted. Your task location has been added:" + "\n " + taskWithLocation.toString() + " "
                + taskWithLocation.getLocation());
        }
    }
}

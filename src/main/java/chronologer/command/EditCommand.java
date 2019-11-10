package chronologer.command;

import chronologer.exception.ChronologerException;
import chronologer.storage.ChronologerStateList;
import chronologer.storage.Storage;
import chronologer.task.Task;
import chronologer.task.TaskList;
import chronologer.ui.UiMessageHandler;

/**
 * Edits the user's task description.
 *
 * @author Tan Yi Xiang
 * @version v1.3
 */
public class EditCommand extends Command {

    private int indexOfTask;
    private String newDescription;

    public EditCommand(int indexOfTask, String newDescription) {
        this.indexOfTask = indexOfTask;
        this.newDescription = newDescription;
    }

    /**
     * Edits the description of a task and saves the updated TaskList to persistent storage.
     *
     * @param tasks   Holds the list of all the tasks the user has.
     * @param storage Allows the saving of the file to persistent storage.
     */
    @Override
    public void execute(TaskList tasks, Storage storage) throws ChronologerException {
        if ((isIndexValid(indexOfTask, tasks.getSize()))) {
            Task taskToEdit = tasks.editTaskDescription(indexOfTask, newDescription);
            ChronologerStateList.addState((tasks.getTasks()));
            storage.saveFile(tasks.getTasks());
            UiMessageHandler.outputMessage("Noted. Your new task description is:" + "\n "
                + taskToEdit.getDescription());
        }
    }
}

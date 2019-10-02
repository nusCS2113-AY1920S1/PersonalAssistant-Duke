package commands;

import tasks.TaskList;
import controlpanel.Ui;
import controlpanel.Storage;
import controlpanel.DukeException;

/**
 * The command which aims to delete a specific task from the checklist.
 */
public class DeleteCommand extends Command {

    private int serialNo;

    /**
     * The constructor which initializes the delete command.
     * @param index the index number of the task which will be deleted.
     */
    public DeleteCommand(int index) {
        serialNo = index;
    }

    /**
     * This method labels whether this command means ceasing the overall program.
     * @return Whether this command means ceasing the overall program.
     */
    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * This method executes the delete command. It deletes a task which is specified by its.
     * index number from the task list.
     * @param tasks The task list object to interact with the checklist.
     * @param ui To print something needed in user interface.
     * @param storage To re-save the data in local disk if necessary.
     * @throws DukeException When the command line is not qualified.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        if (serialNo > tasks.lengthOfList()) {
            throw new DukeException("The serial number of the task is Out Of Bounds!");
        }
        ui.appendToOutput(" Noted. I've removed this task:\n");
        ui.appendToOutput("  " + tasks.getTask(serialNo - 1).toString() + "\n");
        ui.appendToOutput(" Now you have " + (tasks.lengthOfList() - 1) + " tasks in the list.\n");
        tasks.removeTask(serialNo - 1);
        storage.writeTheFile(tasks.getCheckList());
    }
}

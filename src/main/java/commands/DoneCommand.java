package commands;

import tasks.TaskList;
import controlpanel.DukeException;
import controlpanel.Storage;
import controlpanel.Ui;

/**
 * The command aims to mark a specific task as done.
 */
public class DoneCommand extends Command {

    private int serialNo;

    /**
     * The constructor which initialize the done command with a input index.
     * @param index The index number of the task which will be deleted.
     */
    public DoneCommand(int index) {
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
     * This method executes the done command. It marks the task as done according to its index.
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
        tasks.markDoneATask(serialNo - 1);
        storage.writeTheFile(tasks.getCheckList());
        ui.appendToOutput(" Nice! I've marked this task as done:\n");
        ui.appendToOutput("   " + tasks.getTask(serialNo - 1).getStatusIcon());
        ui.appendToOutput(tasks.getTask(serialNo - 1).getDescription() + "\n");
    }
}

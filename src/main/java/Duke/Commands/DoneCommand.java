package Duke.Commands;

import Duke.Constant.Duke_Response;
import Duke.Storage;
import Duke.Task.Task;
import Duke.Task.TaskList;
import Duke.Ui;

public class DoneCommand extends Command{
    private int index;

    /**
     * Creates a DoneCommand with the specified index
     * to be mark as done. Index starts from 1.
     * @param index Index of task to be mark as done.
     */
    public DoneCommand(int index){
        this.index = index;
    }

    /**
     * Marks the task specified by user as done. Sets message of Ui
     * to show if Command is successfully carried out.
     * @param tasks The list of task stored by Duke
     * @param ui The user interface that handles messages
     * @param storage The database to read files and write txt files
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        String message;

        if(index <= tasks.size() && index > 0){
            if(!tasks.isCompletedTask(index)) {
                Task task = tasks.doneTask(index);
                message = new Duke_Response().DONE_FOUND + "      " + task.toString() + "\n";
            } else {
                message = new Duke_Response().DONE_COMPLETED;
            }
        } else {
            message = new Duke_Response().NOT_FOUND;
        }

        ui.setMessage(message);
    }
}

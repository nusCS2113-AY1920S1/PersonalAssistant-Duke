package duke.commands;

import duke.constant.DukeResponse;
import duke.Storage;
import duke.task.Task;
import duke.task.TaskList;
import duke.Ui;

public class DoneCommand extends Command {
    private int index;

    /**
     * Creates a DoneCommand with the specified index
     * to be mark as done. Index starts from 1.
     * @param index Index of task to be mark as done.
     */
    public DoneCommand(int index) {
        this.index = index;
    }

    /**
     * Marks the task specified by user as done. Sets message of Ui
     * to show if command is successfully carried out.
     * @param tasks The list of task stored by duke
     * @param ui The user interface that handles messages
     * @param storage The database to read files and write txt files
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        String message;

        if (index <= tasks.size() && index > 0) {
            if (!tasks.isCompletedTask(index)) {
                Task task = tasks.doneTask(index);
                message = new DukeResponse().DONE_FOUND + task.toString() + "\n";
            } else {
                message = new DukeResponse().DONE_COMPLETED;
            }
        } else {
            message = new DukeResponse().NOT_FOUND;
        }

        ui.setMessage(message);
    }
}

package duke.command;

import duke.commons.DukeException;
import duke.storage.Storage;
import duke.task.Deadline;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;

public class ReminderCommand extends Command {

    public ReminderCommand() {}

    /**
     * Represents a command lists all not done deadlines in TaskList.
     */
    public void execute(TaskList tasks, Storage storage, Ui ui) throws DukeException {
        ui.showMessage("Here are your Deadlines that have not been met:");
        for (Task task : tasks.getTasks()) {
            if(task instanceof Deadline && !task.isDone()) {
                Deadline D = (Deadline) task;
                ui.showMessage(D.toString());
            }
        }
    }

}
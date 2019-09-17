package duke.commands;

import duke.Storage;
import duke.Ui;
import duke.constant.DukeResponse;
import duke.task.Task;
import duke.task.TaskList;

public class RemindersCommand extends Command{

    /**
     * Execute corresponding command and store response to Ui.
     *
     * @param tasks   The list of task stored by duke
     * @param ui      The user interface that handles messages
     * @param storage The database to read files and write txt files
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        String message = "";
        boolean deadlineExists = false;
        int counter = 1;
        for (Task i : tasks) {
            if (i.getSymbol() == "[D]") {
                if (!deadlineExists) {
                    message += new DukeResponse().REMINDERS_FOUND;
                    deadlineExists = true;
                }
                message += counter++ + "." + i.toString();
                }
            }
        if (!deadlineExists) {
            message += new DukeResponse().REMINDERS_NOT_FOUND;
        }
        ui.setMessage(message);
    }
}

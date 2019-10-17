package duke.command;

import duke.exceptions.DukeException;
import duke.storage.FileHandling;
import duke.tasks.Task;
import duke.tasks.TaskList;
import duke.ui.Ui;

import java.util.ArrayList;

public class RemindCommand extends Command {

    @Override
    public void execute(TaskList tasks, Ui ui, FileHandling storage)throws DukeException {
        ArrayList<Task> isDeadline = new ArrayList<Task>();
        for (int i = 0; i < tasks.numTasks(); i++) {
            String tempTask = tasks.getTask(i).toString();
            if (tempTask.contains("[D]")) {
                isDeadline.add(tasks.getTask(i));
            }
        }
        ui.getRemindersList(isDeadline);
    }
}

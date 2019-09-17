package duke.commands;

import duke.exceptions.DukeException;
import duke.storage.Storage;
import duke.tasks.Schedule;
import duke.tasks.Task;
import duke.tasks.TaskList;
import duke.ui.Ui;

import java.util.ArrayList;

public class ConfirmCommand extends Command {
    private Task task;

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage, Schedule schedule) throws DukeException {
        task = schedule.confirm();
        ArrayList<Task> currentTasks = tasks.getTasks();
        schedule.update(task);
        currentTasks.add(task);
        ui.showAdded(task, currentTasks);
        storage.updateFile(currentTasks);
    }
}
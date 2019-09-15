package duke.command;

import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;

import java.util.ArrayList;

public class AddMultipleCommand extends Command {
    protected ArrayList<Task> tasks;

    public AddMultipleCommand(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public void execute(TaskList items, Ui ui) {
        for (Task curTask : tasks) {
            items.add(curTask);
        }
        ui.showAdd(items);
    }

    @Override
    public String executeGui(TaskList items, Ui ui) {
        for (Task curTask : tasks) {
            items.add(curTask);
        }

        String str = ui.showAddGui(items);
        return str;
    }

    @Override
    public void executeStorage(TaskList items, Ui ui, Storage storage) {
    }
}

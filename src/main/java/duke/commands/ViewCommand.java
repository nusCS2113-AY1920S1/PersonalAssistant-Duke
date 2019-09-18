package duke.commands;

import duke.Storage;
import duke.Ui;
import duke.task.TaskList;

public class ViewCommand extends Command {
    private String date;

    public ViewCommand(String date) {
        this.date = date;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.setMessage(tasks.viewSchedule(date));
    }
}

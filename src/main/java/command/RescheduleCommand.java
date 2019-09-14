package command;

import process.DukeException;
import process.Storage;
import process.Ui;
import task.TaskList;

public class RescheduleCommand extends Command{
    private int index;
    private String datetime;
    public RescheduleCommand(int index_, String datetime_) {
        index = index_;
        datetime = datetime_;
    }
    public String execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        if (this.index >= tasks.size()) throw new DukeException("index error");
        tasks.rescheduleTask(index, datetime);
        storage.save(tasks);
        return ui.showTaskRescheduled(tasks.get(index).toString());
    }
}

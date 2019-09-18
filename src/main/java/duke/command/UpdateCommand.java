package duke.command;

import duke.commons.DukeException;
import duke.storage.Storage;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;
import java.util.Date;

public class UpdateCommand extends Command {
    protected int index;
    protected Date start;
    protected Date end;

    public UpdateCommand(int index, Date start) {
        super();
        this.index = index - 1;
        this.start = start;
    }

    public UpdateCommand(int index, Date start, Date end) {
        super();
        this.index = index - 1;
        this.start = start;
        this.end = end;
    }

    @Override
    public void execute(TaskList tasks, Storage storage, Ui ui) throws DukeException {
        if (tasks.get(index) instanceof Deadline) {
            Deadline updated = (Deadline) tasks.get(index);
            updated.setTime(start);
        } else if (tasks.get(index) instanceof Event && this.end != null) {
            Event updated = (Event) tasks.get(index);
            updated.setTime(start, end);
        } else if (tasks.get(index) instanceof Event) {
            Event updated = (Event) tasks.get(index);
            updated.setTime(start);
        }
        storage.serialize(tasks);
        ui.refreshTaskList(tasks, tasks);
    }
}

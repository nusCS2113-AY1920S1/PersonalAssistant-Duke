package command;

import exception.DukeException;
import storage.Storage;
import task.Deadline;
import task.Event;
import task.Task;
import task.TaskList;
import ui.Ui;

import java.io.IOException;
import java.util.Comparator;

public class ViewScheduleCommand extends Command {
    TaskList scheduleList;

    public ViewScheduleCommand() {
        scheduleList = new TaskList();
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException, IOException {
        for (Task i : tasks) {
            if (i.getClass() == Event.class || i.getClass() == Deadline.class) {
                scheduleList.add(i);
            }
        }
        scheduleList.sort(
                Comparator.comparing(Task::getDateTime));
        for (Task i : scheduleList) {
            ui.showString(i.toString());
        }
    }
}

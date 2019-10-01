package command;

import exception.DukeException;
import storage.Storage;
import task.Deadline;
import task.Event;
import task.Task;
import task.TaskList;
import ui.Ui;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class ViewScheduleCommand extends Command {
    private TaskList scheduleList;
    Date dateToView;

    public ViewScheduleCommand(String date) throws DukeException {
        scheduleList = new TaskList();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            dateToView = sdf.parse(date);
        } catch (ParseException e) {
            throw new DukeException("Please enter date time format correctly: dd/mm/yyyy");
        }
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

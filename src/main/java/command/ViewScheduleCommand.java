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
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

public class ViewScheduleCommand extends Command {
    private TaskList scheduleList;
    Date dateToView;
    Date dateAfter;

    /**
     * View the schedule on a certain date.
     * @param splitStr tokenized user input
     * @throws DukeException if input format is incorrect
     */
    public ViewScheduleCommand(String[] splitStr) throws DukeException {
        if (splitStr.length == 1) {
            throw new DukeException("☹ OOPS!!! Please add the date which you want to view!");
        }
        scheduleList = new TaskList();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            dateToView = sdf.parse(splitStr[1]);
        } catch (ParseException e) {
            throw new DukeException("Please enter date time format correctly: dd/mm/yyyy");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(dateToView);
        c.add(Calendar.DATE, 1);
        dateAfter = c.getTime();
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException, IOException {
        for (Task i : tasks) {
            if ((i.getClass() == Event.class || i.getClass() == Deadline.class)
                && i.getDateTime().getTime() - dateToView.getTime() >= 0
                && dateAfter.getTime() - i.getDateTime().getTime() >= 0) {
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

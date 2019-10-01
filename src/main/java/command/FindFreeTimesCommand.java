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

public class FindFreeTimesCommand extends Command {
    private int num;
    private TaskList scheduleList;

    /**
     * Command to find the next free n number of hours in the schedule.
     * @param splitStr from user
     */
    public FindFreeTimesCommand(String[] splitStr) throws DukeException {
        if (splitStr.length == 1) {
            throw new DukeException("☹ OOPS!!! Please input the number of hours of free time you need");
        }
        num = Integer.parseInt(splitStr[1]);
        scheduleList = new TaskList();
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException, IOException {
        for (Task i : tasks) {
            if (i.getClass() == Event.class) {
                scheduleList.add(i);
            }
        }
        scheduleList.sort(
                Comparator.comparing(Task::getDateTime));
        if (num < 1) {
            throw new DukeException("☹ OOPS!!! Please enter a number > 0");
        }
    }


}

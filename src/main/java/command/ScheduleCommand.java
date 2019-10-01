package command;

import exception.DukeException;
import parser.CommandParams;
import parser.TimeParser;
import storage.Storage;
import task.Deadline;
import task.Event;
import task.Task;
import task.TaskList;
import ui.Ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScheduleCommand extends Command {


    /**
     * Constructs a {@code Command} object with commandType.
     */
    public ScheduleCommand() {
        super(null, null, null, null);
    }

    /**
     * Iterates through current task list and finds tasks that are on the day.
     * @param tasks   The taskList of Duke.
     * @param ui      The ui of Duke.
     * @param storage The storage of Duke.
     */
    public void execute(CommandParams commandParams, TaskList tasks, Ui ui, Storage storage) throws DukeException {
        if (commandParams.getMainParam() == null) {
            throw new DukeException("☹ OOPS!!! I don't which day's schedule to display.");
        }
        String dateString = commandParams.getMainParam();
        Date dayStart = TimeParser.parse(dateString + " 0000");
        Date dayEnd = TimeParser.parse(dateString + " 2359");
        List<Task> taskList = tasks.getTasks();
        List<Task> matchedList = new ArrayList<>();
        for (Task task : taskList) {
            if (task instanceof Event) {
                if (((Event) task).getStart().after(dayStart) && ((Event) task).getStart().before(dayEnd)) {
                    matchedList.add(task);
                } else if (((Event) task).getEnd().after(dayStart) && ((Event) task).getEnd().before(dayEnd)) {
                    matchedList.add(task);
                }
            } else if (task instanceof Deadline) {
                if (((Deadline) task).getBy().after(dayStart) && ((Deadline) task).getBy().before(dayEnd)) {
                    matchedList.add(task);
                }
            }
        }
        if (matchedList.size() == 0) {
            ui.println("No results found.");
            return;
        }
        ui.println("Here is your schedule for" + dateString);
        int count = 1;
        for (Task task : matchedList) {
            ui.println(count + "." + task.toString());
            count++;
        }
    }
}

package command;

import exception.DukeException;
import storage.Storage;
import task.Deadline;
import task.Event;
import task.TaskList;
import ui.Ui;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;


public class SnoozeCommand extends Command {
    private int num;
    private int hours;
    private String[] split;

    /**
     * Postpone task.
     * @param splitStr tokenized user input
     * @throws DukeException if input is incorrect format
     */
    public SnoozeCommand(String input, String[] splitStr) throws DukeException {
        if (splitStr.length == 1) {
            throw new DukeException("☹ OOPS!!! Please add the index of the task you want to snooze");
        }
        String temp = input.substring(7);
        if (!temp.contains(" /by ")) {
            throw new DukeException("☹ OOPS!!! Please add the time you want to postpone the task to!");
        }
        this.split = temp.split(" /by ");
        try {
            this.num = Integer.parseInt(split[0]) - 1;
            this.hours = Integer.parseInt(split[1]);
        } catch (NumberFormatException e) {
            throw new DukeException("☹ OOPS!!! Please input an integer for the task index!");
        }
    }

    /**
     * Run the command.
     * @param tasks task list
     * @param ui user interface
     * @param storage handles read write of text file
      * @throws IOException for IO exceptions
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException, IOException {
        if (this.num < 0 || this.num >= tasks.size()) {
            throw new DukeException("☹ OOPS!!! That task is not in your list");
        }
        if (tasks.get(num) instanceof Deadline) {
            Date newDate = tasks.get(num).getDateTime();
            newDate = addHoursToDate(newDate, hours);
            ((Deadline) tasks.get(num)).setDateTime(newDate);
        } else if (tasks.get(num) instanceof Event) {
            Date newStartDate = ((Event) tasks.get(num)).getDateTimeStart();
            Date newEndDate = ((Event) tasks.get(num)).getDateTimeEnd();
            newStartDate = addHoursToDate(newStartDate, hours);
            newEndDate = addHoursToDate(newEndDate, hours);
            ((Event) tasks.get(num)).setDateTimeStart(newStartDate);
            ((Event) tasks.get(num)).setDateTimeEnd(newEndDate);
        } else {
            throw new DukeException("☹ OOPS!!! This task cannot be postponed!");
        }
        storage.saveToFile(tasks);
        ui.showString("Got it. I have postponed this task by " + hours + " hours.");
        ui.showString(tasks.get(num).toString());
    }

    /**
     * This method adds n hours to a date.
     * @param date current date
     * @param hours number of hours to be added
     * @return new date after addition
     */
    public Date addHoursToDate(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }
}
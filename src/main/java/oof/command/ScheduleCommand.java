package oof.command;

import oof.Oof;
import oof.Storage;
import oof.TaskList;
import oof.Ui;
import oof.exception.OofException;
import oof.task.Deadline;
import oof.task.Event;
import oof.task.Todo;

public class ScheduleCommand extends Command {
    private String date;

    /**
     * Constructor for ScheduleCommand.
     * @param date      String containing date.
     */
    public ScheduleCommand(String date) {
        super();
        this.date = date;
    }

    /**
     * Checks if input date and date of Task are equal.
     *
     * @param input date from user input.
     * @param date  date from existing Task.
     * @return true if they are equal, false otherwise.
     */
    private boolean compareDate(String input, String date) {
        return input.equals(date);
    }

    /**
     * Checks TaskList for Tasks associated to indicated date.
     *
     * @param arr ArrayList of Task objects.
     * @return ArrayList of Task objects associated to given date.
     */
    private TaskList scheduleByDate(TaskList arr) {
        TaskList scheduledTasks = new TaskList();
        for (int i = 0; i < arr.getSize(); i++) {
            if (arr.getTask(i) instanceof Todo) {
                Todo t = (Todo) arr.getTask(i);
                String date = t.getOn().substring(0, 10);
                if (compareDate(this.date, date)) {
                    scheduledTasks.addTask(t);
                }
            } else if (arr.getTask(i) instanceof Deadline) {
                Deadline d = (Deadline) arr.getTask(i);
                String date = d.getBy().substring(0, 10);
                if (compareDate(this.date, date)) {
                    scheduledTasks.addTask(d);
                }
            } else if (arr.getTask(i) instanceof Event) {
                Event e = (Event) arr.getTask(i);
                String date = e.getStartTiming().substring(0, 10);
                if (compareDate(this.date, date)) {
                    scheduledTasks.addTask(e);
                }
            }
        }
        return scheduledTasks;
    }

    /**
     * Executes Schedule Command.
     *
     * @param arr     ArrayList of Task objects.
     * @param ui      Instance of Ui that is responsible for visual feedback.
     * @param storage Instance of Storage that enables the reading and writing of Task
     *                objects to hard disk.
     * @throws OofException Catches invalid commands given by user and dates with no scheduled tasks.
     */
    public void execute(TaskList arr, Ui ui, Storage storage) throws OofException {
        if (this.date.isEmpty()) {
            throw new OofException("OOPS! Please enter a date!");
        }
        TaskList scheduledTasks = scheduleByDate(arr);
        if (scheduledTasks.getSize() == 0) {
            throw new OofException("There are no Tasks scheduled on " + this.date + ".");
        }
        ui.printTasksByDate(scheduledTasks, this.date);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
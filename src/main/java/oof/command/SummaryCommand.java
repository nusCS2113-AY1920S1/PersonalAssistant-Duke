package oof.command;

import oof.Storage;
import oof.TaskList;
import oof.Ui;
import oof.exception.OofException;
import oof.task.Deadline;
import oof.task.Event;
import oof.task.Task;
import oof.task.Todo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class SummaryCommand extends Command {

    private static final int EMPTY = 0;
    private TaskList summary = new TaskList();

    /**
     * Constructor for SummaryCommand.
     */
    public SummaryCommand() {
        super();
    }

    /**
     * Get a summary of tomorrow's tasks.
     * @param input  LocalDateTime of a day after today.
     * @param arr       TaskList of all tasks
     * @return          a TaskList of tomorrow's tasks.
     */
    private TaskList getSummary(String input, TaskList arr) {
        for (int i = 0; i < arr.getSize(); i++) {
            Task t = arr.getTask(i);
            if (isValid(t)) {
                String date = getDate(t);
                if (input.equals(date)) {
                    summary.addTask(t);
                }
            }
        }
        return summary;
    }

    /**
     * Check Task type.
     * @param task      Task object.
     * @return          boolean if Task object is of a valid Task type or not.
     */
    private boolean isValid(Task task) {
        return task instanceof Todo || task instanceof Deadline || task instanceof Event;
    }

    /**
     * Get Date from Task object.
     * @param task      Task object.
     * @return          String containing date from Task object.
     */
    private String getDate(Task task) {
        if (task instanceof Todo) {
            return ((Todo) task).getOn().substring(0, 10);
        } else if (task instanceof Deadline) {
            return ((Deadline) task).getBy().substring(0, 10);
        } else if (task instanceof Event) {
            return ((Event) task).getStartTiming().substring(0, 10);
        }
        return null;
    }

    /**
     * Get the date of tomorrow in format DD-MM-YYYY.
     * @return date     String containing formatted date of tomorrow.
     */
    private String getTomorrowDate() {
        LocalDateTime ldt = LocalDateTime.now().plusDays(1);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
        return format.format(ldt);
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws OofException {
        String tomorrow = getTomorrowDate();
        TaskList summary = getSummary(tomorrow, tasks);
        if (summary.getSize() == EMPTY) {
            throw new OofException("There are no Tasks scheduled on " + tomorrow + ".");
        }
        ui.printTasksByDate(summary, tomorrow);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}

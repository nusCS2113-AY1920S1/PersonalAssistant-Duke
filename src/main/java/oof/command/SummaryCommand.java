package oof.command;

import oof.Storage;
import oof.TaskList;
import oof.Ui;
import oof.exception.OofException;
import oof.task.Deadline;
import oof.task.Event;
import oof.task.Todo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class SummaryCommand extends Command {
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
            if (arr.getTask(i) instanceof Todo) {
                Todo t = (Todo) arr.getTask(i);
                String date = t.getOn().substring(0, 10);
                if (input.equals(date)) {
                    summary.addTask(t);
                }
            } else if (arr.getTask(i) instanceof Deadline) {
                Deadline d = (Deadline) arr.getTask(i);
                String date = d.getBy().substring(0, 10);
                if (input.equals(date)) {
                    summary.addTask(d);
                }
            } else if (arr.getTask(i) instanceof Event) {
                Event e = (Event) arr.getTask(i);
                String date = e.getStartTiming().substring(0, 10);
                if (input.equals(date)) {
                    summary.addTask(e);
                }
            }
        }
        return summary;
    }

    /**
     * Get the date of tomorrow in format DD-MM-YYYY.
     * @return date     String containing formatted date of tomorrow
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
        if (summary.getSize() == 0) {
            throw new OofException("There are no Tasks scheduled on " + tomorrow + ".");
        }
        ui.printTasksByDate(summary, tomorrow);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}

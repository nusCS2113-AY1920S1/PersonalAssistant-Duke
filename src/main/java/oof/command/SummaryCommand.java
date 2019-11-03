package oof.command;

import oof.Storage;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;
import oof.Ui;
import oof.exception.OofException;
import oof.model.task.Deadline;
import oof.model.task.Event;
import oof.model.task.Task;
import oof.model.task.Todo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class SummaryCommand extends Command {

    private static final int EMPTY = 0;
    private static final int ADD_A_DAY = 1;
    private TaskList summary = new TaskList();

    /**
     * Constructor for SummaryCommand.
     */
    public SummaryCommand() {
        super();
    }

    /**
     * Get a summary of tomorrow's tasks.
     *
     * @param input LocalDateTime of a day after today.
     * @param arr   TaskList of all tasks
     * @return a TaskList of tomorrow's tasks.
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
     *
     * @param task Task object.
     * @return boolean if Task object is of a valid Task type or not.
     */
    private boolean isValid(Task task) {
        return task instanceof Todo || task instanceof Deadline || task instanceof Event;
    }

    /**
     * Get the date of tomorrow in format DD-MM-YYYY.
     *
     * @return date     String containing formatted date of tomorrow.
     */
    private String getTomorrowDate() {
        LocalDateTime ldt = LocalDateTime.now().plusDays(ADD_A_DAY);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
        return format.format(ldt);
    }

    /**
     * Gets a summary of tomorrow's Tasks.
     *
     * @param semesterList Instance of SemesterList that stores Semester objects.
     * @param taskList    Instance of TaskList that stores Task objects.
     * @param ui           Instance of Ui that is responsible for visual feedback.
     * @param storage      Instance of Storage that enables the reading and writing of Task
     *                     objects to hard disk.
     * @throws OofException if there are no tasks scheduled for tomorrow.
     */
    @Override
    public void execute(SemesterList semesterList, TaskList taskList, Ui ui, Storage storage) throws OofException {
        String tomorrow = getTomorrowDate();
        TaskList summary = getSummary(tomorrow, taskList);
        if (summary.getSize() == EMPTY) {
            throw new OofException("There are no Tasks scheduled on " + tomorrow + ".");
        }
        ui.printTasksByDate(summary, tomorrow);
    }
}

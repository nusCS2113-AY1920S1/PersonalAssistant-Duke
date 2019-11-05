package oof.command;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import oof.Ui;
import oof.model.module.SemesterList;
import oof.model.task.Task;
import oof.model.task.TaskList;
import oof.storage.StorageManager;

/**
 * Shows a summary of tomorrow's tasks.
 */
public class SummaryCommand extends Command {

    public static final String COMMAND_WORD = "summary";
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
            String date = getDate(t);
            if (input.equals(date)) {
                summary.addTask(t);
            }
        }
        return summary;
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
     * @param semesterList   Instance of SemesterList that stores Semester objects.
     * @param taskList       Instance of TaskList that stores Task objects.
     * @param ui             Instance of Ui that is responsible for visual feedback.
     * @param storageManager Instance of Storage that enables the reading and writing of Task
     *                       objects to hard disk.
     */
    @Override
    public void execute(SemesterList semesterList, TaskList taskList, Ui ui, StorageManager storageManager) {
        String tomorrow = getTomorrowDate();
        TaskList summary = getSummary(tomorrow, taskList);
        if (summary.isEmpty()) {
            ui.printNoTaskScheduled(tomorrow);
        }
        ui.printTasksByDate(summary, tomorrow);
    }
}

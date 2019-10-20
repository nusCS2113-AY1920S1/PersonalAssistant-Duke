package oof.command;

import oof.Storage;
import oof.TaskList;
import oof.Ui;
import oof.exception.OofException;
import oof.task.Task;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StartTrackerCommand extends Command {
    private String description;

    /**
     * Constructor for StartTrackerCommand.
     * @param description       of Task to start tracking.
     */
    public StartTrackerCommand(String description) {
        super();
        this.description = description;
    }

    /**
     * Check if Task has been completed.
     * @param task      Task object.
     * @return          Status of task Task object.
     */
    boolean isDone(Task task) {
        return task.getStatus();
    }

    /**
     * Parse String to get Task Description.
     * @param task      Task object.
     * @return          Description of task Task object.
     */
    private String getTaskDescription(Task task) {
        String[] byDate = task.toString().split("/");
        String[] byDesc = byDate[0].split(" ");
        return byDesc[1];
    }

    /**
     * Find Task object in TaskList where descriptions match.
     * @param list              TaskList object.
     * @return                  Task object that matches user given description.
     * @throws OofException     if no matches are found.
     */
    Task findTask(TaskList list, String desc) throws OofException {
        Task task = null;
        for (int i = 0; i < list.getSize(); i++) {
            String currentDesc = getTaskDescription(list.getTask(i));
            if (desc.equals(currentDesc)) {
                task = list.getTask(i);
                break;
            }
        }
        if (task == null) {
            throw new OofException("Invalid Task Description!");
        }
        return task;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws OofException {
        if (description.isEmpty()) {
            throw new OofException("Please enter a Task!");
        }
        Task task = findTask(tasks, description);
        if (isDone(task)) {
            throw new OofException("Task has already been completed.");
        } else {
            Date now = new Date();
            String date = convertDateToString(now);
            task.setStartDate(date);
            ui.printStartAtCurrent(task, date);
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}

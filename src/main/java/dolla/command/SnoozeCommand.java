package dolla.command;

import dolla.Time;
import dolla.Ui;
import dolla.task.Deadline;
import dolla.task.Event;
import dolla.task.Task;
import dolla.task.TaskList;

import java.time.LocalDateTime;

/**
 * A command to snooze tasks.
 */
public abstract class SnoozeCommand extends Command {
    protected String taskNumStr;
    protected String newDateStr;

    /**
     * Instantiates a new Snooze command.
     *
     * @param taskNumStr the task num str
     * @param newDateStr the new date str
     */
    public SnoozeCommand(String taskNumStr, String newDateStr) {
        this.taskNumStr = taskNumStr;
        this.newDateStr = newDateStr;
    }

    //@Override

    /**
     * .
     * @param tasks TODO: [placeholder]
     * @throws Exception TODO: [placeholder]
     */
    public void execute(TaskList tasks) throws Exception {
        int taskNumInt = stringToInt(taskNumStr);
        LocalDateTime date = Time.readDateTime(newDateStr); // Default date
        try {
            Task taskToSnooze = tasks.getFromList(taskNumInt - 1);
            char type = taskToSnooze.getTaskType();

            if (type == 'T') {
                Ui.printNoDateToSnoozeError(taskToSnooze);
            } else if (type == 'D') {
                Task newTask = new Deadline(taskToSnooze.getTaskDescription(), date);
                if (newDateIsAfter(taskToSnooze, newTask)) {
                    tasks.replaceTask(taskNumInt - 1, newTask);
                    Ui.printSnoozedTask(newTask);
                } else {
                    Ui.printOldDateIsAfterError();
                }
            } else if (type == 'E') {
                Task newTask = new Event(taskToSnooze.getTaskDescription(), date);
                if (newDateIsAfter(taskToSnooze, newTask)) {
                    tasks.replaceTask(taskNumInt - 1, newTask);
                    Ui.printSnoozedTask(newTask);
                } else {
                    Ui.printOldDateIsAfterError();
                }
            } else {
                Ui.printErrorMsg();
            }
        } catch (IndexOutOfBoundsException e) {
            //Ui.printNoTaskAssocError(taskNumInt);
            return;
        }
    }

    /**
     * Checks if the new date given is after the old date.
     * @param oldTask task with the old date
     * @param newTask task with the new date
     * @return true if the new date is after the old date, else false
     */
    public boolean newDateIsAfter(Task oldTask, Task newTask) {
        LocalDateTime oldDate = oldTask.getDate();
        LocalDateTime newDate = newTask.getDate();
        if (oldDate.isBefore(newDate)) {
            return true;
        }
        return false;
    }
}

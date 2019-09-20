package duke.command;

import duke.Time;
import duke.Ui;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.TaskList;

import java.time.LocalDateTime;

public class SnoozeCommand extends Command {
    protected String taskNumStr;
    protected String newDateStr;

    public SnoozeCommand(String taskNumStr, String newDateStr) {
        this.taskNumStr = taskNumStr;
        this.newDateStr = newDateStr;
    }

    @Override
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
            Ui.printNoTaskAssocError(taskNumInt);
            return;
        }
    }

    public boolean newDateIsAfter(Task oldTask, Task newTask) {
        LocalDateTime oldDate = oldTask.getDate();
        LocalDateTime newDate = newTask.getDate();
        if (oldDate.isBefore(newDate)) {
            return true;
        }
        return false;
    }
}

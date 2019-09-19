package duke.command;

import duke.Time;
import duke.Ui;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.TaskList;

import java.time.LocalDateTime;
import java.util.ArrayList;

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
        ArrayList<String> msg = new ArrayList<>();

        try {
            Task taskToSnooze = tasks.getFromList(taskNumInt - 1);
            char type = taskToSnooze.getTaskType();

            if (type == 'T') {
                msg.add("OOPS! " + taskToSnooze + " do not have a date to snooze!");
            } else if (type == 'D') {
                Task newTask = new Deadline(taskToSnooze.getTaskDescription(), date);
                tasks.replaceTask(taskNumInt - 1, newTask);
                msg.add("Noted. I've snoozed this task:" );
                msg.add(taskToSnooze.getTaskDescription() + "until " + newDateStr);
            } else if (type == 'E') {
                Task newTask = new Event(taskToSnooze.getTaskDescription(), date);
                tasks.replaceTask(taskNumInt - 1, newTask);
                msg.add("Noted. I've snoozed this task:" );
                msg.add(taskToSnooze.getTaskDescription() + "until " + newDateStr);
            } else {
                msg.add("OOPS! An error has occurred.");
            }
        } catch (IndexOutOfBoundsException e) {
            msg.add(taskNumInt + " is not associated to any task number.");
            msg.add("Use 'list' to check the tasks that are here first!");
            Ui.printMsg(msg);
            return;
        }
        Ui.printMsg(msg);
    }
}

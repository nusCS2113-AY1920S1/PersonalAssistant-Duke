package duke.command;

import duke.Ui;
import duke.task.RecurringTask;
import duke.task.Task;
import duke.task.TaskList;

public class AddRecurringTasksCommand extends AddCommand{
    public AddRecurringTasksCommand (String taskDescription) {
        super(taskDescription);
        dateTrigger = "/every";
    }

    public void execute(TaskList tasks) {
        if (!splitDescTime()) return; // If error occurs, stop the method!
        Task newTask = new RecurringTask(taskDescription, time);
        tasks.add(newTask);
        Ui.echoAdd(newTask, tasks.size());
    }


}

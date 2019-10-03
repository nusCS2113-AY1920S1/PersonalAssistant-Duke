package dolla.command;

import dolla.Ui;
import dolla.task.FixDuration;
import dolla.task.Task;
import dolla.task.TaskList;

public abstract class AddFixDurationCommand extends AddCommand {

    private String duration;
    private int position;

    public AddFixDurationCommand(String taskDescription) {
        super(taskDescription);
    }

    /**
     * This method will split the string into the task description and the duration of the task.
     * @param tasks duke.task.TaskList containing all the tasks stored.
     * @throws Exception e when user input is invalid
     */
    public void execute(TaskList tasks) {
        try {
            position = taskDescription.indexOf("/");
            duration = taskDescription.substring(position + 8);
            Task newTask = new FixDuration(taskDescription.substring(0, position - 1), duration);
            tasks.add(newTask);
            Ui.echoAdd(newTask, tasks.size());
        } catch (Exception e) {
            Ui.printFixDurationTaskError();
        }
    }
}

package dolla.command;

import dolla.Ui;
import dolla.task.Event;
import dolla.task.Task;
import dolla.task.TaskList;

/**
 * duke.command.AddEventCommand is a command used to add an event task into the duke.task.TaskList.
 */
public abstract class AddEventCommand extends AddCommand {

    /**
     * Instantiates a new Add event command.
     *
     * @param taskDescription the task description
     */
    public AddEventCommand(String taskDescription) {
        super(taskDescription); // taskDescription includes the task info and date
        time = null;
        dateTrigger = "/at";
    }

    /**
     * Adds an event task into the specified duke.task.TaskList.
     * <p>
     *     The method first splits the input data into the task description and time.
     *     Using the newly acquired data, a new duke.task.Event duke.task.Task is created and then stored into
     *     the specified duke.task.TaskList.
     * </p>
     * <p>
     *     If the method encounters an error when trying to split the input data, the method
     *     returns without doing anything.
     * </p>
     * @param tasks The duke.task.TaskList where the event task is to be added.
     */
    public void execute(TaskList tasks) {
        if (!splitDescTime()) {
            return; // If error occurs, stop the method!
        }
        if (!detectAnomalies(tasks, time)) {
            return; // If error occurs, stop the method!
        }
        Task newTask = new Event(taskDescription, time);
        tasks.add(newTask);
        Ui.echoAdd(newTask, tasks.size());
    }
}

package Commands;
import Tasks.*;
import Interface.*;

import java.util.ArrayList;

/**
 * Represents the command to snooze a Task object to a TaskList object.
 */
public class SnoozeCommand extends Command{
    private int index;
    private String dateString;

    /**
     * Creates an SnoozeCommand object.
     * @param index The index representing the task number in the TaskList object
     */
    public SnoozeCommand(int index, String dateString){
        this.index = index;
        this.dateString = dateString;
    }

    /**
     * Executes the snoozing a task inside the TaskList object with the given number.
     * @param ui The Ui object to display the find message
     * @param storage The Storage object to access file to load or save the tasks
     * @return This returns the method in the Ui object which returns the string to display snooze message
     */
    @Override
    public String execute(TaskList todos, TaskList events, TaskList deadlines, Ui ui, Storage storage) throws DukeException {
        TaskList list = new TaskList();
        ArrayList<Task> todosList = todos.getList();
        ArrayList<Task> eventsList = events.getList();
        ArrayList<Task> deadlinesList = deadlines.getList();
        for (Task task : todosList) {
                list.addTask(task);
        }
        for (Task task : eventsList) {
                list.addTask(task);
        }
        for (Task task : deadlinesList) {
                list.addTask(task);
        }
        list.snoozeTask(index, dateString);
        return ui.showSnooze(index, list.taskListSize(), list);
    }
}

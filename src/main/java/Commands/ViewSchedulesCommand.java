package Commands;

import Interface.Storage;
import Interface.Ui;
import Tasks.Task;
import Tasks.TaskList;

import java.util.ArrayList;

/**
 * A method which allows the user to view tasks in form of schedule.
 */
public class ViewSchedulesCommand extends Command{

    /**
     * Executes the display of all the task as a schedule,
     * differentiated by type.
     * @param ui The Ui object to display the user schedule
     * @param storage The Storage object to access file to load or save the tasks
     * @return This returns the method in the Ui object which returns the string to display user's schedule
     */
    @Override
    public String execute(TaskList todos, TaskList events, TaskList deadlines, Ui ui, Storage storage){
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
        return ui.showUserSchedule(list.schedule());
    }
}

package Commands;
import Tasks.*;
import Interface.*;

import java.util.ArrayList;

/**
 * Represent the command to find a task in the TaskList object
 */
public class FindCommand extends Command {

    private String key;

    /**
     * Creates a FindCommand object.
     * @param key The keyword that the user wants to use to find in teh TaskList object
     */
    public FindCommand(String key){
        this.key = key;
    }

    /**
     * Executes the finding a task inside the TaskList object with the given keyword
     * @param storage The Storage object to access file to load or save the tasks
     * @return This returns the method in the Ui object which returns the string to display find message
     */
    @Override
    public String execute(TaskList todos, TaskList events, TaskList deadlines, Ui ui, Storage storage) {
        TaskList list = new TaskList();
        ArrayList<Task> todosList = todos.getList();
        ArrayList<Task> eventsList = events.getList();
        ArrayList<Task> deadlinesList = deadlines.getList();
        for (Task task : todosList) {
            if (task.getDescription().contains(key)) {
                list.addTask(task);
            }
        }
        for (Task task : eventsList) {
            if (task.getDescription().contains(key)) {
                list.addTask(task);
            }
        }
        for (Task task : deadlinesList) {
            if (task.getDescription().contains(key)) {
                list.addTask(task);
            }
        }
        return ui.showFind(list);
    }
}

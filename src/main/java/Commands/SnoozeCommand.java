package Commands;
import Tasks.*;
import Interface.*;
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
     * @param list The TaskList object used to find a task with the given keyword
     * @param ui The Ui object to display the find message
     * @param storage The Storage object to access file to load or save the tasks
     * @return This returns the method in the Ui object which returns the string to display snooze message
     */
    @Override
    public String execute(TaskList list, Ui ui, Storage storage) throws Exception {
        list.snoozeTask(index, dateString);
        return ui.showSnooze(index, list.taskListSize(), list);
    }
}

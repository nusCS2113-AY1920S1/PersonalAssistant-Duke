/**
 * Represents the command to add a Task object to a TaskList object.
 */
public class AddCommand extends Command {

    protected Task task;

    /**
     * Creates an AddCommand object.
     * @param task The Task object to be added
     */
    public AddCommand(Task task){
        this.task = task;
    }

    /**
     * Executes the adding of a Task object to a TaskList object
     * and displaying the add task response.
     * @param list The TaskList object to add the task to
     * @param ui The Ui object to display the add task message
     * @param storage The Storage object to access file to load or save the tasks
     * @return This returns the method in the Ui object which returns the string to display add task message
     */
    @Override
    public String execute(TaskList list, Ui ui, Storage storage) {
        list.addTask(this.task);
        return ui.showAdd(this.task, list.taskListSize());
    }
}

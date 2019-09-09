/**
 * Represents the command to delete a Task object from a TaskList object.
 */
public class DeleteCommand extends Command {

    protected int index;

    /**
     * Creates a DeleteCommand object.
     * @param index The index representing the task number in the TaskList object
     */
    public DeleteCommand(int index){
        this.index = index;
    }

    /**
     * Executes the deletion of a task inside the TaskList object with the given index.
     * @param list The TaskList object to delete the task from
     * @param ui The Ui object to display the delete task message
     * @param storage The Storage object to access file to load or save the tasks
     * @return This returns the method in the Ui object which returns the string to display delete task message
     * @throws DukeException On ArrayList out of bound error
     */
    @Override
    public String execute(TaskList list, Ui ui, Storage storage) throws DukeException {
        if(index >= 0 && index < list.taskListSize()) {
            Task task = list.getTask(index);
            list.removeTask(this.index);
            return ui.showDelete(task, list.taskListSize());
        } else throw new DukeException("\u2639" + " OOPS!!! I'm sorry, but we cannot find the input task number :-(\n");
    }
}

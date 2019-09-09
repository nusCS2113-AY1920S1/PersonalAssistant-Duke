/**
 * Represent the command to mark a check on a task
 */
public class DoneCommand extends Command {

    protected int index;
    protected Task task;

    /**
     * Creates a DoneCommand object.
     * @param index The index representing the task number in the TaskList object
     */
    public DoneCommand(int index){
        this.index = index;
    }

    /**
     * Executes the marking a check on a task inside the TaskList object with the given index.
     * @param list The TaskList object used to mark a check on the task
     * @param ui The Ui object to display the done task message
     * @param storage The Storage object to access file to load or save the tasks
     * @return This returns the method in the Ui object which returns the string to display done task message
     * @throws DukeException On ArrayList out of bound error
     */
    @Override
    public String execute(TaskList list, Ui ui, Storage storage) throws DukeException {
        if(index >= 0 && index < list.taskListSize()) {
            list.markAsDone(index);
            return ui.showDone(list.getTask(index));
        } else throw new DukeException("     \u2639" + " OOPS!!! I'm sorry, but we cannot find the input task number :-(");
    }
}

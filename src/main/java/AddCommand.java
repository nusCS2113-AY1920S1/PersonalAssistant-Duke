/**
 * Represents a command from user to add a task.
 * Inherits from Command class.
 * @author Zhang Yue Han
 */
public class AddCommand extends Command {

    /**
     * Assigns the task object to take on the values in input from user.
     * @param t task object to be processed
     */
    AddCommand(Task t) {
        task = t;
    }

    /**
     * Adds task item to the task list, writes the new task to hard disk
     * and prints feedback to the user.
     * @param ui Ui object used to display information to the user
     * @param tasks TaskList object which contains the task array list holding the task info
     * @param storage Storage object which is used to write new task to file
     */
    @Override
    public void execute(Ui ui, TaskList tasks, Storage storage) {
        //ask ui to print something
        //add task to tasks
        //ask storage to write to file
        tasks.addItem(task);
        storage.writeFile(task.toData(), true);
        ui.showAdded(task, tasks);

    }



}

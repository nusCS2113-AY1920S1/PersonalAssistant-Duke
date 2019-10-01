package Commands;
import Tasks.*;
import Interface.*;
/**
 * Represents the command to end the program.
 */
public class ByeCommand extends Command {

    /**
     * Executes the updating of the file with current list of tasks
     * in the TaskList object and displays the goodbye message
     * of the program.
     * @param todos The TaskList object for todos
     * @param events The TaskList object for events
     * @param deadlines The TaskList object for deadlines
     * @param ui The Ui object to display the goodbye message
     * @param storage The Storage object to access file to load or save the tasks
     * @return This returns the method in the Ui object which returns the string to display goodbye message
     * @throws Exception On file not found error
     */
    @Override
    public String execute(TaskList todos, TaskList events, TaskList deadlines, Ui ui, Storage storage) throws Exception {
        storage.updateTodoList(todos);
        storage.updateEventList(events);
        storage.updateDeadlineList(deadlines);
        return ui.showBye();
    }
}

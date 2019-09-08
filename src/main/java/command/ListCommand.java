package command;
import process.*;
import task.TaskList;

/**
 * Represents a command that list items from tasks
 */
public class ListCommand extends Command{
    /**
     * Executes the ListCommand
     */
    public String execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        return ui.print_this(tasks.print_list());
    }
}

package command;
import process.*;
import process.DukeException;
import task.TaskList;

/**
 * Represents a command that finds items from tasks
 */
public class FindCommand extends Command {
    private String keyword;
    /**
     * Creates a new FindCommand object with the given keyword
     * @param keyword used to find task
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }
    /**
     * Executes the FindCommand
     */
    public String execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        return  ui.print_this(tasks.find(keyword));
    }
}

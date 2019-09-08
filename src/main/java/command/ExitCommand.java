package command;
import process.*;
import task.TaskList;

/**
 * Represents a command that exits from the programme
 */
public class ExitCommand extends Command {
    /**
     * Executes the ExitCommand
     */
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        super.is_exit = true;
        ui.close();
        return "";
    }
}

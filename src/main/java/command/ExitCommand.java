package command;

import exception.DukeException;
import storage.Storage;
import task.TaskList;
import ui.Ui;

/**
 * The ExitCommand class is used when the user inputs bye and intends to
 * terminate the program.
 *
 * @author Sai Ganesh Suresh
 * @version v1.0
 */
public class ExitCommand extends Command {

    public ExitCommand() {
    }

    /**
     * This execute function calls the super.CommandOut function which is used to
     * terminate the program by updating the boolean flag exit to true.
     */
    public void execute(TaskList tasks, Storage storage) throws DukeException {
        super.commandOut();
        Ui.printGoodbye();
        Ui.printOutput("goodbye");
    }

}

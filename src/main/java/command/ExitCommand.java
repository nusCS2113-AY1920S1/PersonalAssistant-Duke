package command;

import exception.DukeException;
import storage.Storage;
import task.TaskList;
import ui.Ui;

/**
 * Terminates the program.
 *
 * @author Sai Ganesh Suresh
 * @version v1.3
 */
public class ExitCommand extends Command {

    private static final String GOODBYE = "Goodbye";

    public ExitCommand() {
    }

    /**
     * Terminates the program by updating isExit to true.
     */
    public void execute(TaskList tasks, Storage storage) throws DukeException {
        super.commandOut();
        Ui.printGoodbye();
        Ui.printOutput(GOODBYE);
    }

}

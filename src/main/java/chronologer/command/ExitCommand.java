package chronologer.command;

import chronologer.exception.ChronologerException;
import chronologer.storage.Storage;
import chronologer.task.TaskList;
import chronologer.ui.UiTemporary;

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
    public void execute(TaskList tasks, Storage storage) throws ChronologerException {
        super.commandOut();
        UiTemporary.printGoodbye();
        UiTemporary.printOutput(GOODBYE);
    }

}

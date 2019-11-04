package chronologer.command;

import chronologer.storage.ChronologerStateList;
import chronologer.storage.Storage;
import chronologer.task.TaskList;

/**
 * Terminates the program.
 *
 * @author Sai Ganesh Suresh
 * @version v1.4
 */
public class ExitCommand extends Command {

    public ExitCommand() {
    }

    /**
     * Terminates the program by updating isExit to true.
     */
    public void execute(TaskList tasks, Storage storage)  {
        ChronologerStateList.storeUndoRedo();
        System.exit(0);
    }

}

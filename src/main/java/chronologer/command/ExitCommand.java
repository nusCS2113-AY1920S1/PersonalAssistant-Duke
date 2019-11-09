package chronologer.command;

import chronologer.exception.ChronologerException;
import chronologer.storage.ChronologerStateList;
import chronologer.storage.Storage;
import chronologer.task.TaskList;
import chronologer.ui.UiMessageHandler;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.util.concurrent.TimeUnit;

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
     * Terminates the program.
     */
    public void execute(TaskList tasks, Storage storage) throws ChronologerException {
        storage.saveFile(tasks.getTasks());
        System.exit(0);
    }

}

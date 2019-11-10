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
     * Terminates the program after saving the file.
     */
    public void execute(TaskList tasks, Storage storage, ChronologerStateList history) throws ChronologerException {
        storage.saveFile(tasks.getTasks());
        // This is the proper way to exit the JVM.
        Runtime.getRuntime().exit(0);
    }

}

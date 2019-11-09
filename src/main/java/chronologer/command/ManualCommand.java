package chronologer.command;

import chronologer.exception.ChronologerException;
import chronologer.storage.Storage;
import chronologer.task.TaskList;
import chronologer.ui.UiTemporary;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Allows the user to delete a particular task from their task list based on
 * index.
 *
 * @author Sai Ganesh Suresh
 * @version v1.3
 */
public class ManualCommand extends Command {

    private static final String UG_URI = "https://github.com/AY1920S1-CS2113-T13-3/main/blob/master/docs/%5BAY1920S1-"
            + "CS2113-T13-3%5D-Chronologer-UG.pdf";

    /**
     * Removes the task from the TaskList and saves the updated TaskList to
     * persistent storage.
     *
     * @param tasks   Holds the list of all the tasks the user has.
     * @param storage Allows the saving of the file to persistent storage.
     */
    @Override
    public void execute(TaskList tasks, Storage storage) throws ChronologerException {
        try {
            Desktop desktop = Desktop.getDesktop();
            desktop.browse(new URI(UG_URI));
        } catch (URISyntaxException | IOException e) {
            UiTemporary.printOutput("Unable to open our guide!");
            logger.writeLog(e.toString(), this.getClass().getName());
            throw new ChronologerException("Unable to open our guide!");
        }

    }
}
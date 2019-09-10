import java.util.*;
import java.io.*;
import java.text.*;

/**
 * Carries out the necessary operations to close Duke; saving all existing Tasks to disk
 * in text format, and sending the farewell message.
 */

public class CloseCommand extends Command {
    /**
     * Saves all existing Tasks in the TaskList to disk in a text format, sends a farewell
     * message, and sets the exit code to true to halt the Duke program.
     * @param ui An instance of the user interface.
     * @param storage An instance of the Storage class.
     * @param tasks The TaskList, containing all the created Tasks thus far.
     * @throws ParseException if any date is un-parsable
     * @throws IOException if there is an error in saving Tasks to disk
     */
    public void execute(Ui ui, Storage storage, TaskList tasks) throws ParseException, IOException {
        storage.saveToFile(tasks.getAllItems());
        ui.farewell();
        setExitCode();
    }
}
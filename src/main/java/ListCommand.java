import java.util.*;
import java.io.*;
import java.text.*;

/**
 * Shows the TaskList of all the currently existing Tasks in String format.
 */

public class ListCommand extends Command {
    /**
     * Prints all currently existing Tasks in String format, to be displayed on Duke's CLI.
     * @param ui An instance of the user interface.
     * @param storage An instance of the Storage class.
     * @param tasks The TaskList, containing all the created Tasks thus far.
     */
    public void execute(Ui ui, Storage storage, TaskList tasks) {
        ui.printTaskArray("Here are the tasks in your list", tasks.generateList());
    }
}
import java.util.*;
import java.io.*;
import java.text.*;

/**
 * Carries out the necessary operations to search for a Task in the TaskList,
 * according to the keyword inputted by the user.
 */

public class FindCommand extends Command {
    protected String query;

    /**
     * Constructor for a FindCommand.
     * @param query the keyword to be used in searching the TaskList
     */
    public FindCommand(String query) {
        this.query = query;
    }

    /**
     * This method will search the TaskList for Tasks which match the keyword
     * provided by the user, and print a message displaying those Tasks.
     * @param ui An instance of the user interface.
     * @param storage An instance of the Storage class.
     * @param tasks The TaskList, containing all the created Tasks thus far.
     */
    public void execute(Ui ui, Storage storage, TaskList tasks) {
        ui.printTaskArray("Here are the matching tasks in your list:", tasks.searchItems(query));
    }
}
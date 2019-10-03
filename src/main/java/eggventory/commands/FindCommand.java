package eggventory.commands;

import eggventory.TaskList;
import eggventory.Ui;
import eggventory.Storage;
import eggventory.enums.CommandType;

/**
 * Command objects for searching for tasks by name.
 */
public class FindCommand extends Command {
    private String search;

    public FindCommand(CommandType type, String search) {
        super(type);
        this.search = search;
    }

    /**
     * Allows the user to search for task descriptions that match a given string.
     * Prints the list of tasks that match. Alternatively prints a message if none are found.
     */
    @Override
    public void execute(TaskList list, Ui ui, Storage storage) {
        int max = list.getSize();
        boolean found = false;

        String listString = "";
        for (int i = 0; i < max; i++) {
            if (list.getTask(i).getDescription().contains(search)) {
                // Adding task to print with associated index to final string
                // TODO: Change to stringbuilder - Raghav
                listString += (i + 1 + ". " + list.getTask(i).toString() + "\n");
                found = true;
            }
        }

        if (!found) {
            ui.print("Sorry, I could not find any tasks containing the description \""
                    + search + "\".\nPlease try a different search string.");
        } else {
            ui.print(listString);
        }
    }
}


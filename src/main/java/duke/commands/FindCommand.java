package duke.commands;

import duke.TaskList;
import duke.Ui;
import duke.Storage;
import duke.enums.CommandType;

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

        for (int i = 0; i < max; i++) {
            if (list.getTask(i).getDescription().contains(search)) {
                System.out.print(i + 1 + ". "); //Print the index of the task.
                list.getTask(i).printTaskDetails();
                found = true;
            }
        }

        if (!found) {
            ui.print("Sorry, I could not find any tasks containing the description \"" + search + "\".\n" +
                    "Please try a different search string.");
        }
    }

}


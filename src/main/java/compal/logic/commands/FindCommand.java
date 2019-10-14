package compal.logic.commands;

import compal.commons.Compal;
import compal.logic.parser.CommandParser;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;

import java.text.ParseException;
import java.util.Scanner;

import static compal.commons.Messages.MESSAGE_MISSING_COMMAND_ARG;

/**
 * Executes user command "find".
 */
public class FindCommand extends Command implements CommandParser {

    private TaskList taskList;

    /**
     * Constructs FindCommand object.
     *
     * @param d Compal.
     */
    public FindCommand(Compal d) {
        super(d);
        this.taskList = d.tasklist;
    }

    /**
     * Displays search result of keyword input by user.
     *
     * @param userIn Entire user input string.
     * @throws Compal.DukeException If user input after "find" is empty.
     */
    @Override
    public void parseCommand(String userIn) throws Compal.DukeException {
        Scanner scanner = new Scanner(userIn);
        scanner.next();
        if (!scanner.hasNext()) {
            compal.ui.printg(MESSAGE_MISSING_COMMAND_ARG);
            throw new Compal.DukeException(MESSAGE_MISSING_COMMAND_ARG);
        }
        String searchTerm = scanner.next();

        if (taskList.arrlist.isEmpty()) {
            compal.ui.printg("No task to find.");
        }
        Boolean isEmpty = true;
        for (Task task : taskList.arrlist) {
            if (task.getDescription().contains(searchTerm)) {
                if (isEmpty == true) {
                    compal.ui.printg("Your search result for the keyword " + searchTerm + ": \n");
                }
                compal.ui.printg(task.toString());
                isEmpty = false;
            }
        }

        if (isEmpty) {
            compal.ui.printg("No result found for " + searchTerm);
        }
    }
}

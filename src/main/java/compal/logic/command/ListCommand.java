package compal.logic.command;

import compal.commons.LogUtils;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;

import java.util.ArrayList;
import java.util.logging.Logger;

//@@author SholihinK

/**
 * The ListCommand Class handles all list commands.
 */
public class ListCommand extends Command {
    public static final String MESSAGE_USAGE = "list\n\t"
        + "Format: list\n\t"
        + "e.g. \"list \\type deadline\"\n\n"
        + "This command will list all the deadline stored in COMPal.";

    private static final String LIST_PREFIX = "Here are the tasks in your list: \n";
    public static final String LIST_EMPTY = "Looks like your list is empty!\nStart adding in your task "
        + "by looking at the help command!";
    private String type = "";
    private String listPrefixTwo;

    private static final Logger logger = LogUtils.getLogger(ListCommand.class);

    /**
     * Construct the ListCommand class.
     *
     * @param type the type to be display if valid.
     */
    public ListCommand(String type) {
        listPrefixTwo = "Here are the stored " + type + " in your list:\n\n";
        if ("deadline".equals(type)) {
            this.type = "D";
        } else if ("event".equals(type)) {
            this.type = "E";
        }
    }


    @Override
    public CommandResult commandExecute(TaskList taskList) {
        logger.info("Executing list command");
        ArrayList<Task> toList = taskList.getArrList();
        String finalList;
        if (type.isEmpty()) {
            finalList = LIST_PREFIX;
        } else {
            finalList = listPrefixTwo;
        }
        int count = 1;
        for (Task t : toList) {
            if (type.isEmpty()) {
                String taskString = count++ + "." + t.toString() + "\n";
                finalList += taskString;
            } else {
                if (t.getSymbol().equals(type)) {
                    String taskString = count++ + "." + t.toString() + "\n";
                    finalList += taskString;
                }
            }

        }

        if (count == 1) {
            finalList = LIST_EMPTY;
        }
        return new CommandResult(finalList, false);
    }
}
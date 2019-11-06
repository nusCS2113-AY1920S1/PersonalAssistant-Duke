package compal.logic.command;

import compal.commons.LogUtils;
import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;

import java.util.logging.Logger;

public class FindCommand extends Command {

    public static final String MESSAGE_USAGE = "find\n\t"
            + "Format: find <description>\n\n\t"
            + "Note: content in \"<>\": need to be fulfilled by the user\n\n\t"
            + "This command will search for all tasks containing the description\n"
            + "Examples:\n\t"
            + "find cs2106\n\t\t"
            + "show all tasks containing cs2106 in their description";
    private String keyWord;
    private static final Logger logger = LogUtils.getLogger(FindCommand.class);

    public FindCommand(String keyWord) {
        this.keyWord = keyWord;
    }


    @Override
    public CommandResult commandExecute(TaskList taskList) throws CommandException {
        logger.info("Executing find command");
        if (taskList.getArrList().isEmpty()) {
            return new CommandResult("You have no tasks at the moment!",false);
        }
        boolean isEmpty = true;
        StringBuilder sb = new StringBuilder();
        for (Task task : taskList.getArrList()) {
            if (task.getDescription().toUpperCase().contains(keyWord.toUpperCase())) {
                if (isEmpty) {
                    sb.append("Your search result for the keyword ").append(keyWord).append(": \n");
                    isEmpty = false;
                }
                sb.append(task.toString()).append("\n");

            }
        }

        if (isEmpty) {
            return new CommandResult("No result found for " + keyWord,false);
        }
        return new CommandResult(sb.toString(),false);
    }

}

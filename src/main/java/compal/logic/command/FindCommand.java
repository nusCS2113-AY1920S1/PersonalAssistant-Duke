package compal.logic.command;

import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;

public class FindCommand extends Command {

    String keyWord;

    public FindCommand(String keyWord) {
        this.keyWord = keyWord;
    }


    @Override
    public CommandResult commandExecute(TaskList taskList) throws CommandException {

        if (taskList.getArrList().isEmpty()) {
            return new CommandResult("You have no tasks at the moment!",false);
        }
        Boolean isEmpty = true;
        StringBuilder sb = new StringBuilder();
        for (Task task : taskList.getArrList()) {
            if (task.getDescription().contains(keyWord)) {
                if (isEmpty) {
                    sb.append("Your search result for the keyword \" + searchTerm + \": \\n");
                    isEmpty = false;
                }
                sb.append(task.toString() + "\n");

            }
        }

        if (isEmpty) {
            return new CommandResult("No result found for " + keyWord,false);
        }
        return new CommandResult(sb.toString(),false);
    }

}

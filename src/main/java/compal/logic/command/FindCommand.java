package compal.logic.command;

import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;

public class FindCommand extends Command {

    private String keyWord;

    public FindCommand(String keyWord) {
        this.keyWord = keyWord;
    }


    @Override
    public CommandResult commandExecute(TaskList taskList) throws CommandException {

        if (taskList.getArrList().isEmpty()) {
            return new CommandResult("You have no tasks at the moment!",false);
        }
        boolean isEmpty = true;
        StringBuilder sb = new StringBuilder();
        for (Task task : taskList.getArrList()) {
            if (task.getDescription().toUpperCase().contains(keyWord.toUpperCase())) {
                if (isEmpty) {
                    sb.append("Your search result for the keyword ").append(keyWord).append(": \\n");
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

package compal.logic.command;

import compal.model.tasks.Task;
import compal.model.tasks.TaskList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * The ListCommand Class handles all list commands.
 */
public class ListCommand extends Command {
    private static final String LIST_PREFIX = "Here are the tasks in your list: \n";

    @Override
    public CommandResult commandExecute(TaskList taskList) {
        Comparator<Task> compareByDateTime = Comparator.comparing(Task::getDate);
        ArrayList<Task> toList = taskList.getArrList();
        Collections.sort(toList, compareByDateTime);
        String finalList = LIST_PREFIX;
        int count = 1;
        for (Task t : toList) {
            // uiUtil.printg(count++ + "." + t.toString());
            String taskString = count++ + "." + t.toString() + "\n";
            finalList += taskString;
        }
        return new CommandResult(finalList, false);
    }
}
package dolla.command;

import dolla.ui.Ui;
import dolla.task.Task;
import dolla.task.TaskList;

import java.util.ArrayList;

/**
 * FindStringCommand is a Command used to find tasks corresponding to the user input.
 */
public abstract class FindStringCommand extends Command {

    protected String inputLine;
    protected String commandName;

    public FindStringCommand(String inputLine) {
        this.inputLine = inputLine;
        this.commandName = "find";
    }

    /**
     * Find tasks corresponding to the user input from the specified TaskList.
     * <p>
     *     The method first retrieves the String to search for from 'inputLine'.
     *     The method then looks through all the tasks in the specified TaskList
     *     and checks if the recently retrieved string is a substring of any of
     *     the tasks. Any matches to the task are stored in an ArrayList that gets
     *     printed subsequently.
     * </p>
     * <p>
     *     If the user did not input any strings to search for, an error will be printed
     *     and the method will return without doing anything.
     * </p>
     * @param tasks The TaskList containing the tasks to be searched.
     */
    //@Override
    public void execute(TaskList tasks) {

        String searchStr = "";
        ArrayList<String> msg = new ArrayList<String>();

        try {
            searchStr = inputLine.substring(commandName.length() + 1);
        } catch (IndexOutOfBoundsException e) {
            msg.add("Please use the format 'find <string>'");
            Ui.printMsg(msg);
            return;
        }

        // Find tasks that match the searchStr and add into itemsFound
        ArrayList<String> itemsFound = new ArrayList<String>();
        for (Task currTask : tasks.get()) {
            String taskStr = currTask.getTask();
            if (taskStr.indexOf(searchStr) != -1) {
                itemsFound.add(taskStr);
            }
        }
        
        if (itemsFound.isEmpty()) {
            msg.add("There are no matching tasks in your list :(");
        } else {
            msg.add("Here are the matching tasks in your list:");
            for (int i = 0; i < itemsFound.size(); i++) {
                msg.add((i + 1) + "."  + itemsFound.get(i));
            }
        }

        Ui.printMsg(msg);
    }

}

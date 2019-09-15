package command;

import storage.Storage;
import task.Task;
import task.TaskList;
import ui.Ui;

import java.util.ArrayList;

/**
 * Represents a command from user to find tasks containing keywords specified.
 * Inherits from Command class.
 * @author Zhang Yue Han
 */
public class FindCommand extends Command {

    /**
     * The String which holds the value of the query word from the user known within class.
     */
    protected String searchPhrase;

    /**
     * Assigns the searchPhrase variable to take on the value of the requested word.
     * @param query the word to be searched for obtained from user input
     */
    public FindCommand(String query) {
        searchPhrase = query;
    }


    /**
     * Finds task in the task array list and displays the temporarily stored found items
     * to the user.
     * @param ui Ui object used to display information to the user
     * @param tasks TaskList object which contains the task array list holding the task info
     * @param storage Storage object which is used to write new task to file
     */
    @Override
    public void execute(Ui ui, TaskList tasks, Storage storage) {
        //ask ui to print something
        //ask tasks to store the thing in arraylist
        //ask storage to write to file
        ArrayList<Task> foundItems = tasks.findItem(searchPhrase);
        ui.showFound(foundItems);
    }
}

package command;

import exception.DukeException;
import parser.CommandParams;
import task.TaskList;
import ui.Ui;
import storage.Storage;

import java.util.ArrayList;

/**
 * Represents a specified command as FindCommand by extending the {@code Command} class.
 * Finds all tasks relevant with the searched keyword.
 * Responses with the result.
 */
public class FindCommand extends Command {

    /**
     * Constructs a {@code FindCommand} object
     * with given searched keyword.
     */
    public FindCommand() {
        super(null, null, null, null);
    }

    /**
     * Finds relevant tasks from taskList of Duke with the given keyword and
     * responses the result to user by using ui of Duke.
     *
     * @param tasks The taskList of Duke.
     * @param ui The ui of Duke.
     * @param storage The storage of Duke.
     * @throws DukeException if a search string was not given.
     */
    public void execute(CommandParams commandParams, TaskList tasks, Ui ui, Storage storage) throws DukeException {
        if (commandParams.getMainParam() == null) {
            throw new DukeException("☹ OOPS!!! I don't what to find.");
        }
        ArrayList<Integer> matchedList = tasks.find(commandParams.getMainParam());
        if (matchedList.size() == 0) {
            ui.println("No results found.");
            return;
        }
        ui.println("Here are the matching tasks in your list:");
        int count = 1;
        for (int i : matchedList) {
            ui.println(count + "." + tasks.getTaskInfo(i));
            count++;
        }
    }
}

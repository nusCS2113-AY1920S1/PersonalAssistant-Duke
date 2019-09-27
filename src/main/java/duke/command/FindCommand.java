package duke.command;

import duke.TaskList;
import duke.Ui;
import duke.Storage;
import duke.exception.DukeException;
import duke.task.Task;

import java.util.ArrayList;

/**
 * Represents a Command that finds and displays specific tasks in the TaskList.
 */
public class FindCommand extends Command {

    private String line;

    /**
     * Constructor for FindCommand.
     *
     * @param line Command inputted by user.
     */
    public FindCommand(String line) {
        super();
        this.line = line;
    }

    /**
     * Finds the Tasks that contain the keyword(s) specified
     * by the user after processing the Command.
     * Displays the Tasks found after collating them.
     *
     * @param arr     Instance of TaskList that stores Task objects.
     * @param ui      Instance of Ui that is responsible for visual feedback.
     * @param storage Instance of Storage that enables the reading and writing of Task
     *                objects to hard disk.
     * @throws DukeException Catches invalid commands given by user.
     */
    public void execute(TaskList arr, Ui ui, Storage storage) throws DukeException {
        String[] lineSplit = line.split(" ");
        if (lineSplit.length == 1) {
            throw new DukeException("OOPS!!! The description of a find cannot be empty.");
        }
        String item = lineSplit[1].trim();
        ArrayList<Task> foundArr = new ArrayList<>();
        for (int i = 0; i < arr.getSize(); i++) {
            if (arr.getTask(i).getLine().contains(item)) {
                foundArr.add(arr.getTask(i));
            }
        }
        if (foundArr.size() == 0) {
            System.out.println("\tThere are no matching tasks in your list!");
        } else {
            ui.showLine();
            System.out.println("\tHere are the matching tasks in your list:");
            for (int i = 0; i < foundArr.size(); i++) {
                System.out.println("\t" + (i + 1) + ". " + foundArr.get(i));
            }
            ui.showLine();
        }
    }

    /**
     * Checks if ExitCommand is called for Duke to terminate.
     *
     * @return false.
     */
    public boolean isExit() {
        return false;
    }
}

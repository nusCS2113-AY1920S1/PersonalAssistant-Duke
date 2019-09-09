package duke.command;

import duke.TaskList;
import duke.Ui;
import duke.Storage;
import duke.exception.DukeException;
import duke.task.Task;

import java.util.ArrayList;

/**
 * Represents a <code>Command</code> that finds and displays specific tasks in the <code>TaskList</code>.
 */
public class findCommand extends Command {

    String line;

    /**
     * Constructor for <code>findCommand</code>.
     * @param line Command inputted by user.
     */
    public findCommand(String line) {
        super();
        this.line = line;
    }

    /**
     * Finds the <code>Tasks</code> that contain the keyword(s) specified
     * by the user after processing the command.
     * Displays the <code>Tasks</code> found after collating them.
     * @param arr Instance of <code>TaskList</code> that stores <code>Task</code> objects.
     * @param ui Instance of <code>Ui</code> that is responsible for visual feedback.
     * @param storage Instance of <code>Storage</code> that enables the reading and writing of <code>Task</code>
     *      *         objects to harddisk.
     * @throws DukeException Catches invalid commands given by user.
     */
    public void execute(TaskList arr, Ui ui, Storage storage) throws DukeException {
        String linesplit[] = line.split(" ");
        if (linesplit.length == 1) {
            throw new DukeException("\u2639 OOPS!!! The description of a find cannot be empty.");
        }
        String item = linesplit[1].trim();
        ArrayList<Task> foundarr = new ArrayList<>();
        for (int i = 0; i < arr.getSize(); i++) {
            if (arr.getTask(i).getLine().contains(item)) {
                foundarr.add(arr.getTask(i));
            }
        }
        if (foundarr.size() == 0) {
            System.out.println("\tThere are no matching tasks in your list!");
        }
        else {
            ui.showLine();
            System.out.println("\tHere are the matching tasks in your list:");
            for (int i = 0; i < foundarr.size(); i++) {
                System.out.println("\t" + (i + 1) + ". " + foundarr.get(i));
            }
            ui.showLine();
        }
    }
    /**
     * Checks if <code>exitCommand</code> is called for <code>Duke</code>
     * to terminate.
     * @return false.
     */
    public boolean isExit() {
        return false;
    }
}

package command;

import exception.DukeException;
import storage.Storage;
import task.TaskList;
import ui.Ui;
import java.io.IOException;
import task.DoAfter;

/**
 * Command to add new DoAfter tasks to task list.
 */
public class DoAfterCommand extends Command {
    String[] splitD;

    /**
     * Creates new To Do to be done after a specific task/time.
     * @param input input from user.
     * @param splitStr tokenized input.
     * @throws DukeException if description is empty or format not followed.
     */
    public DoAfterCommand(String input, String[] splitStr) throws DukeException {
        if (splitStr.length == 1) {
            throw new DukeException("☹ OOPS!!! The description of todo cannot be empty.");
        }
        String tempD = input.substring(8);
        if (!tempD.contains(" /after ")) {
            throw new DukeException("☹ OOPS!!! Please add a time for the task.");
        }
        this.splitD = tempD.split(" /after ");
    }

    /**
     *
     * @param tasks task list.
     * @param ui user interface.
     * @param storage handles read write of text file.
     * @throws IOException
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException {
        DoAfter after = new DoAfter(splitD[0], splitD[1]);
        tasks.add(after);
        storage.saveToFile(tasks);
        ui.addToOutput("Got it. I've added this task:\n" +
                after.toString() + "\n" +
                "Now you have " + tasks.size() + " task(s) in the list");
    }
}

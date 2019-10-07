package command;

import exception.DukeException;
import storage.Storage;
import task.Deadline;
import task.TaskList;
import ui.Ui;

import java.io.IOException;

/**
 * Class to interpret task.Deadline command
 */
public class DeadlineCommand extends Command {

    /**
     * String array to contain the parts of the command.
     */
    String[] splitD;

    /**
     * Constructor for task.Deadline command.
     * @param input raw user input
     * @param splitStr string array containing the individual words from user input
     * @throws DukeException if user input does not follow correct format
     */
    public DeadlineCommand(String input, String[] splitStr) throws DukeException {
        if (splitStr.length == 1) {
            throw new DukeException("☹ OOPS!!! The description of a deadline cannot be empty.");
        }
        String tempD = input.substring(9);
        if (!tempD.contains(" /by ")) {
            throw new DukeException("☹ OOPS!!! Please add a deadline for the task.");
        }
        this.splitD = tempD.split(" /by ");
        if (!isValidDateTime(splitD[1])) {
            throw  new DukeException("Please enter correct date time format: dd/mm/yyyy hhmm");
        }
    }

    /**
     * Execute command.
     * @param tasks task list
     * @param ui user interface
     * @param storage handles read write of text file
     * @throws IOException for IO exception
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException {
        Deadline deadline = new Deadline(splitD[0], splitD[1]);
        tasks.add(deadline);
        storage.saveToFile(tasks);
        ui.addToOutput("Got it. I've added this task:\n"
                + deadline.toString() + "\n"
                + "Now you have " + tasks.size() + " task(s) in the list.");
    }
}

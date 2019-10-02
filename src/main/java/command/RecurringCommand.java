package command;

import exception.DukeException;
import storage.Storage;
import task.Event;
import task.Recurring;
import task.TaskList;
import ui.Ui;

import java.io.IOException;

/**
 * command.Command to create a recurring task
 */
public class RecurringCommand extends Command {
    private String[] splitR1;
    private String[] splitR;

    /**
     * Create new Event object.
     * @param input from user
     * @param splitStr tokenized input
     * @throws DukeException if description empty or format not followed
     */
    public RecurringCommand(String input, String[] splitStr) throws DukeException {
        if (splitStr.length == 1) {
            throw new DukeException("☹ OOPS!!! The description of a recurring task cannot be empty.");
        }
        String tempR = input.substring(10);
        if (!tempR.contains(" /at ")) {
            throw new DukeException("☹ OOPS!!! Please add a date for the task.");
        }
        if (!tempR.contains("/every")){
            throw new DukeException("☹ OOPS!!! Please add a frequency for the recurrence: Day, Week or Month.");
        }
        this.splitR1 = tempR.split("/every");

        this.splitR = splitR1[0].split(" /at ");
        if (!isValidDateTime(splitR[1])) {
            throw  new DukeException("Please enter correct date time format: dd/mm/yyyy hhmm");
        }
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException, IOException {
        Recurring recurring = new Recurring(splitR[0], splitR[1], splitR1[1].substring(1).toUpperCase());
        tasks.add(recurring);
        storage.saveToFile(tasks);
        ui.showString("Got it. I've added this task:\n"
                + recurring.toString() + "\n"
                + "Now you have " + tasks.size() + " task(s) in the list.");
    }
}

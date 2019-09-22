package owlmoney.logic.command;

import java.util.ArrayList;

import owlmoney.logic.exception.DukeException;
import owlmoney.model.task.Task;
import owlmoney.model.task.TaskList;
import owlmoney.storage.Storage;
import owlmoney.ui.Ui;

/**
 * Class representing a command to check for reminder of task that will be due within the specify day,
 * and to display those items.
 * The numOfDay is an integer that specify the next number of day of task to be reminded on.
 */
public class ReminderCommand extends Command {
    private final int numOfDay;

    /**
     * Creates a new ReminderCommand with the specified number of days.
     *
     * @param num The next number of day specify by the user for the program to search for task to be reminded.
     */
    public ReminderCommand(String num) throws DukeException {
        String numTrim = num.trim();
        String pattern = "^[0-9]+$";
        if (!numTrim.matches(pattern)) {
            throw new DukeException("The task number should be numeric and cannot be blank");
        } else {
            try {
                this.numOfDay = Integer.parseInt(numTrim);
            } catch (NumberFormatException exceptionMessage) {
                throw new DukeException("The number cannot exceed 9 digits");
            }
        }
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param tasks The task list.
     * @param ui The user interface displaying events on the task list.
     * @param storage The place where tasks will be stored.
     */
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ArrayList<Task> output = tasks.checkReminder(numOfDay);
        if (output.size() == 0) {
            ui.printMessage("There are no deadline or event in the next " + numOfDay + " days");
        } else {
            ui.printMessage("Here are the reminder for the deadline and event in the next " + numOfDay + " days");
            for (int i = 0; i < output.size(); i++) {
                ui.printMessage(i + 1 + "." + output.get(i).toString());
            }
        }
    }
}

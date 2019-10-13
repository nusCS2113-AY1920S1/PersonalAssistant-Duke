package duke.command.dukecommands;

import duke.command.Command;
import duke.exception.DukeException;
import duke.storage.Storage;
import duke.list.tasklist.TaskList;
import duke.ui.Ui;

import java.text.ParseException;

import static duke.common.Messages.COMMAND_RECURRING;
import static duke.common.Messages.ERROR_MESSAGE_GENERAL;
import static duke.common.Messages.MESSAGE_FOLLOWUP_NUll;
import static duke.common.Messages.ERROR_MESSAGE_RANDOM;
import static duke.common.Messages.ERROR_MESSAGE_RECURRING_DETAILS_NULL;
import static duke.common.Messages.ERROR_MESSAGE_RECURRING_FREQUENCY_NULL;

/**
 * Handles task that recur over time.
 */
public class RecurringCommand extends Command {

    protected String details = "";
    protected String dayOrDate = "";
    protected String frequency = "";

    /**
     * Constructor for class RecurringCommand.
     * @param userInput String containing input command from user
     */
    public RecurringCommand(String userInput) {
        this.userInput = userInput;
    }

    /**
     * Processes the recurring command to add recurring tasks to task list.
     * @param taskList contains the task list
     * @param ui deals with interactions with the user
     * @param storage deals with loading tasks from the file and saving tasks in the file
     * @throws DukeException if Duke cannot recognize the user input or user inputs a wrong format for the date and time
     * @throws ParseException if there is any error in parsing the day or date input by the user.
     */

    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException, ParseException {
        if (userInput.trim().equals(COMMAND_RECURRING)) {
            throw new DukeException(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
        } else if (userInput.trim().charAt(9) == ' ') {

            String description = userInput.split("\\s",2)[1].trim();
            if (description.contains("/daily")) {
                this.frequency = "daily";
                this.details = description.split("/daily", 2)[0].trim();
                if (details.isEmpty()) {
                    throw new DukeException(ERROR_MESSAGE_RANDOM);
                } else {
                    taskList.addRecurringTask(details, frequency, dayOrDate);
                    storage.saveFile(taskList);
                }
            } else if (description.contains("/weekly")) {
                this.frequency = "weekly";
                this.details = description.split("/weekly", 2)[0].trim();
                this.dayOrDate = description.split("/weekly", 2)[1].trim();
                checkFullInfoProvided(taskList, storage);
            } else if (description.contains("/monthly")) {
                frequency = "monthly";
                details = description.split("/monthly", 2)[0].trim();
                dayOrDate = description.split("/monthly", 2)[1].trim();
                checkFullInfoProvided(taskList, storage);
            } else {
                throw new DukeException(ERROR_MESSAGE_RECURRING_FREQUENCY_NULL);
            }
        } else {
            throw new DukeException(ERROR_MESSAGE_RANDOM);
        }
    }

    /**
     * Checks if both the details and the day or date of the recurring tasks have been input by the user.
     * If either of the information is missing
     * @param taskList contains the task list
     * @param storage deals with loading tasks from the file and saving tasks in the file
     * @throws DukeException if either the details of the day/date is not input by the user.
     * @throws ParseException if there is any error in parsing the day or date input by the user.
     */
    private void checkFullInfoProvided(TaskList taskList, Storage storage) throws DukeException, ParseException {
        if (details.isEmpty() || dayOrDate.isEmpty()) {
            throw new DukeException(ERROR_MESSAGE_RECURRING_DETAILS_NULL);
        } else {
            taskList.addRecurringTask(details, frequency, dayOrDate);
            storage.saveFile(taskList);
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}

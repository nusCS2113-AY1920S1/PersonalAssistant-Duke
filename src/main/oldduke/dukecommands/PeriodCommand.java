package duke.command.dukecommands;

import duke.command.Command;
import duke.exception.DukeException;
import duke.storage.Storage;
import duke.list.tasklist.TaskList;
import duke.ui.Ui;

import static duke.common.Messages.COMMAND_PERIOD;
import static duke.common.Messages.ERROR_MESSAGE_GENERAL;
import static duke.common.Messages.ERROR_MESSAGE_PERIOD;
import static duke.common.Messages.ERROR_MESSAGE_PERIOD2;
import static duke.common.Messages.ERROR_MESSAGE_RANDOM;
import static duke.common.Messages.MESSAGE_FOLLOWUP_NUll;

public class PeriodCommand extends Command {

    /**
     * Constructor for class PeriodCommand.
     * @param userInput String containing input command from user
     */
    public PeriodCommand(String userInput) {
        this.userInput = userInput;
    }

    /**
     * Processes the period command to add period task to task list.
     * @param taskList contains the task list
     * @param ui deals with interactions with the user
     * @param storage deals with loading tasks from the file and saving tasks in the file
     * @throws DukeException if Duke cannot recognize the user input or user inputs a wrong format for the date and time
     */
    // Can refactor deep if-else nesting.

    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        if (userInput.trim().equals(COMMAND_PERIOD)) {
            throw new DukeException(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
        } else if (userInput.trim().charAt(6) == ' ') {
            String description = userInput.split("\\s",2)[1].trim();
            if (description.contains("/between")) {
                String details = description.split("/between", 2)[0].trim();
                String period = description.split("/between", 2)[1].trim();
                if (!period.isEmpty()) {
                    if (period.contains("/and")) {
                        String startDate = period.trim().split("/and", 2)[0].trim();
                        String endDate = period.trim().split("/and", 2)[1].trim();
                        if (startDate.isEmpty() || endDate.isEmpty()) {
                            throw new DukeException(ERROR_MESSAGE_PERIOD2);
                        } else {
                            taskList.addPeriodTask(details, startDate, endDate);
                            storage.saveFile(taskList);
                        }
                    } else {
                        throw new DukeException(ERROR_MESSAGE_PERIOD2);
                    }
                }
                if (details.isEmpty() || period.isEmpty()) {
                    throw new DukeException(ERROR_MESSAGE_PERIOD);
                }
            } else {
                throw new DukeException(ERROR_MESSAGE_PERIOD);
            }
        } else {
            throw new DukeException(ERROR_MESSAGE_RANDOM);
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}

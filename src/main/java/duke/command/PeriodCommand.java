package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.tasklist.TaskList;
import duke.ui.Ui;

import static duke.common.Messages.*;
import static duke.common.Messages.ERROR_MESSAGE_RANDOM;

public class PeriodCommand extends Command {

    /**
     * Constructor for class PeriodCommand
     * @param userInputCommand String containing input command from user
     */
    public PeriodCommand(String userInputCommand) {
        this.userInputCommand = userInputCommand;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        if(userInputCommand.trim().equals(COMMAND_PERIOD)) {
            throw new DukeException(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
        } else if(userInputCommand.trim().charAt(6) == ' ') {
            String description = userInputCommand.trim().split("\\s",2)[1];
            if(description.contains("/between")) {
                String details = description.trim().split("/between", 2)[0];
                String period = description.trim().split("/between", 2)[1];
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
                if(details.isEmpty() || period.isEmpty()) {
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

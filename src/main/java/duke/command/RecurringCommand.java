package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.tasklist.TaskList;
import duke.ui.Ui;

import java.text.ParseException;

import static duke.common.Messages.*;

public class RecurringCommand extends Command {

    protected String details = "";
    protected String dayOrDate = "";
    protected String frequency = "";

    public RecurringCommand(String userInputCommand) {
        this.userInputCommand = userInputCommand;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException, ParseException {
        if (userInputCommand.trim().equals(COMMAND_RECURRING)) {
            throw new DukeException(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
        }
        else if (userInputCommand.trim().charAt(9) == ' ') {
            String description = userInputCommand.split("\\s",2)[1].trim();

            if (description.contains("/daily")) {
                this.frequency = "daily";
                this.details = description.split("/daily", 2)[0].trim();
                if (details.isEmpty()) {
                    throw new DukeException(ERROR_MESSAGE_RANDOM);
                } else {
                    taskList.addRecurringTask(details, frequency, dayOrDate);
                    storage.saveFile(taskList);
                }
            }
            // can combine here?
            else if (description.contains("/weekly")) {
                this.frequency = "weekly";
                this.details = description.split("/weekly", 2)[0].trim();
                this.dayOrDate = description.split("/weekly", 2)[1].trim();
                checkFullInfoProvided(taskList, storage);
            }
            else if (description.contains("/monthly")) {
                frequency = "monthly";
                details = description.split("/monthly", 2)[0].trim();
                dayOrDate = description.split("/monthly", 2)[1].trim();
                checkFullInfoProvided(taskList, storage);
            }
            /*
            else if (description.contains("/yearly")) {
                frequency = "yearly";
                details = description.split("/yearly", 2)[0].trim();
                dayOrDate = description.split("/yearly", 2)[1].trim();
                checkFullInfoProvided(taskList, storage);
            }
            */
            else {
                throw new DukeException(ERROR_MESSAGE_RECURRING_FREQUENCY_NULL);
            }

        } else {
            throw new DukeException(ERROR_MESSAGE_RANDOM);
        }
    }

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

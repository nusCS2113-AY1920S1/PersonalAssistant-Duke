package duke.command.dukecommands;

import duke.command.Command;
import duke.exception.DukeException;
import duke.storage.Storage;
import duke.list.tasklist.TaskList;
import duke.ui.Ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static duke.common.Messages.COMMAND_DEADLINE;
import static duke.common.Messages.ERROR_MESSAGE_GENERAL;
import static duke.common.Messages.MESSAGE_FOLLOWUP_NUll;
import static duke.common.Messages.ERROR_MESSAGE_DEADLINE;
import static duke.common.Messages.ERROR_MESSAGE_INVALID_DATE;
import static duke.common.Messages.ERROR_MESSAGE_RANDOM;

/**
 * Handles the deadline command and inherits all the fields and methods of Command parent class.
 */
public class DeadlineCommand extends Command {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d/M/yyyy HHmm");

    /**
     * Constructor for class DeadlineCommand.
     * @param userInput String containing input command from user
     */
    public DeadlineCommand(String userInput) {
        this.userInput = userInput;
    }

    /**
     * Processes the deadline command to add deadline task to task list.
     * @param taskList contains the task list
     * @param ui deals with interactions with the user
     * @param storage deals with loading tasks from the file and saving tasks in the file
     * @throws DukeException if Duke cannot recognize the user input or user inputs a wrong format for the date and time
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException, ParseException {
        if (userInput.trim().equals(COMMAND_DEADLINE)) {
            throw new DukeException(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
        } else if (userInput.trim().charAt(8) == ' ') {
            String description = userInput.split("\\s",2)[1].trim();
            if (description.contains("/by")) {
                String details = description.split("/by", 2)[0].trim();
                String date = description.split("/by", 2)[1].trim();
                if (details.isEmpty() || date.isEmpty()) {
                    throw new DukeException(ERROR_MESSAGE_DEADLINE);
                } else {
                    if (isParseDate(date)) {
                        taskList.addDeadlineTask(details, date);
                        storage.saveFile(taskList);
                    } else {
                        throw new DukeException(ERROR_MESSAGE_INVALID_DATE);
                    }
                }
            } else {
                throw new DukeException(ERROR_MESSAGE_DEADLINE);
            }
        } else {
            throw new DukeException(ERROR_MESSAGE_RANDOM);
        }
    }

    /**
     * Validate the format of the input date.
     * @param dateStr String containing user input date and time
     * @return true if user input the correct format for the date and time and false otherwise
     */
    private static boolean isParseDate(String dateStr) {
        try {
            simpleDateFormat.parse(dateStr);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}

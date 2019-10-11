package duke.command.dukecommands;

import duke.command.Command;
import duke.exception.DukeException;
import duke.storage.Storage;
import duke.list.tasklist.TaskList;
import duke.ui.Ui;

import java.text.ParseException;

import static duke.common.Messages.COMMAND_TENTATIVESCHEDULE;
import static duke.common.Messages.ERROR_MESSAGE_TENTATIVESCHEDULE;
import static duke.common.Messages.ERROR_MESSAGE_RANDOM;

/**
 * Handles the tentativeschedule command and inherits all the fields and methods of Command parent class.
 */
public class TentativeScheduleCommand extends Command {

    /**
     * Constructor for class TentativeCommand.
     * @param userInput String containing input command from user
     */
    public TentativeScheduleCommand(String userInput) {
        this.userInput = userInput;
    }

    /**
     * Processes the tentativeschedule command to add tasks in multiple slots in the task list.
     * @param taskList contains the task list
     * @param ui deals with interactions with the user
     * @param storage deals with loading tasks from the file and saving tasks in the file
     * @throws DukeException if Duke cannot recognize the user input
     *                      or user inputs an wrong format for the description
     */

    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException, ParseException {
        if (userInput.trim().equals(COMMAND_TENTATIVESCHEDULE)) {
            throw new DukeException(ERROR_MESSAGE_TENTATIVESCHEDULE);
        } else if (userInput.trim().charAt(17) == ' ') {
            String description = userInput.split("\\s",2)[1].trim();
            if (description.contains("/on")) {
                String details = description.split("/on", 2)[0].trim();
                String on = description.split("/on", 2)[1].trim();
                if (details.isEmpty() || on.isEmpty()) {
                    throw new DukeException(ERROR_MESSAGE_TENTATIVESCHEDULE);
                } else {
                    taskList.addTentativeSchedulingTask(details, on);
                    storage.saveFile(taskList);
                }
            } else {
                throw new DukeException(ERROR_MESSAGE_TENTATIVESCHEDULE);
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

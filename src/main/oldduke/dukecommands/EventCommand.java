package duke.command.dukecommands;

import duke.command.Command;
import duke.exception.DukeException;
import duke.storage.Storage;
import duke.list.tasklist.TaskList;
import duke.ui.Ui;

import static duke.common.Messages.ERROR_MESSAGE_EVENT;
import static duke.common.Messages.ERROR_MESSAGE_RANDOM;
import static duke.common.Messages.COMMAND_EVENT;

/**
 * Handles the event command and inherits all the fields and methods of Command parent class.
 */
public class EventCommand extends Command {

    /**
     * Constructor for class EventCommand.
     * @param userInput String containing input command from user
     */
    public EventCommand(String userInput) {
        this.userInput = userInput;
    }

    /**
     * Processes the event command to add event task to task list.
     * @param taskList contains the task list
     * @param ui deals with interactions with the user
     * @param storage deals with loading tasks from the file and saving tasks in the file
     * @throws DukeException if Duke cannot recognize the user input or user inputs a wrong format for the description
     */

    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        if (userInput.trim().equals(COMMAND_EVENT)) {
            throw new DukeException(ERROR_MESSAGE_EVENT);
        } else if (userInput.trim().charAt(5) == ' ') {
            String description = userInput.split("\\s",2)[1].trim();
            if (description.contains("/at")) {
                String details = description.split("/at", 2)[0].trim();
                String date = description.split("/at", 2)[1].trim();
                if (details.isEmpty() || date.isEmpty()) {
                    throw new DukeException(ERROR_MESSAGE_EVENT);
                } else {
                    taskList.addEventTask(details, date);
                    storage.saveFile(taskList);
                }
            } else {
                throw new DukeException(ERROR_MESSAGE_EVENT);
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

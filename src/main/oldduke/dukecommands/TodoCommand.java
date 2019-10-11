package duke.command.dukecommands;

import duke.command.Command;
import duke.exception.DukeException;
import duke.storage.Storage;
import duke.list.tasklist.TaskList;
import duke.ui.Ui;

import static duke.common.Messages.MESSAGE_FOLLOWUP_NUll;
import static duke.common.Messages.ERROR_MESSAGE_DO_AFTER;
import static duke.common.Messages.ERROR_MESSAGE_GENERAL;
import static duke.common.Messages.ERROR_MESSAGE_RANDOM;
import static duke.common.Messages.COMMAND_TODO;

/**
 * Handles the todo command and inherits all the fields and methods of Command parent class.
 */
public class TodoCommand extends Command {

    /**
     * Constructor for class TodoCommand.
     * @param userInput String containing input command from user
     */
    public TodoCommand(String userInput) {
        this.userInput = userInput;
    }

    /**
     * Processes the todo command to add tasks to task list.
     * @param taskList contains the task list
     * @param ui deals with interactions with the user
     * @param storage deals with loading tasks from the file and saving tasks in the file
     * @throws DukeException if Duke cannot recognize the user input
     */

    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        if (userInput.trim().equals(COMMAND_TODO)) {
            throw new DukeException(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
        } else if (userInput.trim().charAt(4) == ' ') {
            String description = userInput.split("\\s",2)[1].trim();
            if (description.contains("/after")) {
                String details = description.split("/after", 2)[0].trim();
                String doaftertask = description.split("/after", 2)[1].trim();
                if (details.isEmpty() || doaftertask.isEmpty()) {
                    throw new DukeException(ERROR_MESSAGE_DO_AFTER);
                } else {
                    taskList.addDoAfterTask(details, doaftertask);
                    storage.saveFile(taskList);
                }
            } else {
                taskList.addTodoTask(description);
                storage.saveFile(taskList);
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

package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.tasklist.TaskList;
import duke.ui.Ui;

import static duke.common.Messages.*;
import static duke.common.Messages.ERROR_MESSAGE_RANDOM;

public class DurationCommand extends Command {

    /**
     * Constructor for class TodoCommand
     * @param userInputCommand String containing input command from user
     */
    public DurationCommand(String userInputCommand) {
        this.userInputCommand = userInputCommand;
    }


    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        if(userInputCommand.trim().equals(COMMAND_DURATION)) {
            throw new DukeException(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
        } else if(userInputCommand.trim().charAt(5) == ' ') {
            String description = userInputCommand.trim().split("\\s",2)[1];
            if(description.contains(" /need ")) {
                String details = description.trim().split(" /need ", 2)[0];
                String need = description.trim().split(" /need ", 2)[1];
                if(details == null || need == null) {
                    throw new DukeException(ERROR_MESSAGE_DURATION);
                } else {
                    taskList.addDurationTask(details, need);
                    storage.saveFile(taskList);
                }
            } else {
                throw new DukeException(ERROR_MESSAGE_DURATION);
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

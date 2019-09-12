package command;

import exception.DukeException;
import storage.Storage;
import tasklist.TaskList;
import ui.Ui;

import static common.Messages.*;

/**
 * Handles the find command and inherits all the fields and methods of Command parent class
 */
public class FindCommand extends Command {

    /**
     * Constructor for class FindCommand
     * @param userInputCommand String containing input command from user
     */
    public FindCommand(String userInputCommand) {
        this.userInputCommand = userInputCommand;
    }

    /**
     * Processes the find command to search for tasks in task list
     * @param taskList contains the task list
     * @param ui deals with interactions with the user
     * @param storage deals with loading tasks from the file and saving tasks in the file
     * @throws DukeException if Duke cannot recognize the user input or there is no matching task found in tbe list
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        if(userInputCommand.trim().equals(COMMAND_FIND)){
            throw new DukeException(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
        }else if(userInputCommand.trim().charAt(4) == ' ') {
            String description = userInputCommand.trim().split("\\s", 2)[1];
            System.out.println(MESSAGE_FIND);
            for (int i = 0; i < taskList.findTask(description).size(); i++){
                System.out.println("     " + (i + 1) + ". " + taskList.findTask(description).get(i));
            }
        }else{
            throw new DukeException(ERROR_MESSAGE_RANDOM);
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}

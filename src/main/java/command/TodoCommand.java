package command;

import exception.DukeException;
import storage.Storage;
import tasklist.TaskList;
import ui.Ui;

import static common.Messages.*;

/**
 * Handles the todo command and inherits all the fields and methods of Command parent class
 */
public class TodoCommand extends Command {

    /**
     * Constructor for class TodoCommand
     * @param userInputCommand String containing input command from user
     */
    public TodoCommand(String userInputCommand) {
        this.userInputCommand = userInputCommand;
    }

    /**
     * Processes the todo command to add tasks to task list
     * @param taskList contains the task list
     * @param ui deals with interactions with the user
     * @param storage deals with loading tasks from the file and saving tasks in the file
     * @throws DukeException if Duke cannot recognize the user input
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException{
        if(userInputCommand.trim().equals(COMMAND_TODO)){
            throw new DukeException(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
        }else if(userInputCommand.trim().charAt(4) == ' '){
            String description = userInputCommand.trim().split("\\s",2)[1];
            taskList.addTodoTask(description);
            storage.saveFile(taskList);
        }else{
            throw new DukeException(ERROR_MESSAGE_RANDOM);
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}

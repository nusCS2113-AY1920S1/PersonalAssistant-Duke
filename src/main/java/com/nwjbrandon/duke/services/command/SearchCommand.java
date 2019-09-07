package com.nwjbrandon.duke.services.command;

import com.nwjbrandon.duke.exceptions.DukeEmptyCommandException;
import com.nwjbrandon.duke.services.task.TaskList;
import com.nwjbrandon.duke.services.validation.Parser;

public class SearchCommand extends Command {

    /**
     * Description of task.
     */
    private String taskDescription;

    /**
     * Input by user.
     */
    private String userInput;

    /**
     * Command.
     */
    private String command;

    /**
     * Create search command.
     * @param userInput input by user.
     * @param command type of command.
     */
    public SearchCommand(String userInput, String command) {
        this.userInput = userInput;
        this.command = command;
    }

    /**
     * Get description of task.
     * @return description of task.
     */
    private String getTaskDescription() {
        return taskDescription;
    }

    /**
     * Validate user input.
     * @param userInput input by user.
     * @param command type of command.
     * @return instruction in input.
     */
    private String parseCommand(String userInput, String command) throws DukeEmptyCommandException {
        return Parser.checkCommandInput(userInput, command);
    }

    /**
     * Execute command.
     * @param taskList list of tasks.
     */
    @Override
    public void execute(TaskList taskList) {
        try {
            this.taskDescription = parseCommand(userInput, command);
            taskList.searchTask(this.getTaskDescription());
        } catch (DukeEmptyCommandException e) {
            e.showError();
        }
    }

}

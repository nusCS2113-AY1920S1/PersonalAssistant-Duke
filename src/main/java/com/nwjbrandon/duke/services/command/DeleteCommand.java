package com.nwjbrandon.duke.services.command;

import com.nwjbrandon.duke.exceptions.DukeEmptyCommandException;
import com.nwjbrandon.duke.exceptions.DukeOutOfBoundException;
import com.nwjbrandon.duke.exceptions.DukeTypeConversionException;
import com.nwjbrandon.duke.services.task.TaskList;
import com.nwjbrandon.duke.services.validation.InputValidation;

public class DeleteCommand extends Command {

    /**
     * Index of task in string.
     */
    private String taskIndexString;

    /**
     * Input by user.
     */
    private String userInput;

    /**
     * Command.
     */
    private String command;


    /**
     * Number of tasks.
     */
    private int size;

    /**
     * Create delete command.
     * @param userInput input by user.
     * @param command type of command.
     * @param size number of tasks.
     */
    public DeleteCommand(String userInput, String command, int size) {
        this.userInput = userInput;
        this.command = command;
        this.size = size;
    }

    /**
     * Get task index.
     * @return task index.
     */
    private int getTaskIndex() throws DukeTypeConversionException, DukeOutOfBoundException {
        Integer taskIndex = InputValidation.checkStringToIntegerConversion(taskIndexString);
        return InputValidation.checkIndex(taskIndex - 1, size);

    }

    /**
     * Validate user input.
     * @param userInput input by user.
     * @param command type of command.
     * @return instruction in input.
     */
    private String parseCommand(String userInput, String command) throws DukeEmptyCommandException {
        return InputValidation.checkCommandInput(userInput, command);
    }

    /**
     * Execute command.
     * @param taskList list of tasks.
     */
    @Override
    public void execute(TaskList taskList) {
        try {
            this.taskIndexString = parseCommand(userInput, command);
            int taskIndex = this.getTaskIndex();
            taskList.removeTask(taskIndex);
        } catch (DukeEmptyCommandException | DukeTypeConversionException | DukeOutOfBoundException e) {
            e.showError();
        }
    }
}

package com.nwjbrandon.duke.services.command;

import com.nwjbrandon.duke.exceptions.DukeEmptyCommandException;
import com.nwjbrandon.duke.exceptions.DukeWrongCommandFormatException;
import com.nwjbrandon.duke.services.task.Task;
import com.nwjbrandon.duke.services.task.TaskList;
import com.nwjbrandon.duke.services.task.Todos;
import com.nwjbrandon.duke.services.validation.Parser;

public class TodosCommand extends Command {

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
     * Number of tasks.
     */
    private int size;

    /**
     * Create add command.
     * @param userInput input by user.
     * @param command type of command.
     * @param size number of tasks.
     */
    public TodosCommand(String userInput, String command, int size) {
        this.userInput = userInput;
        this.command = command;
        this.size = size;
    }

    /**
     * Create specific task.
     * @return specific task.
     */
    private Task setTask() throws DukeWrongCommandFormatException {
        return new Todos(taskDescription, size);
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
            taskList.addTask(this.setTask());
        } catch (DukeWrongCommandFormatException | DukeEmptyCommandException e) {
            e.showError();
        }
    }
}

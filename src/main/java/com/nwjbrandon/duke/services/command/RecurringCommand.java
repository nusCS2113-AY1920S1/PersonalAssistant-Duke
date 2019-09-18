package com.nwjbrandon.duke.services.command;

import com.nwjbrandon.duke.exceptions.DukeEmptyCommandException;
import com.nwjbrandon.duke.exceptions.DukeTaskCollisionException;
import com.nwjbrandon.duke.exceptions.DukeWrongCommandFormatException;
import com.nwjbrandon.duke.services.task.TaskList;
import com.nwjbrandon.duke.services.ui.Terminal;
import com.nwjbrandon.duke.services.validation.InputValidation;

public class RecurringCommand extends Command {

    /**
     * The entire string input by the user.
     * e.g. "recurring 1 daily".
     */
    private String userInput;

    /**
     * In this case, it is set to "recurring".
     */
    private String commandName;

    /**
     * The size of taskList, at the time this command was made.
     */
    private int taskListSize;

    /**
     * There you go Travis.
     * @param userInput the exact line of text input by the user.
     * @param commandName The string name of the command.
     * @param size current size of taskList.
     */
    public RecurringCommand(String userInput, String commandName, int size) {
        this.userInput = userInput;
        this.commandName = commandName;
        taskListSize = size;
    }

    @Override
    public void execute(TaskList taskList) {

        String[] tokens = this.userInput.split(" ");
        String recurFrequency = "";

        // Check if there are exactly 3 tokens
        if (tokens.length != 3) {
            new DukeWrongCommandFormatException("Recur command1").showError();
            return;
        }
        //Check if token[1] is "daily" or "weekly" or invalid
        switch (tokens[1].strip()) {
        case "daily":
            recurFrequency = "daily";
            break;
        case "weekly":
            recurFrequency = "weekly";
            break;
        default:
            new DukeWrongCommandFormatException("Recur Command2").showError();
            return;
        }
        int index;

        // find value of index to modify
        try {
            index = Integer.parseInt(tokens[2]) - 1;
            if (index < 0 || index >= this.taskListSize) {
                throw new Exception();
            }
        } catch (Exception e) {
            new DukeWrongCommandFormatException("Recur Command3").showError();
            return;
        }
        // check if that task currently has a valid date
        if (taskList.getTask(index).getDate() == null) {
            Terminal.showMessage("Error, task currently does not have a date");
            return;
        }

        taskList.getTask(index).setRecurFrequency(recurFrequency);
        Terminal.showMessage("Task has been set as recurring " + recurFrequency);
    }
}

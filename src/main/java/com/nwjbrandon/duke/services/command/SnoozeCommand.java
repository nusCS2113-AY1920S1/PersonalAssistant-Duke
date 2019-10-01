package com.nwjbrandon.duke.services.command;

import com.nwjbrandon.duke.exceptions.DukeEmptyCommandException;
import com.nwjbrandon.duke.exceptions.DukeNullDateException;
import com.nwjbrandon.duke.exceptions.DukeOutOfBoundException;
import com.nwjbrandon.duke.exceptions.DukeTypeConversionException;
import com.nwjbrandon.duke.services.task.Task;
import com.nwjbrandon.duke.services.task.TaskList;
import com.nwjbrandon.duke.services.validation.InputValidation;

import java.util.Calendar;
import java.util.Date;

public class SnoozeCommand extends Command {

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
     * Create snooze command.
     * @param userInput input by user.
     * @param command type of command.
     * @param size number of tasks.
     */
    public SnoozeCommand(String userInput, String command, int size) {
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
     * Extends the deadline/time of specified task by 1 hour.
     * @param task the specified task to be snoozed.
     * @return the snoozed time.
     */
    private Date snoozeTime(Task task) throws DukeNullDateException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(task.getDate());
        calendar.add(Calendar.HOUR, 1);
        return calendar.getTime();
    }

    /**
     * Executes command.
     * @param taskList list of tasks.
     */
    @Override
    public void execute(TaskList taskList) {
        try {
            this.taskIndexString = parseCommand(userInput, command);
            int taskIndex = this.getTaskIndex();
            Date snoozedDate = snoozeTime(taskList.getTask(taskIndex));
            taskList.snoozeTask(taskIndex, snoozedDate);
        } catch (DukeEmptyCommandException | DukeTypeConversionException | DukeOutOfBoundException | DukeNullDateException e) {
            e.showError();
        }
    }
}

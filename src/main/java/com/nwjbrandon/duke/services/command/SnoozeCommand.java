package com.nwjbrandon.duke.services.command;

import com.nwjbrandon.duke.constants.TaskCommands;
import com.nwjbrandon.duke.exceptions.DukeEmptyCommandException;
import com.nwjbrandon.duke.exceptions.DukeOutOfBoundException;
import com.nwjbrandon.duke.exceptions.DukeTypeConversionException;
import com.nwjbrandon.duke.services.task.Task;
import com.nwjbrandon.duke.services.task.TaskList;
import com.nwjbrandon.duke.services.validation.InputValidation;
import com.nwjbrandon.duke.exceptions.DukeNullDateException;

import java.text.ParseException;
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
    public Date snoozeTime(Task task) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(task.getDate()); // your date (java.util.Date)
        cal.add(Calendar.HOUR, 1); // You can -/+ x months here to go back in history or move forward.
        return cal.getTime(); // New date

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
            if (taskList.getTask(taskIndex).getDate() == null) {
                System.out.println("☹ OOPS!!! I'm sorry, but there is no date to be snoozed");
            } else {
                Date snoozedDate = snoozeTime(taskList.getTask(taskIndex));
                taskList.modifyDate(taskIndex, snoozedDate);
            }
        } catch (DukeEmptyCommandException | DukeTypeConversionException | DukeOutOfBoundException e) {
            e.showError();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}

package seedu.duke.parser;

import seedu.duke.data.Schedule;
import java.util.Arrays;

import seedu.duke.logic.CommandLineException;
import seedu.duke.logic.CommandLogic;
import seedu.duke.task.Reminders;
import seedu.duke.task.TaskList;
import seedu.duke.ui.Ui;
import seedu.duke.command.Command;
import seedu.duke.command.DeleteCommand;
import seedu.duke.command.ListCommand;
import seedu.duke.command.DoneCommand;
import seedu.duke.command.FindCommand;
import seedu.duke.command.InvalidCommand;
import seedu.duke.command.ScheduleCommand;
import seedu.duke.command.SnoozeCommand;
import seedu.duke.command.RemindCommand;
import seedu.duke.command.AddCommand;

import java.text.ParseException;
import java.util.Date;

/**
 * Takes raw user input as string, makes sense out of the input using
 * regex and then performs operations based on the input.
 */
public class CommandParser extends Parser {
    /**
     * Takes raw input and splits it into task type (eg. todo) and task
     * description (eg. finish work). In cases like task type: list, bye,
     * the output array only contains task type.
     *
     * @param rawInput users single line string input
     * @return an array split into task type and task description
     */
    public String[] split(String rawInput) {
        String[] userInput = rawInput.split(" ", 2);
        return userInput;
    }
    /**
     * Default constructor.
     */

    /**
     * This method takes the raw user input and attempts to decipher
     * the user's intentions (whether the user wants to find a task, add
     * a task, etc.), thereafter returning the corresponding command.
     *
     * @param rawInput user's single line string input
     * @return an instruction, of type Command, to be executed.
     */
    public Command parse(String rawInput) throws CommandLineException {
        String[] userInput = this.split(rawInput);

        String[] taskCommands = {"todo", "deadline", "event", "range", "doafter", "fixedtask", "recur"};
        Arrays.sort(taskCommands);
        if (userInput[0].equals("find")) {
            return new FindCommand(userInput);
        } else if (userInput[0].equals("delete")) {
            return new DeleteCommand(userInput);
        } else if (userInput[0].equals("list")) {
            return new ListCommand();
        } else if (userInput[0].equals("remind")) {
            return new RemindCommand();
        } else if (userInput[0].equals("done")) {
            return new DoneCommand(userInput);
        } else if (userInput[0].equals("show")) {
            return new ScheduleCommand(userInput);
        } else if (userInput[0].equals("snooze")) {
            return new SnoozeCommand(userInput);
        } else if (Arrays.binarySearch(taskCommands, userInput[0]) >= 0) {
            return new AddCommand(userInput);
        } else {
            return new InvalidCommand();
        }
    }
}

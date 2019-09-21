package seedu.duke.command;

import seedu.duke.data.Schedule;
import seedu.duke.task.Reminders;
import seedu.duke.task.TaskList;
import seedu.duke.ui.Ui;

import java.text.ParseException;
import java.util.Date;

/**
 * Takes raw user input as string, makes sense out of the input using
 * regex and then performs operations based on the input.
 */
public class Parser {

    private Schedule schedule = new Schedule();

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
    public Command parse(String rawInput) {
        Ui ui = new Ui();
        String[] userInput = this.split(rawInput);
        /* return output; */

        // If user inputs list
        if (userInput[0].equals("find")) {
            return new commandFindTask(userInput[1]);
        } else if (userInput[0].equals("delete")) {
            return new commandDeleteTask(userInput[1]);
        } else if (userInput[0].equals("list")) {
            return new commandDisplayTaskList();
        } else if (userInput[0].equals("remind")) {
            return new commandDisplayReminders();
        } else if (userInput[0].equals("done")) {
            return new commandMarkTaskAsDone(userInput[1]);
        } else if (userInput[0].equals("show")) {
            return new commandDisplaySchedule(userInput[1]);
        } else if (userInput[0].equals("snooze")) {
            return new commandSnoozeTask(userInput[1]);
        } else {
            // add task to list
            if (userInput[0].equals("todo") || userInput[0].equals("deadline") || userInput[0].equals("event") ||
                userInput[0].equals("range") || userInput[0].equals("doafter")) {
                return new commandAddTask(userInput[0], userInput[1]);
            } else if (!userInput[0].equals("bye")) {
                return new commandInputNotRecognised();
            }
        }
    }
}

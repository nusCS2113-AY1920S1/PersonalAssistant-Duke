package seedu.duke.command;

import seedu.duke.data.Schedule;
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
     * Takes raw user input and the current list of tasks and based on
     * the user input performs operations like find, delete, done, list,
     * add, show and bye.
     * Outputs a task list back after performing the operation.
     *
     * @param rawInput users single line string input
     * @param list existing list of tasks to perform operations upon
     * @return list after an operation has been performed
     * @see TaskList
     */
    public TaskList parse(String rawInput, TaskList list) {
        Ui ui = new Ui();
        String[] userInput = this.split(rawInput);
        /* return output; */

        // If user inputs list
        if (userInput[0].equals("find")) {
            if (userInput.length == 1) {
                ui.empty_description_error();
            } else {
                list.findTask(userInput[1]);
            }
        } else if (userInput[0].equals("delete")) {
            if (userInput.length == 1) {
                ui.empty_description_error();
            } else {
                int taskId = Integer.parseInt(userInput[1]) - 1;
                list.removeTask(taskId);
            }
        } else if (userInput[0].equals("list")) {
            list.displayList();
        } else if (userInput[0].equals("done")) {
            if (userInput.length == 1) {
                ui.empty_description_error();
            } else {
                int taskId = Integer.parseInt(userInput[1]) - 1;
                list.doTask(taskId);
            }
        } else if (userInput[0].equals("show")) {
            if (userInput.length != 2) {
                ui.showFormatError();
            } else {
                Date date = schedule.convertStringToDate(userInput[1]);
                if (date != (null)) {
                    schedule.printSchedule(date);
                }
            }
        } else if (userInput[0].equals("snooze")) {
            if (userInput.length == 1) {
                ui.empty_description_error();
            } else {
                int taskId = Integer.parseInt(userInput[1]) - 1;
                list.snoozeTask(taskId);
            }
        } else {
            // add task to list
            if (userInput[0].equals("todo") || userInput[0].equals("deadline")
                || userInput[0].equals("event") || userInput[0].equals("range") || userInput[0].equals("doafter")) {
                if (userInput.length == 1) {
                    ui.empty_description_error();
                } else {
                    list.add(userInput[0], userInput[1]);
                }
            } else if (!userInput[0].equals("bye")) {
                ui.correct_command_error();
            }
        }
        return list;
    }
}

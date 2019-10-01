package dolla.command;

import dolla.Ui;
import dolla.task.TaskList;

/**
 * Command is an abstract class which all types of command will inherit from.
 */
public abstract class Command {
    /**
     * This method is called to read/write the specified duke.task.TaskList after every user input.
     * @param tasks duke.task.TaskList containing all the tasks stored.
     * @throws Exception handle exception
     */
    public abstract void execute(TaskList tasks) throws Exception;

    /**
     * Returns an integer variable from the specified string.
     * <p>
     *     Returns 0 if the specified string is not of a number.
     * </p>
     * <p>
     *     Mainly used for using the specified string for calculations in the command.
     *     IE. Accessing arrays.
     * </p>
     * @param str String (of number) to be converted into integer type.
     * @return Integer type of the specified string.
     */
    public int stringToInt(String str) {
        int newInt = 0;
        try {
            newInt = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            Ui.printInvalidNumberError(str);
        }
        return newInt;
    }

    public void extractDesc(String inputLine) {

    }
}

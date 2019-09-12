package duke.command;

import duke.Ui;
import duke.task.TaskList;

import java.util.ArrayList;

/**
 * Command is an abstract class which all types of command will inherit from.
 */
public abstract class Command {
    /**
     * This method is called to read/write the specified duke.task.TaskList after every user input.
     * @param task duke.task.TaskList containing all the tasks stored.
     * @throws Exception
     */
    public abstract void execute(TaskList task) throws Exception;

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
        }
        catch (NumberFormatException e) {
            ArrayList<String> msg = new ArrayList<String>();
            msg.add(str + " is not a number. Please use a number instead!");
            Ui.printMsg(msg);
        }

        return newInt;
    }


    public void extractDesc(String inputLine) {

    }
}

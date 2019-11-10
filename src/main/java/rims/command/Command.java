package rims.command;

import java.text.ParseException;
import java.io.IOException;

import rims.core.ResourceList;
import rims.core.Storage;
import rims.core.Ui;
import rims.exception.RimsException;

//@@author rabhijit
/**
 * The parent class of all possible Commands understood by RIMS. Sets the exit code
 * to false by default and lists methods common to all the commands.
 */
public abstract class Command {
    protected Boolean willExit;
    protected boolean canModifyData;
    protected String commandUserInput;

    /**
     * Sets the exit code, which is checked after the handling and processing of every command
     * and before the input of the next command, to false. When it is true, the RIMS program halts.
     */
    public Command() {
        willExit = false;
        canModifyData = false;
    }

    /**
     * Returns the exit code of the command.
     * @return the exit code
     */
    public Boolean getExitCode() {
        return willExit;
    }

    /**
     * Sets the exit code of the command to true. When this happens, the RIMS
     * program will halt.
     */
    public void setExitCode() {
        willExit = true;
    }

    /**
     * Checks whether the Command type could change ResourceList
     * or ReservationLists in Resources.
     * @return true if type of Command may change data.
     */
    public boolean canModifyData() {
        return canModifyData;
    }

    /**
     * Returns the command with its relevant arguments inputted by
     * the user as a string.
     * @return The string with the command details and arguments
     */
    public String getCommandUserInput() {
        return commandUserInput;
    }


    /**
     * In the Command child classes, this method will carry out the necessary operations
     * to execute its command.
     * @param ui An instance of the user interface.
     * @param storage An instance of the Storage class.
     * @param resources The ResourceList, containing all the created Resources thus far.
     * @throws RimsException if any date is un-parsable, if there is an error in saving Resources to disk or
     * if there is a non-formatting-related issue in the input
     */
    public abstract void execute(Ui ui, Storage storage, ResourceList resources)
        throws RimsException;

}
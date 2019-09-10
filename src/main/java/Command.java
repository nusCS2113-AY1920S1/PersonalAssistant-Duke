import java.util.*;
import java.io.*;
import java.text.*;

/**
 * The parent of all possible commands understood by Duke. Sets the exit code
 * to false by default and lists methods common to all the commands.
 */

public abstract class Command {
    protected Boolean exitCode;

    /**
     * Sets the exit code, which is checked after the handling and processing of every command
     * and before the input of the next command, to false. When it is true, the Duke program halts.
     */
    public Command() {
        exitCode = false;
    }

    /**
     * Returns the exit code of the command.
     * @return the exit code
     */
    public Boolean getExitCode() {
        return exitCode;
    }

    /**
     * Sets the exit code of the command to true. When this happens, the Duke
     * program will halt.
     */
    public void setExitCode() {
        exitCode = true;
    }

    /**
     * In the Command child classes, this method will carry out the necessary operations
     * to execute its command.
     * @param ui An instance of the user interface.
     * @param storage An instance of the Storage class.
     * @param tasks The TaskList, containing all the created Tasks thus far.
     * @throws ParseException if any date is un-parsable
     * @throws IOException if there is an error in saving Tasks to disk
     * @throws DukeException if there is a non-formatting-related issue in the input
     */
    public void execute(Ui ui, Storage storage, TaskList tasks) throws ParseException, IOException, DukeException {
        ;
    }
}
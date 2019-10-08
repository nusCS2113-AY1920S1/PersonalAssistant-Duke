package duke.core;

import duke.command.*;
import duke.task.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Represents a Parser that parses user input into a specific
 * type of Command.
 */
public class CommandManager {

    /**
     * Parses a Task from a string array.
     *
     * @param userInput The string array to be parsed.
     * @return The Command received from user.
     */
    public static Command manageCommand(String userInput) throws DukeException {
        switch (userInput) { //change this depending on how string is parsed
            case "list":
                //do thing for 'list'
            case "done":
                //do thing for 'done'
            case "delete":
                //do thing for 'delete
            case "find":
                //do thing for 'find'
            case "reschedule":
                //do thing for 'reschedule'
            case "view":
                //do thing for 'view'
            case "help":
                //do thing for 'help'
            case "bye":
                return new ExitCommand();
            default:
                throw new DukeException("Unrecognized user input!");
        }
    }

}

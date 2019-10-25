package Parser;

import Commands.Command;
import Commands.DeleteCommand;
import DukeExceptions.DukeException;
import Interface.*;
import Tasks.Deadline;
import Tasks.Event;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Parse Deletecommand for event and deadline and return formatted command to Parser
 */
public class DeleteParse extends Parse {
    private static String[] split;
    private static String fullCommand;
    private static final Logger LOGGER = Logger.getLogger(Parser.class.getName());
    private static DateTimeParser DTP;

    public DeleteParse(String fullCommand) {
        this.fullCommand = fullCommand;
    }
    /**
     * @return Command which represents the parsed Deletecommand
     * @throws Exception Returned if command does not adhere to format
     */
    @Override
    public Command execute() throws Exception {
        if (fullCommand.trim().substring(0, 8).equals("delete/e")) {
            try { //add/e module_code description /at date from time to time
                String activity = fullCommand.trim().substring(8);
                split = activity.split("/at"); //split[0] is " module_code description", split[1] is "date from time to time"
                if (split[0].trim().isEmpty()) {
                    throw new DukeException("\u2639" + " OOPS!!! The description of a event cannot be empty.");
                }
                String[] out = DTP.EventParse(split[1]);
                return new DeleteCommand("event", new Event(split[0].trim(), out[0],out[1],out[2]));
            } catch (ParseException | ArrayIndexOutOfBoundsException e) {
                LOGGER.log(Level.INFO, e.toString(), e);
                throw new DukeException("OOPS!!! Please enter in the format as follows:\n" +
                        "delete/e mod_code name_of_event /at dd/MM/yyyy /from HHmm /to HHmm\n" +
                        "or delete/e mod_code name_of_event /at week x day /from HHmm /to HHmm\n");
            }
        } else if (fullCommand.trim().substring(0, 8).equals("delete/d")) {
            try {
                String activity = fullCommand.trim().substring(8);
                split = activity.split("/by");
                if (split[0].trim().isEmpty()) {
                    throw new DukeException("\u2639" + " OOPS!!! The description of a deadline cannot be empty.");
                }
                String[] out = DTP.DeadlineParse(split[1]);
                return new DeleteCommand("deadline", new Deadline(split[0].trim(), out[1],out[2]));

            } catch (ParseException | ArrayIndexOutOfBoundsException e) {
                LOGGER.log(Level.INFO, e.toString(), e);
                throw new DukeException("OOPS!!! Please enter in the format as follows:\n" +
                        "delete/d mod_code name_of_event /by dd/MM/yyyy HHmm\n" +
                        "or delete/d mod_code name_of_event /by week x day HHmm\n");
            }
        } else {
            throw new DukeException("\u2639" + " OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }
}
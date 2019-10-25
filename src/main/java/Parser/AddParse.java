package Parser;

import Commands.AddCommand;
import Commands.Command;
import DukeExceptions.DukeException;
import Interface.*;
import Tasks.Deadline;
import Tasks.Event;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Parse Addcommand for event and deadline and return formatted command to Parser
 */
public class AddParse extends Parse {
    private static String[] split;
    private static String fullCommand;
    private static final Logger LOGGER = Logger.getLogger(Parser.class.getName());

    public AddParse(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    /**
     * @return Command which represents the parsed Addcommand
     * @throws Exception Returned if command does not adhere to format
     */
    @Override
    public Command execute() throws Exception {
        if (fullCommand.trim().substring(0, 5).equals("add/d")) {//deadline
            try {
                String activity = fullCommand.trim().substring(5);
                split = activity.split("/by");
                if (split[0].trim().isEmpty()) {
                    throw new DukeException("\u2639" + " OOPS!!! The description of a deadline cannot be empty.");
                }
                String[] out = DateTimeParser.DeadlineParse(split[1]);
                return new AddCommand(new Deadline(split[0].trim(), out[0], out[1]));
            } catch (ParseException | ArrayIndexOutOfBoundsException e) {
                LOGGER.log(Level.INFO, e.toString(), e);
                throw new DukeException(" OOPS!!! Please enter deadline as follows:\n" +
                        "add/d mod_code name_of_event /by dd/MM/yyyy HHmm\n" +
                        "or add/d mod_code name_of_event /by week x day HHmm\n");
            }
        } else if (fullCommand.trim().substring(0, 5).equals("add/e")) {
            try { //add/e module_code description /at date from time to time
                String activity = fullCommand.trim().substring(5);
                split = activity.split("/at"); //split[0] is " module_code description", split[1] is "date /from time /to time"
                if (split[0].trim().isEmpty()) {
                    throw new DukeException("\u2639" + " OOPS!!! The description of a event cannot be empty.");
                }
                String[] out = DateTimeParser.EventParse(split[1]);
                return new AddCommand(new Event(split[0].trim(),out[0],out[1],out[2]));
            } catch (ParseException | ArrayIndexOutOfBoundsException e) {
                LOGGER.log(Level.INFO, e.toString(), e);
                throw new DukeException("OOPS!!! Please enter event as follows:\n" +
                        "add/e modCode name_of_event /at dd/MM/yyyy from HHmm to HHmm\n" +
                        "For example: add/e CS1231 project meeting /at 1/1/2020 /from 1500 /to 1700");
            }
        }else {
            throw new DukeException("\u2639" + " OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }

}

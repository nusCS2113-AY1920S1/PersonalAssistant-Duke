package Parser;

import Commands.AddCommand;
import Commands.Command;
import Commons.DukeLogger;
import DukeExceptions.DukeInvalidCommandException;

import DukeExceptions.DukeInvalidDateTimeException;
import DukeExceptions.DukeInvalidFormatException;
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
    private static String[] split1;
    private static String fullCommand;
    private final Logger LOGGER = DukeLogger.getLogger(AddParse.class);

    /**
     * Creates AddParse object.
     * @param fullCommand The entire command that calls for AddParse.
     */
    public AddParse(String fullCommand)  {
        this.fullCommand = fullCommand;

    }

    /**
     * @return Command which represents the parsed Addcommand
     * @throws Exception Returned if command does not adhere to format
     */
    @Override
    public Command parse() throws DukeInvalidFormatException, DukeInvalidCommandException {
        if (fullCommand.trim().substring(0, 5).equals("add/d")) {//deadline
            try {
                String activity = fullCommand.trim().substring(5);
                split = activity.split("/by");
                split1 = split[0].trim().split(" ");
                if (!super.isModCode(split1[0])) {
                    throw new DukeInvalidFormatException("\u2639" + " OOPS!!! The ModCode is invalid");
                }
                //condition does not work use the condition below
                //TODO: review use of condition
                if (split[0].trim().isEmpty()) {
                    throw new DukeInvalidFormatException("\u2639" + " OOPS!!! The description of a deadline cannot be empty.");
                }
                if(!isValidDescription(split1)) throw new DukeInvalidFormatException("\u2639" + " OOPS!!! The description of a deadline cannot be empty.");
                if(!isValidTime(split[1])) throw new DukeInvalidFormatException("\u2639" + " OOPS!!! The time of a deadline can only contain digits and the time has to be 4 digits.\n" +
                        "Please enter the time in a 24-hour time format");
                String[] out = DateTimeParser.DeadlineParse(split[1]);
                return new AddCommand(new Deadline(split[0].trim(), out[0], out[1]));
            } catch (ParseException | ArrayIndexOutOfBoundsException e) {
                LOGGER.info("Invalid format for adding deadline" + e.getMessage());
                throw new DukeInvalidFormatException(" OOPS!!! Please enter deadline as follows:\n" +
                        "add/d mod_code name_of_event /by dd/MM/yyyy HHmm\n" +
                        "or add/d mod_code name_of_event /by week x day HHmm\n");
            }
        } else if (fullCommand.trim().substring(0, 5).equals("add/e")) {
            try { //add/e module_code description /at date from time to time
                String activity = fullCommand.trim().substring(5);
                split = activity.split("/at"); //split[0] is " module_code description", split[1] is "date /from time /to time"
                split1 = split[0].trim().split(" ");
                if (!super.isModCode(split1[0])) {
                    throw new DukeInvalidFormatException("\u2639" + " OOPS!!! The ModCode is invalid");
                }
                //condition does not work use the condition below
                //TODO: review use of condition
                if (split[0].trim().isEmpty()) {
                    throw new DukeInvalidFormatException("\u2639" + " OOPS!!! The description of a event cannot be empty.");
                }
                if(!isValidDescription(split1)) throw new DukeInvalidFormatException("\u2639" + " OOPS!!! The description of a event cannot be empty.");
                if(!isValidTimePeriod(split[1])) throw new DukeInvalidFormatException("\u2639" + " OOPS!!! The time of a event can only contain digits and the time has to be 4 digits.\n" +
                        "Please enter the time in a 24-hour time format");
                String[] out = DateTimeParser.EventParse(split[1]);
                return new AddCommand(new Event(split[0].trim(),out[0],out[1],out[2]));
            } catch (ParseException | ArrayIndexOutOfBoundsException e ) {
                LOGGER.info("Invalid format for adding event" + e.getMessage());
                throw new DukeInvalidFormatException("OOPS!!! Please enter event as follows:\n" +
                        "add/e modCode name_of_event /at dd/MM/yyyy from HHmm to HHmm\n" +
                        "For example: add/e CS1231 project meeting /at 1/1/2020 /from 1500 /to 1700");
            }
        }else {
            throw new DukeInvalidCommandException("\u2639" + " OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }

}

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
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Parse Addcommand for event and deadline and return formatted command to Parser
 */
public class AddParse extends Parse {
    private static String[] modCodeAndDescription;
    private static String[] modCodeAndDescriptionSplit;
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
    public Command parse() throws DukeInvalidFormatException, DukeInvalidCommandException, DukeInvalidDateTimeException {
        if (fullCommand.trim().substring(0, 5).equals("add/d")) {
            try {
                String activity = fullCommand.trim().substring(5);
                modCodeAndDescription = activity.split("/by");
                modCodeAndDescriptionSplit = modCodeAndDescription[0].trim().split(" ");
                if (!super.isValidModCodeAndDescription(modCodeAndDescription[0].trim())) throw new DukeInvalidFormatException("\u2639" + " OOPS!!! The ModCode + description of a deadline cannot be empty.");
                if (!super.isModCode(modCodeAndDescriptionSplit[0])) {
                    throw new DukeInvalidFormatException("\u2639" + " OOPS!!! The ModCode is invalid");
                }
                if(!super.isValidDescription(modCodeAndDescriptionSplit)) throw new DukeInvalidFormatException("\u2639" + " OOPS!!! The description of a deadline cannot be empty.");
                if(!super.isValidTime(modCodeAndDescription[1])) throw new DukeInvalidFormatException("\u2639" + " OOPS!!! The time of a deadline can only contain digits and the time has to be 4 digits \nand in a 24-hour time format");
                String[] out = DateTimeParser.DeadlineParse(modCodeAndDescription[1].trim());
                return new AddCommand(new Deadline(modCodeAndDescription[0].trim(), out[0], out[1]));
            } catch (ParseException | ArrayIndexOutOfBoundsException e) {
                LOGGER.severe("Invalid format for adding deadline");
                throw new DukeInvalidFormatException(" OOPS!!! Please enter deadline as follows:\n" +
                        "add/d mod_code name_of_deadline /by dd/MM/yyyy HHmm\n" +
                        "or add/d mod_code name_of_deadline /by week x day HHmm\n");
            }
        } else if (fullCommand.trim().substring(0, 5).equals("add/e")) {
            try {
                String activity = fullCommand.trim().substring(5);
                modCodeAndDescription = activity.split("/at");
                modCodeAndDescriptionSplit = modCodeAndDescription[0].trim().split(" ");
                if (!super.isValidModCodeAndDescription(modCodeAndDescription[0].trim())) throw new DukeInvalidFormatException("\u2639" + " OOPS!!! The ModCode + description of an event cannot be empty.");
                if (!super.isModCode(modCodeAndDescriptionSplit[0])) {
                    throw new DukeInvalidFormatException("\u2639" + " OOPS!!! The ModCode is invalid");
                }
                if(!super.isValidDescription(modCodeAndDescriptionSplit)) throw new DukeInvalidFormatException("\u2639" + " OOPS!!! The description of an event cannot be empty.");
                if(!super.isValidTimePeriod(modCodeAndDescription[1])) throw new DukeInvalidFormatException("\u2639" + " OOPS!!! The time of an event can only contain digits and the time has to be 4 digits.\n" +
                        "and the start time has to be before the end." +
                        "Please enter the time in a 24-hour time format");
                String[] out = DateTimeParser.EventParse(modCodeAndDescription[1].trim());
                return new AddCommand(new Event(modCodeAndDescription[0].trim(),out[0],out[1],out[2]));
            } catch (ParseException | ArrayIndexOutOfBoundsException e) {
                LOGGER.severe("Invalid format for adding event");
                throw new DukeInvalidFormatException("OOPS!!! Please enter event as follows:\n" +
                        "add/e modCode name_of_event /at dd/MM/yyyy /from HHmm /to HHmm\n" +
                        "or add/e modCode name_of_event /at week x day /from HHmm /to HHmm\n " +
                        "For example: add/e CS1231 project meeting /at 1/1/2020 /from 1500 /to 1700");
            }
        }else {
            throw new DukeInvalidCommandException("\u2639" + " OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }

}

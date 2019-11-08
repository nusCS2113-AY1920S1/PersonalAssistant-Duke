package Parser;

import Commands.Command;
import Commands.DeleteCommand;
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
 * This class parses the full command that calls for DeleteParse
 */
public class DeleteParse extends Parse {
    private static String[] modCodeAndDescription;
    private static String[] modCodeAndDescriptionSplit;
    private static String fullCommand;
    private final Logger LOGGER = DukeLogger.getLogger(DeleteParse.class);

    /**
     * Creates a DeleteParse object.
     * @param fullCommand The full command that calls for DeleteParse
     */
    public DeleteParse(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    /**
     * @return Command which represents the parsed DeleteCommand
     * @throws Exception Returned if command does not adhere to format
     */
    @Override
    public Command parse() throws DukeInvalidFormatException, DukeInvalidCommandException, DukeInvalidDateTimeException {
        if (fullCommand.trim().substring(0, 8).equals("delete/e")) {
            try {
                String activity = fullCommand.trim().substring(8);
                modCodeAndDescription = activity.split("/at");
                modCodeAndDescriptionSplit = modCodeAndDescription[0].trim().split(" ");
                if (!super.isValidModCodeAndDescription(modCodeAndDescription[0].trim())) throw new DukeInvalidFormatException("\u2639" + " OOPS!!! The ModCode + description of an event cannot be empty.");
                if (!super.isModCode(modCodeAndDescriptionSplit[0])) {
                    throw new DukeInvalidFormatException("\u2639" + " OOPS!!! The ModCode is invalid");
                }
                if(!super.isValidDescription(modCodeAndDescriptionSplit)) throw new DukeInvalidFormatException("\u2639" + " OOPS!!! The description of an event cannot be empty.");
                if(!super.isValidTimePeriod(modCodeAndDescription[1])) throw new DukeInvalidFormatException("\u2639" + " OOPS!!! The time of an event can only contain digits and the time has to be 4 digits.\n" +
                        "Please enter the time in a 24-hour time format");
                String[] out = DateTimeParser.EventParse(modCodeAndDescription[1]);
                return new DeleteCommand("event", new Event(modCodeAndDescription[0].trim(), out[0],out[1],out[2]));
            } catch (ParseException | ArrayIndexOutOfBoundsException e) {
                LOGGER.severe("Invalid format for deleting event");
                throw new DukeInvalidFormatException("OOPS!!! Please enter in the format as follows:\n" +
                        "delete/e mod_code name_of_event /at dd/MM/yyyy /from HHmm /to HHmm\n" +
                        "or delete/e mod_code name_of_event /at week x day /from HHmm /to HHmm\n");
            }
        } else if (fullCommand.trim().substring(0, 8).equals("delete/d")) {
            try {
                String activity = fullCommand.trim().substring(8);
                modCodeAndDescription = activity.split("/by");
                modCodeAndDescriptionSplit = modCodeAndDescription[0].trim().split(" ");
                if (!super.isValidModCodeAndDescription(modCodeAndDescription[0].trim())) throw new DukeInvalidFormatException("\u2639" + " OOPS!!! The ModCode + description of a deadline cannot be empty.");
                if (!super.isModCode(modCodeAndDescriptionSplit[0])) {
                    throw new DukeInvalidFormatException("\u2639" + " OOPS!!! The ModCode is invalid");
                }
                if(!super.isValidDescription(modCodeAndDescriptionSplit)) throw new DukeInvalidFormatException("\u2639" + " OOPS!!! The description of a deadline cannot be empty.");
                if(!super.isValidTime(modCodeAndDescription[1])) throw new DukeInvalidFormatException("\u2639" + " OOPS!!! The time of a deadline can only contain digits and the time has to be 4 digits.\n" +
                        "Please enter the time in a 24-hour time format");
                String[] out = DateTimeParser.DeadlineParse(modCodeAndDescription[1]);
                return new DeleteCommand("deadline", new Deadline(modCodeAndDescription[0].trim(), out[0],out[1]));
            } catch (ParseException | ArrayIndexOutOfBoundsException e) {
                LOGGER.severe("Invalid format for deleting deadline");
                throw new DukeInvalidFormatException("OOPS!!! Please enter in the format as follows:\n" +
                        "delete/d mod_code name_of_deadline /by dd/MM/yyyy HHmm\n" +
                        "or delete/d mod_code name_of_deadline /by week x day HHmm\n");
            }
        } else {
            throw new DukeInvalidCommandException("\u2639" + " OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }
}
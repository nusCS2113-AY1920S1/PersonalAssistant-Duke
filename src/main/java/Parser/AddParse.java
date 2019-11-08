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
import Commons.DukeConstants;
import java.util.logging.Logger;

/**
 * Parse Addcommand for event and deadline and return formatted command to Parser
 */
public class AddParse extends Parse {

    private static String[] modCodeAndDescriptionAndDate;
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
        if (fullCommand.trim().startsWith(DukeConstants.ADD_DEADLINE_HEADER)) {
            try {
                String activity = fullCommand.trim().replaceFirst(DukeConstants.ADD_DEADLINE_HEADER, "");
                modCodeAndDescriptionAndDate = activity.split(DukeConstants.DEADLINE_DATE_DESCRIPTION_SPLIT_KEYWORD);
                modCodeAndDescriptionSplit = modCodeAndDescriptionAndDate[0].trim().split(DukeConstants.STRING_SPACE_SPLIT_KEYWORD);
                String fullDescriptionAndModCode = modCodeAndDescriptionAndDate[0].trim();
                if (!super.isValidModCodeAndDescription(fullDescriptionAndModCode)) {
                    throw new DukeInvalidFormatException("\u2639" + " OOPS!!! The ModCode + description of a deadline cannot be empty.");
                }
                String modCode = modCodeAndDescriptionSplit[0];
                if (!super.isModCode(modCode)) {
                    throw new DukeInvalidFormatException("\u2639" + " OOPS!!! The ModCode is invalid");
                }
                if(!super.isValidDescription(modCodeAndDescriptionSplit)) {
                    throw new DukeInvalidFormatException("\u2639" + " OOPS!!! The description of a deadline cannot be empty.");
                }
                String timePeriod = modCodeAndDescriptionAndDate[1];
                if(!super.isValidTime(timePeriod)) {
                    throw new DukeInvalidFormatException("\u2639" + " OOPS!!! The time of a deadline can only contain digits and the time has to be 4 digits.\n" +
                            "Please enter the time in a 24-hour time format");
                }
                String deadlineDate = modCodeAndDescriptionAndDate[1];
                String[] out = DateTimeParser.DeadlineParse(deadlineDate);
                String date = out[0];
                String time = out[1];
                return new AddCommand(new Deadline(fullDescriptionAndModCode, date, time));
            } catch (ParseException | ArrayIndexOutOfBoundsException e) {
                LOGGER.severe("Invalid format for adding deadline");
                throw new DukeInvalidFormatException(" OOPS!!! Please enter deadline as follows:\n" +
                        "add/d mod_code name_of_deadline /by dd/MM/yyyy HHmm\n" +
                        "or add/d mod_code name_of_deadline /by week x day HHmm\n");
            }
        } else if (fullCommand.trim().startsWith(DukeConstants.ADD_EVENT_HEADER)) {
            try {
                String activity = fullCommand.trim().replaceFirst(DukeConstants.ADD_EVENT_HEADER, "");
                modCodeAndDescriptionAndDate = activity.split(DukeConstants.EVENT_DATE_DESCRIPTION_SPLIT_KEYWORD);
                modCodeAndDescriptionSplit = modCodeAndDescriptionAndDate[0].trim().split(DukeConstants.STRING_SPACE_SPLIT_KEYWORD);
                String fullDescriptionAndModCode = modCodeAndDescriptionAndDate[0].trim();
                if (!super.isValidModCodeAndDescription(fullDescriptionAndModCode)) {
                    throw new DukeInvalidFormatException("\u2639" + " OOPS!!! The ModCode + description of an event cannot be empty.");
                }
                String modCode = modCodeAndDescriptionSplit[0];
                if (!super.isModCode(modCode)) {
                    throw new DukeInvalidFormatException("\u2639" + " OOPS!!! The ModCode is invalid");
                }
                if(!super.isValidDescription(modCodeAndDescriptionSplit)) {
                    throw new DukeInvalidFormatException("\u2639" + " OOPS!!! The description of an event cannot be empty.");
                }
                String timePeriod = modCodeAndDescriptionAndDate[1];
                if(!super.isValidTimePeriod(timePeriod)) {
                    throw new DukeInvalidFormatException("\u2639" + " OOPS!!! The time of an event can only contain digits and the time has to be 4 digits.\n" +
                            "Please enter the time in a 24-hour time format");
                }
                String eventDate = modCodeAndDescriptionAndDate[1];
                String[] out = DateTimeParser.EventParse(eventDate);
                String date = out[0];
                String startTime = out[1];
                String endTime = out[2];
                return new AddCommand(new Event(fullDescriptionAndModCode, date, startTime, endTime));
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

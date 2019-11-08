package Parser;

import Commands.Command;
import Commands.RecurringCommand;
import Commons.DukeLogger;
import DukeExceptions.DukeInvalidCommandException;
import DukeExceptions.DukeInvalidDateTimeException;
import DukeExceptions.DukeInvalidFormatException;

import java.text.ParseException;
import java.util.logging.Logger;

/**
 * This class parses the full command that calls for RecurParse.
 */
public class RecurParse extends Parse {
    private static final int LENGTH_OF_BIWEEKLY = 9;
    private static final int LENGTH_OF_RMBIWEEKLY = 11;
    private static final int LENGTH_OF_RMWEEKLY = 9;
    private static final int LENGTH_OF_WEEKLY = 7;
    private static final int LENGTH_OF_RECUR = 5;
    private static String fullCommand;
    private static String[] modCodeAndDescriptionSplit;
    private final Logger LOGGER = DukeLogger.getLogger(RecurParse.class);

    public RecurParse(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    @Override
    public Command parse() throws DukeInvalidFormatException, DukeInvalidDateTimeException {
        try {
            boolean isBiweekly = false;
            boolean isRecur = false;
            String activity = fullCommand.trim().substring(LENGTH_OF_RECUR);
            String[] fullCommandSplit = activity.split("/start");
            String modCodeAndDescription = fullCommandSplit[0].trim();
            modCodeAndDescriptionSplit = modCodeAndDescription.trim().split(" ");
            String dateAndTime = fullCommandSplit[1].trim();

            if (modCodeAndDescription.contains("/biweekly")) {
                modCodeAndDescription = modCodeAndDescription.substring(LENGTH_OF_BIWEEKLY).trim();
                isRecur = true;
                isBiweekly = true;
            } else if (modCodeAndDescription.contains("/rmbiweekly")) {
                modCodeAndDescription = modCodeAndDescription.substring(LENGTH_OF_RMBIWEEKLY).trim();
                isBiweekly = true;
            } else if (modCodeAndDescription.contains("/rmweekly")) {
                modCodeAndDescription = modCodeAndDescription.substring(LENGTH_OF_RMWEEKLY).trim();
            } else {
                modCodeAndDescription = modCodeAndDescription.substring(LENGTH_OF_WEEKLY).trim();
                isRecur = true;
            }

            if (!super.isValidModCodeAndDescription(modCodeAndDescription)) throw new DukeInvalidFormatException("\u2639" + " OOPS!!! The ModCode + description of an event cannot be empty.");
            if (!super.isModCode(modCodeAndDescription)) {
                throw new DukeInvalidFormatException("\u2639" + " OOPS!!! The ModCode is invalid");
            }
            String[] checkSplit = modCodeAndDescription.trim().split(" ");
            if (!super.isValidDescription(checkSplit)) throw new DukeInvalidFormatException("\u2639" + " OOPS!!! The description of an event cannot be empty.");
            if(!super.isValidTimePeriod(dateAndTime)) throw new DukeInvalidFormatException("\u2639" + " OOPS!!! The time of an event can only contain digits and the time has to be 4 digits.\n" +
                    "Please enter the time in a 24-hour time format");
            String[] in = DateTimeParser.recurringEventParse(dateAndTime);
            String startDateString = in[0];
            String endDateString = in[1];
            String startTimeString = in[2];
            String endTimeString = in[3];
            return new RecurringCommand(modCodeAndDescription, startDateString, endDateString, startTimeString, endTimeString, isBiweekly, isRecur);
        } catch (ParseException | ArrayIndexOutOfBoundsException e) {
            LOGGER.severe("Invalid recur format");
            throw new DukeInvalidFormatException("OOPS!!! Please enter recurring event as follows:\n" +
                    "recur/(fill) modCode name_of_event /start dd/MM/yyyy to dd/MM/yyyy /from HHmm /to HHmm\n" +
                    "Note: replace (fill) with either: weekly, biweekly, rmweekly, rmbiweekly\n" +
                    "For example: recur/weekly CS1231 project meeting /start 1/10/2019 to 15/11/2019 /from 1500 /to 1700");
        }
    }
}
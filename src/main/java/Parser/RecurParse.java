package Parser;

import Commands.Command;
import Commands.RecurringCommand;
import Commons.DukeLogger;
import DukeExceptions.DukeInvalidFormatException;

import java.text.ParseException;
import java.util.logging.Logger;

/**
 * This class parses the full command that calls for RecurParse.
 */
public class RecurParse extends Parse {

    private static String fullCommand;
    private static String[] modCodeAndDescriptionSplit;
    private final Logger LOGGER = DukeLogger.getLogger(RecurParse.class);

    public RecurParse(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    @Override
    public Command parse() throws Exception {
        try {
            // (CS1231 project meeting) /start (1/10/2019 to 15/11/2019 /from 1500 /to 1700)
            boolean isBiweekly = false;
            boolean isRecur = false;
            String activity = fullCommand.trim().substring(5);
            String[] fullCommandSplit = activity.split("/start");
            String modCodeAndDescription = fullCommandSplit[0].trim();
            modCodeAndDescriptionSplit = modCodeAndDescription.trim().split(" ");
            if (!super.isModCode(modCodeAndDescriptionSplit[1])) {
                throw new DukeInvalidFormatException("\u2639" + " OOPS!!! The ModCode is invalid");
            }
            String dateAndTime = fullCommandSplit[1].trim();

            if (modCodeAndDescription.contains("/biweekly")) {
                modCodeAndDescription = modCodeAndDescription.substring(9).trim();
                isRecur = true;
                isBiweekly = true;
            } else if (modCodeAndDescription.contains("/rmbiweekly")) {
                modCodeAndDescription = modCodeAndDescription.substring(11).trim();
                isBiweekly = true;
            } else if (modCodeAndDescription.contains("/rmweekly")) {
                modCodeAndDescription = modCodeAndDescription.substring(9).trim();
            } else {
                modCodeAndDescription = modCodeAndDescription.substring(7).trim();
                isRecur = true;
            }
            if (modCodeAndDescription.isEmpty()) {
                throw new DukeInvalidFormatException("\u2639" + " OOPS!!! The description of a event cannot be empty.");
            }
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
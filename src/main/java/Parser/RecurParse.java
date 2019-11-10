package Parser;

import Commands.Command;
import Commands.RecurringCommand;
import Commons.DukeConstants;
import Commons.DukeLogger;
import Commons.ModCodeChecker;
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
    private ModCodeChecker modCodeChecker = ModCodeChecker.getInstance();

    public RecurParse(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    @Override
    public Command parse() throws DukeInvalidFormatException, DukeInvalidDateTimeException {
        try {
            boolean isBiweekly = false;
            boolean isRecur = false;
            String activity = fullCommand.trim().substring(LENGTH_OF_RECUR);
            String[] fullCommandSplit = activity.split(DukeConstants.RECUR_DATE_DESCRIPTION_SPLIT_KEYWORD);
            String modCodeAndDescription = fullCommandSplit[0].trim();
            modCodeAndDescriptionSplit = modCodeAndDescription.trim().split(DukeConstants.BLANK_SPACE);
            String dateAndTime = fullCommandSplit[1].trim();

            if (modCodeAndDescription.contains(DukeConstants.RECUR_BIWEEKLY_KEYWORD)) {
                modCodeAndDescription = modCodeAndDescription.substring(LENGTH_OF_BIWEEKLY).trim();
                isRecur = true;
                isBiweekly = true;
            } else if (modCodeAndDescription.contains(DukeConstants.RECUR_RMBIWEEKLY_KEYWORD)) {
                modCodeAndDescription = modCodeAndDescription.substring(LENGTH_OF_RMBIWEEKLY).trim();
                isBiweekly = true;
            } else if (modCodeAndDescription.contains(DukeConstants.RECUR_RMWEEKLY_KEYWORD)) {
                modCodeAndDescription = modCodeAndDescription.substring(LENGTH_OF_RMWEEKLY).trim();
            } else {
                modCodeAndDescription = modCodeAndDescription.substring(LENGTH_OF_WEEKLY).trim();
                isRecur = true;
            }

            if (!super.isValidModCodeAndDescription(modCodeAndDescription)) {
                throw new DukeInvalidFormatException(DukeConstants.SAD_FACE
                        + DukeConstants.EVENT_EMPTY_MODCODE_DESCRIPTION_ERROR);
            }
            String[] checkSplit = modCodeAndDescription.trim().split(DukeConstants.BLANK_SPACE);
            String modCode = checkSplit[0];
            if (!modCodeChecker.isModCode(modCode)) {
                throw new DukeInvalidFormatException(DukeConstants.SAD_FACE + DukeConstants.INVALID_MODCODE_ERROR);
            }
            if (!super.isValidDescription(checkSplit)) {
                throw new DukeInvalidFormatException(DukeConstants.SAD_FACE
                        + DukeConstants.EVENT_EMPTY_DESCRIPTION_ERROR);
            }
            if (!super.isValidTimePeriod(dateAndTime)) {
                throw new DukeInvalidFormatException(DukeConstants.SAD_FACE
                        + DukeConstants.EVENT_TIME_FORMAT_ERROR);
            }
            String[] in = DateTimeParser.recurringEventParse(dateAndTime);
            String startDateString = in[0];
            String endDateString = in[1];
            String startTimeString = in[2];
            String endTimeString = in[3];
            if (!super.isValidDateRecurring(startDateString, endDateString)) {
                throw new DukeInvalidFormatException(DukeConstants.SAD_FACE
                        + DukeConstants.RECUR_EVENT_DATE_FORMAT_ERROR);
            }
            return new RecurringCommand(modCodeAndDescription, startDateString, endDateString, startTimeString,
                    endTimeString, isBiweekly, isRecur);
        } catch (ParseException | ArrayIndexOutOfBoundsException e) {
            LOGGER.severe("Invalid recur format");
            throw new DukeInvalidFormatException(DukeConstants.RECUR_EVENT_FORMAT);
        }
    }
}
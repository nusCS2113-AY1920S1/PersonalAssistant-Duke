package Parser;

import Commands.Command;
import Commands.FindFreeTimesCommand;
import Commons.DukeConstants;
import Commons.DukeLogger;
import DukeExceptions.DukeInvalidFormatException;

import java.text.ParseException;
import java.util.logging.Logger;

/**
 * This class parses the full command that calls for FindFreeTimesParse.
 */
public class FindFreeTimesParse extends Parse {
    private final Logger LOGGER = DukeLogger.getLogger(FindFreeTimesParse.class);
    private String fullCommand;
    private static final String INVALID_INPUT = "Invalid input. Please enter the command as follows. \n"
            + "find/time 'x' hours , where 'x' is a digit between 1 - 16";
    private static final String INVALID_DURATION = "Invalid duration. Please enter the command as follows. \n"
            + "find/time 'x' hours , where 'x' is a digit between 1 - 16";
    private static final String INVALID_EMPTY_DURATION = "Invalid input."
            + "\nDuration cannot be blank.\nPlease enter the command as follows.\n"
            + "find/time 'x' hours , where 'x' is a digit between 1 - 16";

    /**
     * Creates FindFreeTimesParse object.
     * @param fullCommand The input by the user
     */
    public FindFreeTimesParse(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    @Override
    public Command parse() throws DukeInvalidFormatException {
        fullCommand = fullCommand.replaceFirst(DukeConstants.FIND_TIME_HEADER, "");
        fullCommand = fullCommand.trim();
        if (fullCommand.equals(DukeConstants.FIND_TIME_KEYWORD_HOUR)
                || fullCommand.equals(DukeConstants.FIND_TIME_KEYWORD_HOURS)) {
            throw new DukeInvalidFormatException(INVALID_EMPTY_DURATION);
        } else if (fullCommand.split(" ").length == 1) {
            throw new DukeInvalidFormatException(INVALID_INPUT);
        }
        if (fullCommand.contains(DukeConstants.FIND_TIME_KEYWORD_HOURS)) {
            fullCommand = fullCommand.replaceFirst(DukeConstants.FIND_TIME_KEYWORD_HOURS, "");
        } else if (fullCommand.contains(DukeConstants.FIND_TIME_KEYWORD_HOUR)) {
            fullCommand = fullCommand.replaceFirst(DukeConstants.FIND_TIME_KEYWORD_HOUR, "");
        } else {
            throw new DukeInvalidFormatException(INVALID_INPUT);
        }
        fullCommand = fullCommand.trim();
        if (fullCommand.isEmpty()) {
            throw new DukeInvalidFormatException(INVALID_EMPTY_DURATION);
        } else {
            try {
                Integer duration = Integer.parseInt(fullCommand);
                if (duration >= DukeConstants.FIND_TIME_LOWER_BOUNDARY
                        && duration <= DukeConstants.FIND_TIME_UPPER_BOUNDARY) {
                    return new FindFreeTimesCommand(duration);
                } else {
                    throw new DukeInvalidFormatException(INVALID_DURATION);
                }
            } catch (NumberFormatException e) {
                LOGGER.info("Unable to parse string to integer" + e.getMessage());
                throw new DukeInvalidFormatException(INVALID_DURATION);
            }
        }
    }
}


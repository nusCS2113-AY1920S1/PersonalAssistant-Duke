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
    private final String invalidInput = "Invalid input. Please enter the command as follows. \n" +
            "find/time 'x' hours , where 'x' is a digit between 1 - 16";
    private final String invalidDuration = "Invalid duration. Please enter the command as follows. \n" +
            "find/time 'x' hours , where 'x' is a digit between 1 - 16";
    private final String invalidEmptyDuration = "Invalid input." +
            "\nDuration cannot be blank.\nPlease enter the command as follows.\n"
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
        if(fullCommand.equals(DukeConstants.FIND_TIME_KEYWORD_HOUR) || fullCommand.equals(DukeConstants.FIND_TIME_KEYWORD_HOURS)) throw new DukeInvalidFormatException(invalidEmptyDuration);
        else if(fullCommand.split(" ").length == 1) throw new DukeInvalidFormatException(invalidInput);
        if (fullCommand.contains(DukeConstants.FIND_TIME_KEYWORD_HOURS)) fullCommand = fullCommand.replaceFirst(DukeConstants.FIND_TIME_KEYWORD_HOURS, "");
        else if (fullCommand.contains(DukeConstants.FIND_TIME_KEYWORD_HOUR)) fullCommand = fullCommand.replaceFirst(DukeConstants.FIND_TIME_KEYWORD_HOUR, "");
        else throw new DukeInvalidFormatException(invalidInput);
        fullCommand = fullCommand.trim();
        if (fullCommand.isEmpty()) {
            throw new DukeInvalidFormatException(invalidEmptyDuration);
        } else {
            try {
                Integer duration = Integer.parseInt(fullCommand);
                if (duration >= DukeConstants.FIND_TIME_LOWER_BOUNDARY && duration <= DukeConstants.FIND_TIME_UPPER_BOUNDARY) return new FindFreeTimesCommand(duration);
                else throw new DukeInvalidFormatException(invalidDuration);
            } catch (NumberFormatException e) {
                LOGGER.info("Unable to parse string to integer" + e.getMessage());
                throw new DukeInvalidFormatException(invalidDuration);
            }
        }
    }
}


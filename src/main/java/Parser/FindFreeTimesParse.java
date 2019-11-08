package Parser;

import Commands.Command;
import Commands.FindFreeTimesCommand;
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

    /**
     * Creates FindFreeTimesParse object.
     * @param fullCommand The input by the user
     */
    public FindFreeTimesParse(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    @Override
    public Command parse() throws DukeInvalidFormatException {
        fullCommand = fullCommand.replaceFirst("find/time", "");
        fullCommand = fullCommand.trim();
        if (fullCommand.contains("hours")) fullCommand = fullCommand.replaceFirst("hours", "");
        else if (fullCommand.contains("hour")) fullCommand = fullCommand.replaceFirst("hour", "");
        else throw new DukeInvalidFormatException(invalidInput);
        fullCommand = fullCommand.trim();
        if (fullCommand.isEmpty()) {
            throw new DukeInvalidFormatException(invalidInput);
        } else {
            try {
                Integer duration = Integer.parseInt(fullCommand);
                if (duration >= 1 && duration <= 16) return new FindFreeTimesCommand(duration);
                else throw new DukeInvalidFormatException(invalidDuration);
            } catch (NumberFormatException e) {
                LOGGER.info("Unable to parse string to integer" + e.getMessage());
                throw new DukeInvalidFormatException(invalidDuration);
            }
        }
    }
}


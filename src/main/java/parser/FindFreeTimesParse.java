package parser;

import commands.Command;
import commands.FindFreeTimesCommand;
import commons.DukeConstants;
import commons.DukeLogger;
import dukeexceptions.DukeInvalidFormatException;
import java.util.logging.Logger;

/**
 * This class parses the full command that calls for FindFreeTimesParse.
 */
public class FindFreeTimesParse extends Parse {
    private final Logger logger = DukeLogger.getLogger(FindFreeTimesParse.class);
    private String fullCommand;

    /**
     * Creates FindFreeTimesParse object.
     * @param fullCommand The input by the user
     */
    public FindFreeTimesParse(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    @Override
    public Command parse() throws DukeInvalidFormatException {
        fullCommand = fullCommand.replaceFirst(DukeConstants.FIND_TIME_HEADER, DukeConstants.NO_FIELD);
        fullCommand = fullCommand.trim();
        if (fullCommand.equals(DukeConstants.FIND_TIME_KEYWORD_HOUR)
                || fullCommand.equals(DukeConstants.FIND_TIME_KEYWORD_HOURS)) {
            throw new DukeInvalidFormatException(DukeConstants.INVALID_EMPTY_DURATION);
        } else if (fullCommand.split(DukeConstants.BLANK_SPACE).length == 1) {
            throw new DukeInvalidFormatException(DukeConstants.INVALID_INPUT);
        }
        if (fullCommand.contains(DukeConstants.FIND_TIME_KEYWORD_HOURS)) {
            fullCommand = fullCommand.replaceFirst(DukeConstants.FIND_TIME_KEYWORD_HOURS, DukeConstants.NO_FIELD);
        } else if (fullCommand.contains(DukeConstants.FIND_TIME_KEYWORD_HOUR)) {
            fullCommand = fullCommand.replaceFirst(DukeConstants.FIND_TIME_KEYWORD_HOUR, DukeConstants.NO_FIELD);
        } else {
            throw new DukeInvalidFormatException(DukeConstants.INVALID_INPUT);
        }
        fullCommand = fullCommand.trim();
        if (fullCommand.isEmpty()) {
            throw new DukeInvalidFormatException(DukeConstants.INVALID_EMPTY_DURATION);
        } else {
            try {
                Integer duration = Integer.parseInt(fullCommand);
                if (duration >= DukeConstants.FIND_TIME_LOWER_BOUNDARY
                        && duration <= DukeConstants.FIND_TIME_UPPER_BOUNDARY) {
                    return new FindFreeTimesCommand(duration);
                } else {
                    throw new DukeInvalidFormatException(DukeConstants.INVALID_DURATION);
                }
            } catch (NumberFormatException e) {
                logger.severe("Unable to parse string to integer");
                throw new DukeInvalidFormatException(DukeConstants.INVALID_DURATION);
            }
        }
    }
}


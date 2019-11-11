package parser;

import commands.Command;
import commands.ShowPreviousCommand;
import commons.DukeConstants;
import commons.DukeLogger;
import dukeexceptions.DukeInvalidFormatException;
import java.util.logging.Logger;

/**
 * This class parses the full command that calls for ShowPreviousParse.
 */
public class ShowPreviousParse extends Parse {
    private final Logger logger = DukeLogger.getLogger(ShowPreviousParse.class);
    private static final Integer TOTAL_NUM_OF_COMMANDS = 22;
    private String fullCommand;

    public ShowPreviousParse(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    /**
     * Creates ShowPreviousParse object.
     * @throws DukeInvalidFormatException on invalid format that called for ShowPreviousParse
     */
    @Override
    public Command parse() throws DukeInvalidFormatException {
        String entireCommand = fullCommand.replaceFirst(DukeConstants.SHOW_PREVIOUS_HEADER, DukeConstants.NO_FIELD);
        entireCommand = entireCommand.trim();

        if (entireCommand.isEmpty()) {
            throw new DukeInvalidFormatException(DukeConstants.SHOW_PREVIOUS_FORMAT);
        }

        boolean isNumber = true;
        int number = 0;
        try {
            number = Integer.parseInt(entireCommand);
        } catch (NumberFormatException e) {
            logger.severe("Unable to parse string to integer");
            isNumber = false;
        }

        String[] listOfAllCommands = { DukeConstants.ADD_DEADLINE_HEADER, DukeConstants.ADD_EVENT_HEADER,
            DukeConstants.DELETE_DEADLINE_HEADER, DukeConstants.DELETE_EVENT_HEADER,
            DukeConstants.RECUR_WEEKLY_HEADER, DukeConstants.RECUR_BIWEEKLY_HEADER,
            DukeConstants.REMOVE_RECUR_WEEKLY_HEADER, DukeConstants.REMOVE_RECUR_BIWEEKLY_HEADER,
            DukeConstants.REMIND_SET_HEADER, DukeConstants.REMIND_CHECK_HEADER, DukeConstants.REMOVE_REMIND_HEADER,
            DukeConstants.SHOW_WEEK_HEADER, DukeConstants.SHOW_FILTER_HEADER, DukeConstants.HELP_HEADER,
            DukeConstants.DONE_EVENT_HEADER, DukeConstants.DONE_DEADLINE_HEADER, DukeConstants.FIND_TIME_HEADER,
            DukeConstants.SHOW_PREVIOUS_HEADER, DukeConstants.RETRIEVE_PREVIOUS_HEADER,
            DukeConstants.RETRIEVE_TIME_HEADER, DukeConstants.SHOW_WORKLOAD_HEADER, DukeConstants.BYE_HEADER
        };

        boolean isValid = false;
        if (isNumber && number < 0) {
            throw new DukeInvalidFormatException(DukeConstants.INVALID_NEGATIVE_NUMBER);
        } else if (!isNumber) {
            for (int i = 0; i < TOTAL_NUM_OF_COMMANDS; i++) {
                String currCommand = listOfAllCommands[i];
                if (entireCommand.equals(currCommand)) {
                    isValid = true;
                    break;
                }
            }
            if (entireCommand.contains(".")) {
                entireCommand = entireCommand.replace(".", DukeConstants.NO_FIELD);
                if (entireCommand.matches("[0-9]+")) {
                    throw new DukeInvalidFormatException(DukeConstants.INVALID_DECIMAL_NUMBER);
                } else {
                    throw new DukeInvalidFormatException(DukeConstants.NO_AND_INVALID_COMMAND_TYPE);
                }
            } else if (!isValid) {
                throw new DukeInvalidFormatException(DukeConstants.NO_AND_INVALID_COMMAND_TYPE);
            }
        } else if (number == 0) {
            throw new DukeInvalidFormatException(DukeConstants.INVALID_NUMBER_ZERO);
        }
        return new ShowPreviousCommand(entireCommand);
    }
}
package Parser;

import Commands.Command;
import Commands.ShowPreviousCommand;
import Commons.DukeConstants;
import Commons.DukeLogger;
import DukeExceptions.DukeInvalidFormatException;
import java.util.logging.Logger;

/**
 * This class parses the full command that calls for ShowPreviousParse.
 */
public class ShowPreviousParse extends Parse{
    private final Logger LOGGER = DukeLogger.getLogger(ShowPreviousParse.class);
    private final Integer TOTAL_NUM_OF_COMMANDS = 22;
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
        String entireCommand = fullCommand.replaceFirst("show/previous", "");
        entireCommand = entireCommand.trim();

        if (entireCommand.isEmpty()) {
            throw new DukeInvalidFormatException("Invalid input. Please enter format: show/previous <num>" +
                    "OR show/previous <command type>");
        }

        boolean isNumber = true;
        int number = 0;
        try {
            number = Integer.parseInt(entireCommand);
        } catch (NumberFormatException e) {
            LOGGER.severe("Unable to parse string to integer");
            isNumber = false;
        }

        String[] listOfAllCommands = {DukeConstants.ADD_DEADLINE_HEADER, DukeConstants.ADD_EVENT_HEADER,
                DukeConstants.DELETE_DEADLINE_HEADER, DukeConstants.DELETE_EVENT_HEADER, DukeConstants.RECUR_WEEKLY_HEADER,
                DukeConstants.RECUR_BIWEEKLY_HEADER, DukeConstants.REMOVE_RECUR_WEEKLY_HEADER, DukeConstants.REMOVE_RECUR_BIWEEKLY_HEADER,
                DukeConstants.REMIND_SET_HEADER, DukeConstants.REMIND_CHECK_HEADER, DukeConstants.REMOVE_REMIND_HEADER,
                DukeConstants.SHOW_WEEK_HEADER, DukeConstants.SHOW_FILTER_HEADER, DukeConstants.HELP_HEADER,
                DukeConstants.DONE_EVENT_HEADER, DukeConstants.DONE_DEADLINE_HEADER, DukeConstants.FIND_TIME_HEADER,
                DukeConstants.SHOW_PREVIOUS_HEADER, DukeConstants.RETRIEVE_PREVIOUS_HEADER,
                DukeConstants.RETRIEVE_TIME_HEADER, DukeConstants.SHOW_WORKLOAD_HEADER, DukeConstants.BYE_HEADER,};
      
        boolean isValid = false;
        if (isNumber && number < 0) {
            throw new DukeInvalidFormatException("Invalid Input. Cannot enter negative number. Please enter a valid integer greater than 0");
        } else if (!isNumber) {
            for (int i = 0; i < TOTAL_NUM_OF_COMMANDS; i++) {
                String currCommand = listOfAllCommands[i];
                if (entireCommand.equals(currCommand)) {
                    isValid = true;
                    break;
                }
            }
            if (isValid == false) {
                throw new DukeInvalidFormatException("Invalid Input. There is no such command type in previous input");
            }
        } else if (number == 0) {
            throw new DukeInvalidFormatException("Please enter a valid integer greater than 0");
        }
        return new ShowPreviousCommand(entireCommand);
    }
}

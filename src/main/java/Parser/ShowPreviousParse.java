package Parser;

import Commands.Command;
import Commands.ShowPreviousCommand;
import Commons.DukeLogger;
import DukeExceptions.DukeInvalidFormatException;

import java.util.logging.Logger;

/**
 * This class parses the full command that calls for ShowPreviousParse.
 */
public class ShowPreviousParse extends Parse{
    private final Logger LOGGER = DukeLogger.getLogger(ShowPreviousCommand.class);
    private final Integer TOTALNUMOFCOMMANDS = 15;
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
            LOGGER.info("Unable to parse string to integer" + e.getMessage());
            isNumber = false;
        }

        String[] listOfAllCommands = { "add/d","add/e", "delete/d", "delete/e", "recur/e",
                "remind/set", "remind/rm", "remind/check", "/show", "filter", "help", "list", "done", "find",
                "show/previous", "Week" };

        boolean isValid = false;
        if (isNumber && number < 0) {
            throw new DukeInvalidFormatException("Invalid Input. Cannot enter negative number");
        } else if (!isNumber) {
            for (int i = 0; i < TOTALNUMOFCOMMANDS; i++) {
                String currCommand = listOfAllCommands[i];
                if (entireCommand.equals(currCommand)) {
                    isValid = true;
                    break;
                }
            }
            if (isValid == false) {
                throw new DukeInvalidFormatException("Invalid Input. There is no such command type in previous input");
            }
        }
        return new ShowPreviousCommand(entireCommand);
    }
}

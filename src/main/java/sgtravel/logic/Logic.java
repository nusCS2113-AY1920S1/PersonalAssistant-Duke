package sgtravel.logic;

import sgtravel.commons.exceptions.DukeException;
import sgtravel.logic.commands.results.CommandResult;

/**
 * Defines the api of main logic.
 */
public interface Logic {
    /**
     * Gets response from LogicManager.
     *
     * @param userInput The input string from user.
     * @return CommandResult Object containing information for Ui to display.
     */
    CommandResult execute(String userInput) throws DukeException;
}

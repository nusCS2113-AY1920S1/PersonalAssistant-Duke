package logic.parsers;

import logic.commands.Command;
import logic.commands.DoneCommand;
import utils.DukeException;

public class DoneCommandParser {
    /**
     * parse user input to DoneCommand.
     * @param userInput the index user input
     * @return the DoneCommand
     * @throws DukeException if number format is not valid
     */
    public static Command parse(String userInput) throws DukeException {
        try {
            int index = Integer.parseInt(userInput) - 1;
            return new DoneCommand(index);
        } catch (NumberFormatException e) {
            throw new DukeException("number format error, please input the right number format.");
        }
    }
}

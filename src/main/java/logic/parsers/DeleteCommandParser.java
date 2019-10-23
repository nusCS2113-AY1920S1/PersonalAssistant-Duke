package logic.parsers;

import logic.commands.Command;
import logic.commands.DeleteCommand;
import utils.DukeException;

public class DeleteCommandParser {

    /**
     * parse the user input to a DeleteCommand.
     * @param userInput the index(es) of task to delete, as a String, separated by space
     * @return DeleteCommand
     * @throws DukeException if index(es) is not in correct number format
     */
    public static Command parse(String userInput) throws DukeException {
        try {
            String[] splites = userInput.split(" ");
            int[] indexes = new int[splites.length];
            for (int i = 0; i < splites.length; i++) {
                indexes[i] = Integer.parseInt(splites[i]);
            }
            return new DeleteCommand(userInput);
        } catch (NumberFormatException e) {
            throw new DukeException("number format error, please input the right number format.");
        }
    }
}

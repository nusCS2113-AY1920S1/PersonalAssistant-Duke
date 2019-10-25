package logic.parsers;

import logic.commands.Command;
import logic.commands.ListCommand;
import utils.DukeException;

public class ListCommandParser {
    /**
     * parse user input to a ListCommand.
     * @param userInput "tasks" or "members"
     * @return the ListCommand
     */
    public static Command parse(String userInput) throws DukeException {
        //userInput = userInput.toUpperCase();
        //userInput = SpellingErrorCorrector.commandCorrector(new String[]{"TASKS", "MEMBERS"}, userInput);
        return new ListCommand(userInput);
    }
}

package parsers;

import commands.Command;
import commands.ListCommand;

public class ListCommandParser {
    /**
     * parse user input to a ListCommand.
     * @param userInput "tasks" or "members"
     * @return the ListCommand
     */
    public static Command parse(String userInput) {
        userInput = userInput.toUpperCase();
        userInput = SpellingErrorCorrector.commandCorrector(new String[]{"TASKS", "MEMBERS"}, userInput);
        return new ListCommand(userInput);
    }
}

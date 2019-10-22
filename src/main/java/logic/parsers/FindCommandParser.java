package logic.parsers;

import logic.commands.Command;
import logic.commands.FindCommand;

public class FindCommandParser {
    public static Command parse(String userInput) {
        return new FindCommand(userInput);
    }
}

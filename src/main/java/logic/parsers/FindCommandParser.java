package parsers;

import commands.Command;
import commands.FindCommand;

public class FindCommandParser {
    public static Command parse(String userInput) {
        return new FindCommand(userInput);
    }
}

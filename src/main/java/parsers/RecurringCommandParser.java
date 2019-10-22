package parsers;

import commands.Command;
import commands.RecurringCommand;

public class RecurringCommandParser {
    public static Command parse(String userInput) {
        return new RecurringCommand(userInput); //TODO
    }
}

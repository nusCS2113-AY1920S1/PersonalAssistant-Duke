package logic.parsers;

import logic.commands.Command;
import logic.commands.RecurringCommand;

public class RecurringCommandParser {
    public static Command parse(String userInput) {
        return new RecurringCommand(userInput); //TODO
    }
}

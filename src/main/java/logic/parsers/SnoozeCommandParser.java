package logic.parsers;

import logic.commands.Command;
import logic.commands.SnoozeCommand;

public class SnoozeCommandParser {
    public static Command parse(String userInput) {
        return new SnoozeCommand(userInput); //TODO
    }
}

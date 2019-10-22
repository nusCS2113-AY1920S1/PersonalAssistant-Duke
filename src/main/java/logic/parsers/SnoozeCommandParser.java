package parsers;

import commands.Command;
import commands.SnoozeCommand;

public class SnoozeCommandParser {
    public static Command parse(String userInput) {
        return new SnoozeCommand(userInput); //TODO
    }
}

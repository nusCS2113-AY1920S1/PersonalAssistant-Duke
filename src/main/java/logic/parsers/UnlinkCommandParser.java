package logic.parsers;

import logic.commands.Command;
import logic.commands.UnlinkCommand;

public class UnlinkCommandParser {
    public static Command parse(String userInput) {
        return new UnlinkCommand(userInput); //TODO
    }
}

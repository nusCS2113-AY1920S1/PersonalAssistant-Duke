package parsers;

import commands.Command;
import commands.UnlinkCommand;

public class UnlinkCommandParser {
    public static Command parse(String userInput) {
        return new UnlinkCommand(userInput); //TODO
    }
}

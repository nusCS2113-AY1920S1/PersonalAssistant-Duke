package parsers;

import commands.Command;
import commands.LinkCommand;

public class LinkCommandParser {
    public static Command parse(String userInput) {
        return new LinkCommand(userInput); //TODO
    }
}

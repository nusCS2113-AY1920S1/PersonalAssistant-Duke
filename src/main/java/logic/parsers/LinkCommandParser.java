package logic.parsers;

import logic.commands.Command;
import logic.commands.LinkCommand;

public class LinkCommandParser {
    public static Command parse(String userInput) {
        return new LinkCommand(userInput); //TODO
    }
}

package logic.parsers;

import logic.commands.ByeCommand;
import logic.commands.Command;
import logic.commands.HelpCommand;

/**
 * This parser is to parse some simple commands, which have no arguments at all.
 */
public class SimpleCommandParser {
    public static Command bye(String userInput) {
        return new ByeCommand();
    }

    public static Command help(String userInput) {
        return new HelpCommand();
    }
}

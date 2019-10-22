package parsers;

import commands.ByeCommand;
import commands.Command;
import commands.HelpCommand;

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

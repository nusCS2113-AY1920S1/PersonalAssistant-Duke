package sgtravel.logic.parsers.commandparsers;

import sgtravel.logic.commands.Command;
import sgtravel.logic.commands.StaticMapCommand;

/**
 * Parses the user inputs into suitable format for StaticMapCommand.
 */
public class StaticMapParser extends CommandParser {
    private String location;

    /**
     * Constructs the StaticMapParser.
     *
     * @param input The user input.
     */
    public StaticMapParser(String input) {
        this.location = input;
    }

    /**
     * Parses the user input and constructs StaticMapCommand object.
     *
     * @return StaticMapCommand object.
     */
    @Override
    public Command parse() {
        return new StaticMapCommand(location);
    }
}

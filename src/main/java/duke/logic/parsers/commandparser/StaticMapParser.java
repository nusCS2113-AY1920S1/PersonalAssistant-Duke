package duke.logic.parsers.commandparser;

import duke.logic.commands.Command;
import duke.logic.commands.StaticMapCommand;

/**
 * Parses the user inputs into suitable format for StaticMapCommand.
 */
public class StaticMapParser extends CommandParser {
    private String location;

    /**
     * Constructs the StaticMapParser.
     */
    public StaticMapParser(String input) {
        this.location = input;
    }

    /**
     * Parses the user input and constructs StaticMapCommand object.
     * @return StaticMapCommand object.
     */
    @Override
    public Command parse() {
        return new StaticMapCommand(location);
    }
}

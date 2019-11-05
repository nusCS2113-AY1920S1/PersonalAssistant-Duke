package duke.logic.parsers.commandparsers;

import duke.logic.commands.Command;
import duke.logic.commands.FindCommand;

/**
 * Parses the user inputs into suitable format for FindCommand.
 */
public class FindParser extends CommandParser {
    private String keyword;

    /**
     * Constructs the FindParser.
     */
    public FindParser(String input) {
        keyword = input;
    }

    /**
     * Parses the user input and constructs FindCommand object.
     * @return FindCommand object.
     */
    public Command parse() {
        return new FindCommand(keyword);
    }
}

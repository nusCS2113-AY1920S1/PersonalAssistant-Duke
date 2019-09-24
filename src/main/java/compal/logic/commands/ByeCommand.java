package compal.logic.commands;

import compal.logic.parser.CommandParser;
import compal.main.Duke;

/**
 * Executes user input "bye".
 */
public class ByeCommand extends Command implements CommandParser {

    /**
     * Constructs ByeCommand object.
     *
     * @param d Duke.
     */
    public ByeCommand(Duke d) {
        super(d);
    }

    /**
     * Exits program.
     *
     * @param userIn Entire user input string.
     */
    @Override
    public void parseCommand(String userIn) {
        System.exit(0);
    }
}

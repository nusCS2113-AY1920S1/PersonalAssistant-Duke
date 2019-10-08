package compal.logic.commands;

import compal.commons.Compal;
import compal.logic.parser.CommandParser;

/**
 * Executes user input "clear".
 */
public class ClearCommand extends Command implements CommandParser {

    /**
     * Constructs ClearCommand object.
     *
     * @param d Compal
     */
    public ClearCommand(Compal d) {
        super(d);
    }

    /**
     * Clears the display viewport on the GUI.
     *
     * @param userIn Entire user input string.
     */
    @Override
    public void parseCommand(String userIn) {
        compal.ui.clearPrimary();
    }
}

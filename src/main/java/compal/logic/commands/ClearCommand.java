package compal.logic.commands;

import compal.logic.parser.CommandParser;
import compal.main.Duke;

/**
 * Executes user input "clear".
 */
public class ClearCommand extends Command implements CommandParser {

    /**
     * Constructs ClearCommand object.
     *
     * @param d Duke
     */
    public ClearCommand(Duke d) {
        super(d);
    }

    /**
     * Clears the display viewport on the GUI.
     *
     * @param userIn Entire user input string.
     */
    @Override
    public void Command(String userIn) {
        duke.ui.clearPrimary();
    }
}

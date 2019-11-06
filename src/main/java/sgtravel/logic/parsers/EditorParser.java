package sgtravel.logic.parsers;

import sgtravel.logic.edits.EditorManager;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Parser for editing operations.
 */
public class EditorParser {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Parses the user input.
     * @param userInput The user input from ui.
     * @return True if the edits can be saved.
     */
    public static boolean parse(String userInput) {
        logger.log(Level.FINE, "Editor parsing user input");
        switch (userInput) {
        case "done": case "save": case "x":
            EditorManager.deactivate();
            return true;
        case "close": case "end": case "X":
            EditorManager.deactivate();
            break;
        default:
        }
        return false;
    }
}

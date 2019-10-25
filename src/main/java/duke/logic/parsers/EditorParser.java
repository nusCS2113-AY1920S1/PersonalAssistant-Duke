package duke.logic.parsers;

import duke.commons.exceptions.DukeException;
import duke.commons.exceptions.parser.EventSelectionOutOfBoundsException;
import duke.logic.EditorManager;
import duke.logic.api.ApiParser;
import duke.model.Event;

import java.util.logging.Level;
import java.util.logging.Logger;

public class EditorParser {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static final int DESCRIPTION = 0;
    private static final int START_DATE = 1;
    private static final int END_DATE = 2;

    public static void parse(String userInput, int eventField, Event event) throws DukeException {
        switch (userInput) {
        case "end": case "close": case "x":
            EditorManager.deactivate();
            return;
        }
        switch (eventField) {
        case DESCRIPTION:
            event.setLocation(ApiParser.getLocationSearch(userInput));
            break;
        case START_DATE:
            event.setStartDate(ParserTimeUtil.parseStringToDate(userInput));
            break;
        case END_DATE:
            event.setEndDate(ParserTimeUtil.parseStringToDate(userInput));
            break;
        default:
            throw new EventSelectionOutOfBoundsException();
        }
    }
}
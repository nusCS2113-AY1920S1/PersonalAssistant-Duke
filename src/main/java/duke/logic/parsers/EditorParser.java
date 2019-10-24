package duke.logic.parsers;

import duke.commons.exceptions.DukeException;
import duke.logic.api.ApiParser;
import duke.model.Event;

public class EditorParser {
    private static final int DESCRIPTION = 0;
    private static final int START_DATE = 1;
    private static final int END_DATE = 2;

    public void parse(String userInput, int eventField, Event event) throws DukeException {
        switch (eventField) {
        case DESCRIPTION:
            event.setLocation(ApiParser.getLocationSearch(userInput));
        case START_DATE:
            event.setStartDate(ParserTimeUtil.parseStringToDate(userInput));
        case END_DATE:
            event.setEndDate(ParserTimeUtil.parseStringToDate(userInput));
        default:
            throw new EventSelectionOutOfBoundsException();
        }
    }
}
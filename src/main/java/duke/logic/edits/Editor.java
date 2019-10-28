package duke.logic.edits;

import duke.commons.exceptions.ApiNullRequestException;
import duke.commons.exceptions.ApiTimeoutException;
import duke.commons.exceptions.DukeDateTimeParseException;
import duke.commons.exceptions.EventSelectionOutOfBoundsException;
import duke.logic.api.ApiParser;
import duke.logic.parsers.ParserTimeUtil;
import duke.model.Event;

public class Editor {
    private static final int DESCRIPTION = 0;
    private static final int START_DATE = 1;
    private static final int END_DATE = 2;

    public static void edit(String userInput, Event event, int eventField) throws EventSelectionOutOfBoundsException, ApiNullRequestException,
            ApiTimeoutException, DukeDateTimeParseException {
        switch (eventField) {
        case DESCRIPTION:
            editDescription(userInput, event);
            break;
        case START_DATE:
            editStartDate(userInput, event);
            break;
        case END_DATE:
            editEndDate(userInput, event);
            break;
        default:
            throw new EventSelectionOutOfBoundsException();
        }
    }

    private static void editDescription(String userInput, Event event) throws ApiNullRequestException, ApiTimeoutException {
        event.setLocation(ApiParser.getLocationSearch(userInput));
        event.setDescription(userInput);
    }

    private static void editStartDate(String userInput, Event event) throws DukeDateTimeParseException {
        event.setStartDate(ParserTimeUtil.parseStringToDate(userInput));
    }

    private static void editEndDate(String userInput, Event event) throws DukeDateTimeParseException {
        event.setEndDate(ParserTimeUtil.parseStringToDate(userInput));
    }
}

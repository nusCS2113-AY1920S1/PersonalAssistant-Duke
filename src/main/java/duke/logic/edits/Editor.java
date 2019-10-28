package duke.logic.edits;

import duke.commons.exceptions.ApiNullRequestException;
import duke.commons.exceptions.ApiTimeoutException;
import duke.commons.exceptions.DukeDateTimeParseException;
import duke.commons.exceptions.EventSelectionOutOfBoundsException;
import duke.logic.api.ApiParser;
import duke.logic.parsers.ParserTimeUtil;
import duke.model.Event;

/**
 * Editor that edits an Event object.
 */
public class Editor {
    private static final int DESCRIPTION = 0;
    private static final int START_DATE = 1;
    private static final int END_DATE = 2;

    /**
     * Edits an Event object.
     * @param userInput The new information.
     * @param event The Event object.
     * @param eventField Integer indicating which field to edit.
     * @throws EventSelectionOutOfBoundsException If the eventField does not corresponds to any component of Event.
     * @throws ApiNullRequestException If the location does not exist.
     * @throws ApiTimeoutException If the API request time out.
     * @throws DukeDateTimeParseException If the date format is invalid.
     */
    public static void edit(String userInput, Event event, int eventField) throws EventSelectionOutOfBoundsException,
            ApiNullRequestException, ApiTimeoutException, DukeDateTimeParseException {
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

    /**
     * Edits the description of an Event object.
     * @param userInput The new description.
     * @param event The Event object.
     * @throws ApiNullRequestException If the user input is not a location.
     * @throws ApiTimeoutException If the request time out.
     */
    private static void editDescription(String userInput, Event event) throws ApiNullRequestException,
            ApiTimeoutException {
        event.setLocation(ApiParser.getLocationSearch(userInput));
        event.setDescription(userInput);
    }

    /**
     * Edits the start date of an Event object.
     * @param userInput The new date.
     * @param event The Event object.
     * @throws DukeDateTimeParseException If the date format of user input is invalid.
     */
    private static void editStartDate(String userInput, Event event) throws DukeDateTimeParseException {
        event.setStartDate(ParserTimeUtil.parseStringToDate(userInput));
    }

    /**
     * Edits the end date of an Event object.
     * @param userInput The new date.
     * @param event The Event object.
     * @throws DukeDateTimeParseException If the date format of user input is invalid.
     */
    private static void editEndDate(String userInput, Event event) throws DukeDateTimeParseException {
        event.setEndDate(ParserTimeUtil.parseStringToDate(userInput));
    }
}

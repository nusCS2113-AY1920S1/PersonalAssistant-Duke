package sgtravel.logic.edits;

import sgtravel.commons.exceptions.ApiException;
import sgtravel.commons.exceptions.ParseException;
import sgtravel.commons.exceptions.OutOfBoundsException;
import sgtravel.logic.api.ApiParser;
import sgtravel.logic.parsers.ParserTimeUtil;
import sgtravel.model.Event;

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
     * @throws OutOfBoundsException If the eventField is out of bounds.
     * @throws ApiException If there is an issue with the request.
     * @throws ParseException If the date format is invalid.
     */
    public static void edit(String userInput, Event event, int eventField) throws OutOfBoundsException,
            ApiException, ParseException {
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
            throw new OutOfBoundsException();
        }
    }

    /**
     * Edits the description of an Event object.
     * @param userInput The new description.
     * @param event The Event object.
     * @throws ApiException If there is an issue with the request.
     */
    private static void editDescription(String userInput, Event event) throws ApiException {
        event.setLocation(ApiParser.getLocationSearch(userInput));
        event.setDescription(userInput);
    }

    /**
     * Edits the start date of an Event object.
     * @param userInput The new date.
     * @param event The Event object.
     * @throws ParseException If the date format of user input is invalid.
     */
    private static void editStartDate(String userInput, Event event) throws ParseException {
        event.setStartDate(ParserTimeUtil.parseStringToDate(userInput));
    }

    /**
     * Edits the end date of an Event object.
     * @param userInput The new date.
     * @param event The Event object.
     * @throws ParseException If the date format of user input is invalid.
     */
    private static void editEndDate(String userInput, Event event) throws ParseException {
        event.setEndDate(ParserTimeUtil.parseStringToDate(userInput));
    }
}

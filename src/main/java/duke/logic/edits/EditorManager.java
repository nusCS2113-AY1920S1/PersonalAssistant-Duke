package duke.logic.edits;

import duke.commons.exceptions.ApiException;
import duke.commons.exceptions.EmptyVenueException;
import duke.commons.exceptions.EventNotSelectedException;
import duke.commons.exceptions.ParseException;
import duke.commons.exceptions.OutOfBoundsException;
import duke.logic.commands.Command;
import duke.logic.commands.EditCommand;
import duke.logic.commands.results.PanelResult;
import duke.logic.parsers.EditorParser;
import duke.logic.parsers.commandparser.PromptParser;
import duke.logic.selectors.EventFieldSelector;
import duke.logic.selectors.LocationSelector;
import duke.model.Event;
import duke.model.lists.EventList;
import duke.model.lists.VenueList;

import javafx.scene.input.KeyCode;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages editing operations of the EventList.
 */
public class EditorManager {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static LocationSelector eventSelector;
    private static EventFieldSelector fieldSelector;
    private static EventList events;
    private static VenueList venues;
    private static Event currentEvent;
    private static int eventField;
    private static boolean isActive = false;

    /**
     * Activates the EditorManager.
     */
    public static void activate(EventList events, VenueList venues) throws EmptyVenueException {
        logger.log(Level.INFO, "Activating editor...");
        EditorManager.events = events;
        EditorManager.venues = venues;
        eventSelector = new LocationSelector(venues);
        fieldSelector = new EventFieldSelector();
        isActive = true;
        selectEvent();
    }

    public static void deactivate() {
        isActive = false;
    }

    public static boolean isActive() {
        return isActive;
    }

    /**
     * Edits the EventList.
     * @param userInput The input string from user.
     * @return Command object for logic to execute.
     * @throws EventNotSelectedException If there are no event selected.
     * @throws OutOfBoundsException If the Event field is out of bounds.
     * @throws ParseException If the user input cannot be parsed.
     * @throws ApiException If the API request timeout.
     */
    public static Command edit(String userInput) throws EventNotSelectedException, ParseException,
            ApiException, OutOfBoundsException {
        if (!eventSelector.isLock()) {
            throw new EventNotSelectedException();
        }
        assert (currentEvent != null) : "currentEvent must always exist when the lock is on";
        boolean canSave = EditorParser.parse(userInput);
        if (isActive) {
            Editor.edit(userInput, currentEvent, eventField);
            return PromptParser.parseCommand("editing...");
        }
        return new EditCommand(canSave, events);
    }

    /**
     * Edits the EventList.
     */
    public static PanelResult edit(KeyCode keyCode) {
        if (keyCode.equals(KeyCode.ESCAPE)) {
            eventSelector.unlock();
        } else if (eventSelector.isLock()) {
            fieldSelector.feedKeyCode(keyCode);
            selectEventField();
        } else {
            eventSelector.feedKeyCode(keyCode);
            selectEvent();
        }
        return new PanelResult(currentEvent, venues, eventSelector.isLock(),
                eventSelector.getIndex(), eventField);
    }

    private static void selectEventField() {
        eventField = fieldSelector.getIndex();
    }

    private static void selectEvent() {
        currentEvent = events.get(eventSelector.getIndex());
    }
}

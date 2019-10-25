package duke.logic;

import duke.commons.exceptions.DukeException;
import duke.commons.exceptions.EmptyVenueException;
import duke.commons.exceptions.EventNotSelectedException;
import duke.logic.commands.Command;
import duke.logic.commands.EditCommand;
import duke.logic.parsers.EditorParser;
import duke.logic.parsers.Parser;
import duke.logic.parsers.PromptParser;
import duke.logic.selectors.EventFieldSelector;
import duke.logic.selectors.LocationSelector;
import duke.model.Event;
import duke.model.lists.EventList;
import duke.model.lists.VenueList;
import javafx.scene.input.KeyCode;

import java.util.logging.Level;
import java.util.logging.Logger;

public class EditorManager {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static LocationSelector eventSelector;
    private static EventFieldSelector fieldSelector;
    private static EventList events;
    private static boolean isEventLock;
    private static Event currentEvent;
    private static int eventField;
    private static boolean isActive = false;

    public static void activate(EventList events, VenueList venues) throws EmptyVenueException {
        EditorManager.events = events;
        eventSelector = new LocationSelector(venues);
        fieldSelector = new EventFieldSelector();
        isEventLock = false;
        isActive = true;
        selectEvent();
    }

    public static void deactivate() {
        isActive = false;
    }

    public static boolean isActive() {
        return isActive;
    }

    public static Command edit(String userInput) throws DukeException {
        if (!isEventLock) {
            throw new EventNotSelectedException();
        }
        assert (currentEvent != null) : "currentEvent must always exist when the lock is on";
        EditorParser.parse(userInput, eventField, currentEvent);
        if (isActive) {
            return PromptParser.parseCommand("editing...");
        }
        return new EditCommand(events);
    }

    public static void edit(KeyCode keyCode) {
        if (isEventLock) {
            fieldSelector.feedKeyCode(keyCode);
            selectEventField();
        } else {
            eventSelector.feedKeyCode(keyCode);
            selectEvent();
        }
    }

    private static void selectEventField() {
        eventField = fieldSelector.getIndex();
    }

    private static void selectEvent() {
        isEventLock = eventSelector.isLock();
        currentEvent = events.get(eventSelector.getIndex());
    }
}

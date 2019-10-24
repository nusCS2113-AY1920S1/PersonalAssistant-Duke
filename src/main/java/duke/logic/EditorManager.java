package duke.logic;

import duke.commons.exceptions.DukeException;
import duke.commons.exceptions.EmptyVenueException;
import duke.commons.exceptions.EventNotSelectedException;
import duke.logic.parsers.EditorParser;
import duke.logic.selectors.EventFieldSelector;
import duke.logic.selectors.LocationSelector;
import duke.model.Event;
import duke.model.lists.EventList;
import duke.model.lists.VenueList;
import javafx.scene.input.KeyEvent;

public class EditorManager {
    private LocationSelector eventSelector;
    private EventFieldSelector fieldSelector;
    private EventList events;
    private boolean isEventLock;
    private Event currentEvent;
    private int eventField;

    public EditorManager(EventList events, VenueList venues) throws EmptyVenueException {
        this.events = events;
        eventSelector = new LocationSelector(venues);
        fieldSelector = new EventFieldSelector();
        isEventLock = false;
    }

    public void edit(String userInput) throws DukeException {
        if (!isEventLock) {
            throw new EventNotSelectedException();
        }
        assert (currentEvent != null) : "currentEvent must always exist when the lock is on";
        EditorParser.parse(userInput, eventField, currentEvent);
    }

    public void edit(KeyEvent keyEvent) {
        if (isEventLock) {
            fieldSelector.feedKeyEvent(keyEvent);
            selectEventField();
        } else {
            eventSelector.feedKeyEvent(keyEvent);
            selectEvent();
        }
    }

    private void selectEventField() {
        eventField = fieldSelector.getIndex();
    }

    private void selectEvent() {
        if (eventSelector.isLock()) {
            isEventLock = true;
        } else {
            isEventLock = false;
        }
        currentEvent = events.get(eventSelector.getIndex());
    }
}

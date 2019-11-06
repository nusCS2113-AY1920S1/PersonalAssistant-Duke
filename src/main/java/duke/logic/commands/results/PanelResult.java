package duke.logic.commands.results;

import duke.commons.exceptions.OutOfBoundsException;
import duke.logic.parsers.ParserTimeUtil;
import duke.model.Event;
import duke.model.lists.VenueList;
import duke.model.locations.Venue;

import javafx.scene.paint.Paint;

/**
 * Represents the information that is to be displayed in side panel.
 */
public class PanelResult {
    private Event event;
    private VenueList venues;
    private boolean isLock;
    private int index;
    private boolean isReady;
    private int field;

    public PanelResult() {
        isReady = false;
    }

    /**
     * Constructs a PanelResult object.
     * @param event The event to be displayed.
     * @param venues The venues to be displayed.
     * @param isLock Whether an event is locked on.
     * @param index The index of the event.
     * @param field The index of the field that is selected.
     */
    public PanelResult(Event event, VenueList venues, boolean isLock, int index, int field) {
        this.event = event;
        this.venues = venues;
        this.isLock = isLock;
        this.index = index;
        this.field = field;
        isReady = true;
    }

    /**
     * Checks if the result is ready.
     */
    public boolean isReady() {
        return isReady;
    }

    /**
     * Tries to get the description of the event.
     */
    public String getDescription() {
        assert (event != null) : "This method should only be called if its isReady.";
        return event.getLocation().getAddress();
    }

    /**
     * Returns the start date of the Event.
     */
    public String getStartDate() {
        return ParserTimeUtil.stringify(event.getStartDate());
    }

    /**
     * Returns the end date of the Event.
     */
    public String getEndDate() {
        return ParserTimeUtil.stringify(event.getEndDate());
    }

    /**
     * Gets the color of the venues.
     */
    public Paint getVenueColor(int index) {
        if (this.index == index) {
            if (isLock) {
                return Paint.valueOf("orange");
            }
            return Paint.valueOf("green");
        }
        return Paint.valueOf("blue");
    }

    public Venue getVenue(int index) {
        return venues.get(index);
    }

    public int size() {
        return venues.size();
    }

    /**
     * Gets the index of the field that is currently selected.
     */
    public int getField() throws OutOfBoundsException {
        if (!isLock) {
            throw new OutOfBoundsException();
        }
        return field;
    }
}

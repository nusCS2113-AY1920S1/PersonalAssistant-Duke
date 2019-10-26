package duke.logic.commands.results;

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

    public PanelResult(Event event, VenueList venues, boolean isLock, int index, int field) {
        this.event = event;
        this.venues = venues;
        this.isLock = isLock;
        this.index = index;
        this.field = field;
        isReady = true;
    }

    public boolean isReady() {
        return isReady;
    }

    public String getDescription() {
        if (event == null) {
            return null;
        }
        return event.getLocation().getAddress();
    }

    public String getStartDate() {
        return event.getStartDate().toString();
    }

    public String getEndDate() {
        return event.getEndDate().toString();
    }

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

    public int getField() {
        if (!isLock) {
            return -1;
        }
        return field;
    }
}

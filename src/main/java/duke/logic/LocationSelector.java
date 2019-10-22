package duke.logic;

import duke.commons.Messages;
import duke.commons.exceptions.DukeException;
import duke.model.lists.VenueList;
import duke.model.locations.Venue;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class LocationSelector {
    private VenueList venues;
    private int index;
    private boolean isLock;

    /**
     * Constructs a new Location Selector object.
     * @param venues The venues to be iterated through.
     * @throws DukeException If there is no venues in the list.
     */
    public LocationSelector(VenueList venues) throws DukeException {
        if (venues.isEmpty()) {
            throw new DukeException(Messages.LOCATION_SELECTOR_ERROR);
        }
        this.venues = venues;
        index = 0;
        isLock = false;
    }

    public int getIndex() {
        return index;
    }

    /**
     * Feeds key event to location selector to determine the next location selected.
     *
     * @param keyEvent KeyEvent from the Ui.
     */
    public void feedKeyEvent(KeyEvent keyEvent) {
        if (isLock) {
            return;
        }
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            lock();
        } else if (keyEvent.getCode().isArrowKey()) {
            index = find(keyEvent.getCode());
        }
    }

    private int find(KeyCode keyCode) {
        Venue currentVenue = venues.get(index);
        double min = Double.POSITIVE_INFINITY;
        int nextIndex = index;
        for (int i = 0; i < venues.size(); ++i) {
            if (i == index) {
                continue;
            }
            Venue v = venues.get(i);
            if (LocationHelper.checkDirection(keyCode, currentVenue, v)
                    && v.getDistance(currentVenue) < min) {
                nextIndex = i;
                min = v.getDistance(currentVenue);
            }
        }
        return nextIndex;
    }

    /**
     * Locks the Location Selector.
     */
    private void lock() {
        isLock = true;
    }

    /**
     * Unlocks the Location Selector.
     */
    public void unlock() {
        isLock = false;
    }
}

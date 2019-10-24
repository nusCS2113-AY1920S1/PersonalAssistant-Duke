package duke.logic.selectors;

import duke.commons.Messages;
import duke.commons.exceptions.DukeException;
import duke.model.lists.VenueList;
import duke.model.locations.Venue;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Iterates through locations using proximity of Venue via arrow keys.
 */
public class LocationSelector implements Selector {
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

    @Override
    public int getIndex() {
        return index;
    }

    /**
     * Feeds key event to location selector to determine the next location selected.
     *
     * @param keyEvent KeyEvent from the Ui.
     */
    @Override
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

    /**
     * Finds the next nearest Venue given the KeyCode.
     *
     * @param keyCode KeyCode indicating up/down/left/right.
     * @return The index of the nearest Venue in the given keycode direction.
     */
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

    public boolean isLock() {
        return isLock;
    }
}

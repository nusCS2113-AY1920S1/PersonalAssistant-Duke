package sgtravel.logic.selectors;

import sgtravel.commons.exceptions.EmptyVenueException;
import sgtravel.model.lists.VenueList;
import sgtravel.model.locations.Venue;

import javafx.scene.input.KeyCode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LocationSelectorTest {
    private Venue v1 = new Venue("YEW TEE INDUSTRIAL ESTATE", 1.3973210291170202, 103.753758637401, 0, 0);
    private Venue v2 = new Venue("Tuas Checkpoint", 1.34942405517095, 103.636127935782, 0, 0);

    @Test
    void getIndex() throws EmptyVenueException {
        assertThrows(EmptyVenueException.class, () -> {
            new LocationSelector(new VenueList());
        });
        VenueList venues = new VenueList();
        venues.add(v1);
        LocationSelector selector = new LocationSelector(venues);
        assertEquals(selector.getIndex(), 0);
    }

    @Test
    void feedKeyCode() throws EmptyVenueException {
        VenueList venues = new VenueList();
        venues.add(v1);
        venues.add(v2);
        LocationSelector selector = new LocationSelector(venues);
        selector.feedKeyCode(KeyCode.LEFT);
        assertEquals(selector.getIndex(), 1);
        selector.feedKeyCode(KeyCode.LEFT);
        assertEquals(selector.getIndex(), 1);
        selector.feedKeyCode(KeyCode.RIGHT);
        assertEquals(selector.getIndex(), 0);
        selector.feedKeyCode(KeyCode.RIGHT);
        assertEquals(selector.getIndex(), 0);
        selector.feedKeyCode(KeyCode.DOWN);
        assertEquals(selector.getIndex(), 1);
        selector.feedKeyCode(KeyCode.DOWN);
        assertEquals(selector.getIndex(), 1);
        selector.feedKeyCode(KeyCode.UP);
        assertEquals(selector.getIndex(), 0);
        selector.feedKeyCode(KeyCode.UP);
        assertEquals(selector.getIndex(), 0);

    }

    @Test
    void unlock() throws EmptyVenueException {
        VenueList venues = new VenueList();
        venues.add(v1);
        venues.add(v2);
        LocationSelector selector = new LocationSelector(venues);
        assertFalse(selector.isLock());
        selector.unlock();
        assertFalse(selector.isLock());
        selector.feedKeyCode(KeyCode.ENTER);
        assertTrue(selector.isLock());
        selector.unlock();
        assertFalse(selector.isLock());
    }

    @Test
    void isLock() throws EmptyVenueException {
        VenueList venues = new VenueList();
        venues.add(v1);
        venues.add(v2);
        LocationSelector selector = new LocationSelector(venues);
        assertFalse(selector.isLock());
        selector.feedKeyCode(KeyCode.K);
        assertFalse(selector.isLock());
        selector.feedKeyCode(KeyCode.UP);
        assertFalse(selector.isLock());
        selector.feedKeyCode(KeyCode.ENTER);
        assertTrue(selector.isLock());
        selector.feedKeyCode(KeyCode.ENTER);
        assertTrue(selector.isLock());
        selector.feedKeyCode(KeyCode.K);
        assertTrue(selector.isLock());
        selector.feedKeyCode(KeyCode.UP);
        assertTrue(selector.isLock());
    }
}

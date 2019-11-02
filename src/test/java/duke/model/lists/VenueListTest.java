package duke.model.lists;

import duke.model.locations.Venue;
import duke.model.transports.Route;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

class VenueListTest {

    @Test
    void add() {
        VenueList venueList = new VenueList();
        Venue v1 = new Venue("YEW TEE INDUSTRIAL ESTATE", 1.3973210291170202, 103.753758637401,
                0, 0);
        venueList.add(v1);
        assertTrue(venueList.contains(v1));

        Venue v2 = new Venue("Tuas Checkpoint", 1.34942405517095, 103.636127935782, 0, 0);
        venueList.add(v2);
        assertTrue(venueList.contains(v2));
    }

    @Test
    void get() {
        VenueList venueList = new VenueList();
        Venue v1 = new Venue("YEW TEE INDUSTRIAL ESTATE", 1.3973210291170202, 103.753758637401,
                0, 0);
        venueList.add(v1);

        assertEquals(v1, venueList.get(0));

        //test OOB
        assertThrows(IndexOutOfBoundsException.class, () -> {
            venueList.get(-1);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            venueList.get(-2);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            venueList.get(1);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            venueList.get(2);
        });
    }

    @Test
    void remove() {
        VenueList venueList = new VenueList();
        Venue v1 = new Venue("YEW TEE INDUSTRIAL ESTATE", 1.3973210291170202, 103.753758637401,
                0, 0);
        venueList.add(v1);
        assertTrue(venueList.contains(v1));

        venueList.remove(0);
        assertEquals(0, venueList.size());

        assertThrows(IndexOutOfBoundsException.class, () -> {
            venueList.remove(0);
        });

        venueList.add(v1);
        assertThrows(IndexOutOfBoundsException.class, () -> {
            venueList.remove(-1);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            venueList.remove(-2);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            venueList.remove(1);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            venueList.remove(2);
        });
    }

    @Test
    void getVenueList() {
        Venue v1 = new Venue("YEW TEE INDUSTRIAL ESTATE", 1.3973210291170202, 103.753758637401,
                0, 0);
        List<Venue> vList = new ArrayList<>();
        vList.add(v1);

        VenueList venueList = new VenueList(vList);
        assertEquals(vList, venueList.getVenueList());
    }

    @Test
    void setVenue() {
        VenueList venueList = new VenueList();
        Venue v1 = new Venue("YEW TEE INDUSTRIAL ESTATE", 1.3973210291170202, 103.753758637401,
                0, 0);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            venueList.setVenue(1, v1);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            venueList.setVenue(2, v1);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            venueList.setVenue(-1, v1);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            venueList.setVenue(-2, v1);
        });

        Venue v2 = new Venue("Tuas Checkpoint", 1.34942405517095, 103.636127935782, 0, 0);
        venueList.add(v2);

        venueList.setVenue(0, v1);
        assertTrue(venueList.contains(v1));
    }

    @Test
    void size() {
        VenueList venueList = new VenueList();
        assertEquals(0, venueList.size());

        Venue v1 = new Venue("YEW TEE INDUSTRIAL ESTATE", 1.3973210291170202, 103.753758637401,
                0, 0);
        venueList.add(v1);
        assertEquals(1, venueList.size());

        Venue v2 = new Venue("Tuas Checkpoint", 1.34942405517095, 103.636127935782, 0, 0);
        venueList.add(v2);
        assertEquals(2, venueList.size());
    }

    @Test
    void contains() {
        VenueList venueList = new VenueList();
        assertEquals(0, venueList.size());

        Venue v1 = new Venue("YEW TEE INDUSTRIAL ESTATE", 1.3973210291170202, 103.753758637401,
                0, 0);
        assertFalse(venueList.contains(v1));
        venueList.add(v1);
        assertTrue(venueList.contains(v1));

        Venue v2 = new Venue("Tuas Checkpoint", 1.34942405517095, 103.636127935782, 0, 0);
        assertFalse(venueList.contains(v2));
        venueList.add(v2);
        assertTrue(venueList.contains(v2));
    }

    @Test
    void isEmpty() {
        VenueList venueList = new VenueList();
        assertTrue(venueList.isEmpty());

        Venue v1 = new Venue("YEW TEE INDUSTRIAL ESTATE", 1.3973210291170202, 103.753758637401,
                0, 0);
        venueList.add(v1);
        assertFalse(venueList.isEmpty());
    }
}

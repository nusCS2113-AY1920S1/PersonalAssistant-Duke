package duke.model.locations;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VenueTest {
    private Venue v1 = new Venue("YEW TEE INDUSTRIAL ESTATE", 1.3973210291170202, 103.753758637401, 0, 0);
    private Venue v2 = new Venue("Tuas Checkpoint", 1.34942405517095, 103.636127935782, 0, 0);

    @Test
    void getAddress() {
        assertEquals("YEW TEE INDUSTRIAL ESTATE", v1.getAddress());
        assertNotEquals("YEW TEE INDUSTRIAL ESTATE", v2.getAddress());
    }

    @Test
    void getLatitude() {
        assertEquals(1.3973210291170202, v1.getLatitude());
        assertNotEquals(1.3973210291170202, v2.getLatitude());
    }

    @Test
    void getLongitude() {
        assertEquals(103.753758637401, v1.getLongitude());
        assertNotEquals(103.753758637401, v2.getLongitude());
    }

    @Test
    void getDistX() {
        assertEquals(0, v1.getDistX());
    }

    @Test
    void getDistY() {
        assertEquals(0, v2.getDistY());
    }

    @Test
    void getDistance() {
        assertTrue(v2.getDistance(v1) < 15000);
        assertTrue(v1.getDistance(v2) > 1000);
        assertEquals(v1.getDistance(v2), v2.getDistance(v1));
    }

    @Test
    void setLatitude() {
        v1.setLatitude(0);
        assertEquals(0, v1.getLatitude());
    }

    @Test
    void setLongitude() {
        v2.setLongitude(0);
        assertEquals(0, v2.getLongitude());
    }

    @Test
    void testToString() {
        assertEquals("YEW TEE INDUSTRIAL ESTATE | 1.3973210291170202 | 103.753758637401 | 0.0 | 0.0", v1.toString());
    }

    @Test
    void testEquals() {
        Venue v = new Venue("YEW TEE INDUSTRIAL ESTATE", 1.3973210291170202, 103.753758637401, 0, 0);
        assertTrue(v.equals(v1));
        assertFalse(v2.equals(v1));
    }
}

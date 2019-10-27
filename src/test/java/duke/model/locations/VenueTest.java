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
        assertEquals(v1.getAddress(), "YEW TEE INDUSTRIAL ESTATE");
        assertNotEquals(v2.getAddress(), "YEW TEE INDUSTRIAL ESTATE");
    }

    @Test
    void getLatitude() {
        assertEquals(v1.getLatitude(), 1.3973210291170202);
        assertNotEquals(v2.getLatitude(), 1.3973210291170202);
    }

    @Test
    void getLongitude() {
        assertEquals(v1.getLongitude(), 103.753758637401);
        assertNotEquals(v2.getLongitude(), 103.753758637401);
    }

    @Test
    void getDistX() {
        assertEquals(v1.getDistX(), 0);
    }

    @Test
    void getDistY() {
        assertEquals(v2.getDistY(), 0);
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
        assertEquals(v1.getLatitude(), 0);
    }

    @Test
    void setLongitude() {
        v2.setLongitude(0);
        assertEquals(v2.getLongitude(), 0);
    }

    @Test
    void testToString() {
        assertEquals(v1.toString(), "YEW TEE INDUSTRIAL ESTATE | 1.3973210291170202 | 103.753758637401 | 0.0 | 0.0");
    }

    @Test
    void testEquals() {
        Venue v = new Venue("YEW TEE INDUSTRIAL ESTATE", 1.3973210291170202, 103.753758637401, 0, 0);
        assertTrue(v.equals(v1));
        assertFalse(v2.equals(v1));
    }
}

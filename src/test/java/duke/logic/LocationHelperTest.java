package duke.logic;

import duke.model.locations.Venue;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LocationHelperTest {

    @Test
    void checkDirection() {
        Venue v1 = new Venue("YEW TEE INDUSTRIAL ESTATE", 1.3973210291170202, 103.753758637401, 0, 0);
        Venue v2 = new Venue("Tuas Checkpoint", 1.34942405517095, 103.636127935782, 0, 0);
        assertTrue(LocationHelper.checkDirection(KeyCode.UP, v2, v1));
        assertTrue(LocationHelper.checkDirection(KeyCode.DOWN, v1, v2));
        assertTrue(LocationHelper.checkDirection(KeyCode.RIGHT, v2, v1));
        assertTrue(LocationHelper.checkDirection(KeyCode.LEFT, v1, v2));
    }
}

package duke.models.locker;

import duke.exceptions.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ZoneTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Zone(null));
    }

    @Test
    public void constructor_isOfInvalidType_throwsDukeException() {
        assertThrows(DukeException.class, () -> new Zone(""));
    }

    @Test
    public void checkIsValidZone() {
        //null and empty checks
        assertThrows(NullPointerException.class, () -> Zone.checkIsValidZone(null));
        assertFalse(Zone.checkIsValidZone(""));
        assertFalse(Zone.checkIsValidZone(" "));

        //invalid zone
        assertFalse(Zone.checkIsValidZone("1")); //contains non-alphabets
        assertFalse(Zone.checkIsValidZone(".")); //contains special characters
        assertFalse(Zone.checkIsValidZone("ab")); //contains more than 1 character
        assertFalse(Zone.checkIsValidZone(" a")); //trailing spaces
        assertFalse(Zone.checkIsValidZone("a ")); //leading spaces

        //valid zone
        assertTrue(Zone.checkIsValidZone("A"));
        assertTrue(Zone.checkIsValidZone("a"));
    }

    @Test
    public void checkForCaseInsensitivity() throws DukeException {
        Zone lowerCase = new Zone("a");
        Zone upperCase = new Zone("A");
        assertEquals(lowerCase,upperCase);
    }

}

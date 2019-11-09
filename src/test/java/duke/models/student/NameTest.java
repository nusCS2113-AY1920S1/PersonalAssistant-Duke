package duke.models.student;

import duke.exceptions.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidType_throwsDukeException() {
        assertThrows(DukeException.class, () -> new Name(" "));
    }

    @Test
    public void checkIsValidName() {
        //null and empty cases
        assertThrows(NullPointerException.class, () -> Name.checkIsValidName(null));
        assertFalse(Name.checkIsValidName(""));
        assertFalse(Name.checkIsValidName(" "));

        //invalid name
        assertFalse(Name.checkIsValidName("sponge_bob"));
        assertFalse(Name.checkIsValidName("!*"));
        assertFalse(Name.checkIsValidName(" sponge bob")); //leading spaces

        //valid names
        assertTrue(Name.checkIsValidName("sponge bob"));
        assertTrue(Name.checkIsValidName("12345689"));
        assertTrue(Name.checkIsValidName("sponge bob version 1"));
        assertTrue(Name.checkIsValidName("SpoNge BoB")); //mix casing
        assertTrue(Name.checkIsValidName("King Lear the 2nd"));
    }
}

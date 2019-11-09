package duke.models;

import duke.exceptions.DukeException;
import duke.models.tag.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidType_throwsDukeException() {
        assertThrows(DukeException.class, () -> new Tag(""));
    }

    @Test
    public void checkValidTagName() {

        //null or empty cases
        assertThrows(NullPointerException.class, () -> Tag.checkValidTagName(null));
        assertFalse(Tag.checkValidTagName(""));
        assertFalse(Tag.checkValidTagName(" "));

        //invalid tags
        assertFalse(Tag.checkValidTagName("123")); //numbers not allowed
        assertFalse(Tag.checkValidTagName("."));
        assertFalse(Tag.checkValidTagName(".-!"));
        assertFalse(Tag.checkValidTagName(" not-in-use")); //leading spaces
        assertFalse(Tag.checkValidTagName("broken ")); //trailing spaces

        //valid tags
        assertTrue(Tag.checkValidTagName(Tag.BROKEN));
        assertTrue(Tag.checkValidTagName(Tag.IN_USE));
        assertTrue(Tag.checkValidTagName(Tag.NOT_IN_USE));
        assertTrue(Tag.checkValidTagName(Tag.UNAUTHORIZED));
        assertTrue(Tag.checkValidTagName("BroKeN")); //case insensitive
    }
}

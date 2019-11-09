package duke.models.student;

import duke.exceptions.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MajorTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Major(null));
    }

    @Test
    public void constructor_invalidType_throwsDukeException() {
        assertThrows(DukeException.class, () -> new Major(" "));
    }

    @Test
    public void checkIsValidCourse() {
        //null and empty cases
        assertThrows(NullPointerException.class, () -> Major.checkIsValidCourse(null));
        assertFalse(Major.checkIsValidCourse(""));
        assertFalse(Major.checkIsValidCourse(" "));

        //invalid course
        assertFalse(Major.checkIsValidCourse("com-puter"));
        assertFalse(Major.checkIsValidCourse("!*"));
        assertFalse(Major.checkIsValidCourse(" computer")); //leading spaces

        //valid course
        assertTrue(Major.checkIsValidCourse("computer engineering"));
        assertTrue(Major.checkIsValidCourse("COMPUTER enginEEring"));
    }
}


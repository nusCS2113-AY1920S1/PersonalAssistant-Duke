package duke.models.student;

import duke.exceptions.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MatricNumberTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StudentId(null));
    }

    @Test
    public void constructor_invalidType_throwsDukeException() {
        assertThrows(DukeException.class, () -> new StudentId(" "));
    }

    @Test
    public void checkIsValidMatricNumber() {
        //null and empty parts
        assertThrows(NullPointerException.class, () -> StudentId.checkIsValidStudentId(null));
        assertFalse(StudentId.checkIsValidStudentId(""));
        assertFalse(StudentId.checkIsValidStudentId(" "));

        //invalid student id
        assertFalse(StudentId.checkIsValidStudentId("B0193621C")); //does not with "A"
        assertFalse(StudentId.checkIsValidStudentId("A0191C")); //length is too short
        assertFalse(StudentId.checkIsValidStudentId("A00000000001111B")); //length is too long
        assertFalse(StudentId.checkIsValidStudentId("A12345678")); //not ending with a letter
        assertFalse(StudentId.checkIsValidStudentId("A0_12345B")); //invalid characters
        assertFalse(StudentId.checkIsValidStudentId(" A1234567B")); //leading spaces
        assertFalse(StudentId.checkIsValidStudentId("A1234567B ")); //trailing spaces

        //valid student id
        assertTrue(StudentId.checkIsValidStudentId("A1234567B"));
        assertTrue(StudentId.checkIsValidStudentId("A3465691Z"));
    }
}

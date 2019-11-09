package duke.models.student;

import duke.exceptions.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MatricNumberTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MatricNumber(null));
    }

    @Test
    public void constructor_invalidType_throwsDukeException() {
        assertThrows(DukeException.class, () -> new MatricNumber(" "));
    }

    @Test
    public void checkIsValidMatricNumber() {
        //null and empty parts
        assertThrows(NullPointerException.class, () -> MatricNumber.checkIsValidMatricNumber(null));
        assertFalse(MatricNumber.checkIsValidMatricNumber(""));
        assertFalse(MatricNumber.checkIsValidMatricNumber(" "));

        //invalid student id
        assertFalse(MatricNumber.checkIsValidMatricNumber("B0193621C")); //does not with "A"
        assertFalse(MatricNumber.checkIsValidMatricNumber("A0191C")); //length is too short
        assertFalse(MatricNumber.checkIsValidMatricNumber("A00000000001111B")); //length is too long
        assertFalse(MatricNumber.checkIsValidMatricNumber("A12345678")); //not ending with a letter
        assertFalse(MatricNumber.checkIsValidMatricNumber("A0_12345B")); //invalid characters
        assertFalse(MatricNumber.checkIsValidMatricNumber(" A1234567B")); //leading spaces
        assertFalse(MatricNumber.checkIsValidMatricNumber("A1234567B ")); //trailing spaces

        //valid student id
        assertTrue(MatricNumber.checkIsValidMatricNumber("A1234567B"));
        assertTrue(MatricNumber.checkIsValidMatricNumber("A3465691Z"));
    }
}

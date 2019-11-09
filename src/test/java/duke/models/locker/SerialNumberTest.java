package duke.models.locker;

import duke.exceptions.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SerialNumberTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SerialNumber(null));
    }

    @Test
    public void constructor_invalidFormat_throwsDukeException() {
        assertThrows(DukeException.class, () -> new SerialNumber(""));
    }

    @Test
    public void checkIsValidSerialNumber() {
        //null and empty cases
        assertThrows(NullPointerException.class, () -> SerialNumber.checkIsValidSerialNumber(null));
        assertFalse(SerialNumber.checkIsValidSerialNumber(""));
        assertFalse(SerialNumber.checkIsValidSerialNumber(" "));

        //invalid serial number
        assertFalse(SerialNumber.checkIsValidSerialNumber("rubbish")); //non-numeric characters
        assertFalse(SerialNumber.checkIsValidSerialNumber("1234.3")); //non-integers
        assertFalse(SerialNumber.checkIsValidSerialNumber("-1")); //negative numbers
        assertFalse(SerialNumber.checkIsValidSerialNumber("12345678")); //more than 6 digits
        assertFalse(SerialNumber.checkIsValidSerialNumber(" 123")); //leading spaces
        assertFalse(SerialNumber.checkIsValidSerialNumber("123 ")); //trailing spaces

        //valid serial number
        assertTrue(SerialNumber.checkIsValidSerialNumber("1"));
        assertTrue(SerialNumber.checkIsValidSerialNumber("01")); //with leading 0s
        assertTrue(SerialNumber.checkIsValidSerialNumber("999999")); //maximum allowed
        assertTrue(SerialNumber.checkIsValidSerialNumber("0")); //0  allowed
    }

    @Test
    public void testForLeadingZeroes() throws DukeException {
        SerialNumber test = new SerialNumber("00123");
        SerialNumber removedLeading = new SerialNumber("123");
        SerialNumber addTrailing = new SerialNumber("1230");
        assertEquals(test,removedLeading);
        assertNotEquals(removedLeading,addTrailing);
    }
}

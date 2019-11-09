package duke.models.student;

import duke.exceptions.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmailTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Email(null));
    }

    @Test
    public void constructor_invalidFormat_throwsDukeException() {
        assertThrows(DukeException.class, () -> new Email(""));
    }

    @Test
    public void checkIsValidEmail() {
        //null and empty cases
        assertThrows(NullPointerException.class, () -> Email.checkIsValidEmail(null));
        assertFalse(Email.checkIsValidEmail(""));
        assertFalse(Email.checkIsValidEmail(" "));

        //missing parts
        assertFalse(Email.checkIsValidEmail("@onlydomain.com"));
        assertFalse(Email.checkIsValidEmail("missingsymbol.com"));
        assertFalse(Email.checkIsValidEmail("missingdomain@"));

        //valid email
        assertTrue(Email.checkIsValidEmail("spongeBob@lockers.com"));
        assertTrue(Email.checkIsValidEmail("sponge_!Bob@lockers.com"));
        assertTrue(Email.checkIsValidEmail("2345@12"));
        assertTrue(Email.checkIsValidEmail("!#$%&'*+/=?`{|}~^.-@allspecialchars.com"));
        assertTrue(Email.checkIsValidEmail("a_very_long_name@with-very-long-domain-name.com"));

        //invalid email
        assertFalse(Email.checkIsValidEmail("spongebob@_invalid")); //underscore in domain name
        assertFalse(Email.checkIsValidEmail("sponge bob@lockers.com")); //spaces in local part
        assertFalse(Email.checkIsValidEmail("spongebob@loc kers.com")); //spaces in domain name
        assertFalse(Email.checkIsValidEmail("spong@bob@lockers.com")); //two @s
        assertFalse(Email.checkIsValidEmail("spongebob@@lockers.com"));
        assertFalse(Email.checkIsValidEmail(" spongebob@lockers.com")); //trailing spaces
        assertFalse(Email.checkIsValidEmail("spongebob@lockers.com ")); //ending spaces
        assertFalse(Email.checkIsValidEmail("spongebob@-invalid.com"));
        assertFalse(Email.checkIsValidEmail("spongebob@invalid.com."));
    }
}

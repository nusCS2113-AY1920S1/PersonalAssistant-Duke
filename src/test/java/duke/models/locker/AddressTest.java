package duke.models.locker;

import duke.exceptions.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddressTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Address(null));
    }

    @Test
    public void constructor_invalidFormat_throwsDukeException() {
        assertThrows(DukeException.class, () -> new Address(""));
    }

    @Test
    public void checkIsValidAddress() {
        //null and empty cases
        assertThrows(NullPointerException.class, () -> Address.checkIsValidAddress(null));
        assertFalse(Address.checkIsValidAddress(""));
        assertFalse(Address.checkIsValidAddress(" "));

        //valid address
        assertTrue(Address.checkIsValidAddress("Com-1"));
        assertTrue(Address.checkIsValidAddress("-"));
        assertTrue(Address.checkIsValidAddress("Com-1 Level 2 02-17."));
        assertTrue(Address.checkIsValidAddress("extremely _long add!ress .with / special, characters"));
    }
}

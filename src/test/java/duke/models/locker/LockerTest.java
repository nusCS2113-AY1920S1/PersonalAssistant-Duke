package duke.models.locker;

import duke.exceptions.DukeException;
import duke.models.tag.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LockerTest {

    private SerialNumber sampleSerialNumber;
    private Zone sampleZone;
    private Address sampleAddress;
    private Usage sampleUsage;
    private Tag sampleTag;
    private Locker sampleLocker;

    private LockerTest() throws DukeException {
        sampleSerialNumber = new SerialNumber("123");
        sampleZone = new Zone("A");
        sampleAddress = new Address("com-1");
        sampleTag = new Tag(Tag.NOT_IN_USE);
        sampleUsage = null;
        sampleLocker = new Locker(sampleSerialNumber,sampleAddress,sampleZone,sampleTag,sampleUsage);
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Locker(
                null,null,null,null, null));
        assertThrows(NullPointerException.class, () -> new Locker(null,sampleAddress,sampleZone,
                null,null));
    }

    @Test
    public void isOfTypeInUse() throws DukeException {
        assertFalse(sampleLocker.isOfTypeInUse());
        Locker newTest = new Locker(sampleSerialNumber,sampleAddress,sampleZone,new Tag(Tag.BROKEN),sampleUsage);
        assertFalse(newTest.isOfTypeInUse());
        newTest = new Locker(sampleSerialNumber,sampleAddress,sampleZone,new Tag(Tag.UNAUTHORIZED),sampleUsage);
        assertFalse(newTest.isOfTypeInUse());
    }

    @Test
    public void isOfInvalidType() throws DukeException {
        Locker newTest = new Locker(sampleSerialNumber,sampleAddress,sampleZone, new Tag(Tag.IN_USE),sampleUsage);
        assertTrue(newTest.isOfInValidType());
        assertFalse(sampleLocker.isOfInValidType());
    }

    @Test
    public void hasSameTagAs() throws DukeException {
        Tag typeInUse = new Tag(Tag.IN_USE);
        assertFalse(sampleLocker.hasSameTagAs(typeInUse));

        Tag typeUnauthorised = new Tag(Tag.UNAUTHORIZED);
        assertFalse(sampleLocker.hasSameTagAs(typeUnauthorised));

        Tag typeBroken = new Tag(Tag.BROKEN);
        assertFalse(sampleLocker.hasSameTagAs(typeBroken));

        Tag typeNotInUse = new Tag(Tag.NOT_IN_USE);
        assertTrue(sampleLocker.hasSameTagAs(typeNotInUse));

    }

    @Test
    public void hasSameSerialNumber() throws DukeException {
        Locker testLocker = new Locker(new SerialNumber("0123"),sampleAddress,sampleZone,sampleTag,sampleUsage);
        assertTrue(sampleLocker.hasSameSerialNumber(testLocker));

        testLocker = new Locker(new SerialNumber("123"),sampleAddress, new Zone("Z"), sampleTag, sampleUsage);
        assertTrue(sampleLocker.hasSameSerialNumber(testLocker));

        testLocker = new Locker(new SerialNumber("1230"),sampleAddress,sampleZone,sampleTag,sampleUsage);
        assertFalse(sampleLocker.hasSameSerialNumber(testLocker));

        assertFalse(sampleLocker.hasSameSerialNumber(null));
    }

    @Test
    public void isEquals() throws DukeException {

        assertFalse(sampleLocker.equals(null));

        Locker otherLocker = new Locker(sampleSerialNumber, sampleAddress, sampleZone,
                new Tag("broken"),sampleUsage);

        //check for equality with tag changed
        assertFalse(sampleLocker.equals(otherLocker));

        otherLocker = new Locker(new SerialNumber("12345"), sampleAddress, sampleZone, sampleTag, sampleUsage);
        //check for equality with serial number changed
        assertFalse(sampleLocker.equals(otherLocker));

        //check for equality with address changed
        otherLocker = new Locker(sampleSerialNumber,new Address("com-2 level-1"), sampleZone, sampleTag, sampleUsage);
        assertFalse(sampleLocker.equals(otherLocker));

        //check for equality with zone changed
        otherLocker = new Locker(sampleSerialNumber, sampleAddress, new Zone("Q"), sampleTag, sampleUsage);
        assertFalse(sampleLocker.equals(otherLocker));

        //check for valid output
        otherLocker = new Locker(sampleSerialNumber, sampleAddress, sampleZone, sampleTag, sampleUsage);
        assertTrue(sampleLocker.equals(otherLocker));

    }
}
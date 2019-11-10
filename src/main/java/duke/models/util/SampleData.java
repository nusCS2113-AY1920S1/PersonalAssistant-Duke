package duke.models.util;

import duke.exceptions.DukeException;
import duke.models.LockerList;
import duke.models.locker.Address;
import duke.models.locker.Locker;
import duke.models.locker.SerialNumber;
import duke.models.locker.Zone;
import duke.models.tag.Tag;

/**
 * Contains methods for populating SpongeBob with sample data.
 */
public class SampleData {

    private static Locker[] getSampleLockers() throws DukeException {
        return new Locker[]{
            new Locker(new SerialNumber("1"), new Address("Com-1"), new Zone("A"),
                       new Tag(Tag.NOT_IN_USE), null),
            new Locker(new SerialNumber(("2")), new Address("Com-1"), new Zone("A"),
                       new Tag(Tag.NOT_IN_USE), null),
            new Locker(new SerialNumber(("3")), new Address("Com-1"), new Zone("A"),
                       new Tag(Tag.NOT_IN_USE), null),
            new Locker(new SerialNumber("4"), new Address("Com-1"), new Zone("A"),
                       new Tag(Tag.UNAUTHORIZED), null),
            new Locker(new SerialNumber("5"), new Address("Com-1"), new Zone("A"),
                       new Tag(Tag.BROKEN), null),
            new Locker(new SerialNumber("6"), new Address("Com-2"), new Zone("B"),
                       new Tag(Tag.NOT_IN_USE), null),
            new Locker(new SerialNumber("7"), new Address("Com-2"), new Zone("B"),
                       new Tag(Tag.UNAUTHORIZED), null),
            new Locker(new SerialNumber("8"), new Address("Com-2"), new Zone("C"),
                       new Tag(Tag.BROKEN), null),
            new Locker(new SerialNumber("9"), new Address("Com-2"), new Zone("C"),
                       new Tag(Tag.NOT_IN_USE), null),
            new Locker(new SerialNumber("10"), new Address("Com-2"), new Zone("C"),
                       new Tag(Tag.NOT_IN_USE), null)
        };
    }

    /**
     * Returns the sample list of lockers.
     */
    public static LockerList getSampleLockerList() throws DukeException {
        LockerList sampleList = new LockerList();
        for (Locker sampleLocker : getSampleLockers()) {
            sampleList.addLocker(sampleLocker);
        }
        return sampleList;
    }
}
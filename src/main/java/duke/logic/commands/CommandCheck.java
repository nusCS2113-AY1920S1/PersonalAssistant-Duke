package duke.logic.commands;

import duke.exceptions.DukeException;
import duke.models.LockerList;
import duke.models.locker.Locker;
import duke.models.locker.SerialNumber;
import duke.models.tag.Tag;

import java.util.List;
import java.util.function.Predicate;

public class CommandCheck {
    private static final int GET_FIRST_INDEX = 0;

    public static List<Locker> findLockersInAnyZone(LockerList lockerList) throws DukeException {
        Tag checkAvailableTag = new Tag(Tag.NOT_IN_USE);
        return lockerList.getMatchingLockers(findLocker());
    }

    /**
     * This function is ued to check if the given locker is already present in the lockerlist.
     * @param serialNumber stroes the serial number of the locker to be checked
     * @param lockerList stores the lockerlist
     * @return true if the user the locker is not present
     * @throws DukeException if the locker is already present
     */
    public static boolean isAlreadyPresent(SerialNumber serialNumber,LockerList lockerList) throws DukeException {
        if ((lockerList.getMatchingLockers(p -> p.getSerialNumber().equals(serialNumber)))
                 .size() > 0) {
            throw new DukeException(" Duplicate entries are not allowed. All serial numbers"
                    + " must be unique");
        }
        return true;
    }

    private static Predicate<Locker> findLocker() throws DukeException {
        Tag checkTag = new Tag(Tag.NOT_IN_USE);
        return p -> p.getTag().equals(checkTag);
    }

    /**
     * This function is used to return the locker details for a given serial number.
     * @param lockerList stores the current state of the locker list
     * @param serialNumber stores the serial number of the locker whose properties are to
     *                    be extracted
     * @return the locker details
     * @throws DukeException when there is no match for the serial number.
     */
    public static Locker getLockerToEdit(LockerList lockerList,
                                         SerialNumber serialNumber) throws DukeException {
        List<Locker> listOfLockers;
        listOfLockers = lockerList.getMatchingLockers(isMatchingSerialNumber(serialNumber));
        if (listOfLockers.size() == 0) {
            throw new DukeException(" There are no lockers associated to the serial number entered");
        }
        return listOfLockers.get(GET_FIRST_INDEX);

    }

    private static Predicate<Locker> isMatchingSerialNumber(SerialNumber serialNumber) {
        return p -> p.getSerialNumber().equals(serialNumber);
    }
}

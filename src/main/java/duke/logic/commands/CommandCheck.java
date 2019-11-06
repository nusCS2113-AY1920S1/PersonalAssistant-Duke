package duke.logic.commands;

import duke.exceptions.DukeException;
import duke.models.LockerList;
import duke.models.locker.SerialNumber;


public class CommandCheck {

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
                    + " must be unique.");
        }
        return true;
    }
}

package duke.models;

import duke.exceptions.DukeException;
import duke.models.locker.Locker;
import duke.models.locker.LockerDate;
import duke.models.locker.Usage;
import duke.models.tag.Tag;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.List;

public class ModelChecks {

    private static final String DATE_FORMAT = "dd-MM-uuuu";

    /**
     * checks whether all entries are valid in the lockerList.
     * @param lockerList stores the entire LockerList to be checked
     * @return true if the entries are valid
     * @throws DukeException caused by chaining of functions
     */
    public static boolean areAllEntriesValid(LockerList lockerList) throws DukeException {
        List<Locker> lockersToCheck = lockerList.getAllLockers();
        for (int i = 0; i < lockersToCheck.size(); i++) {
            Locker lockerToCheck = lockersToCheck.get(i);
            lockerList.deleteLocker(lockerToCheck);
            if (lockerList.isPresentLocker(lockerToCheck) || lockerToCheck.isOfInValidType()) {
                return false;
            }
            if (lockerToCheck.isOfTypeInUse() && !areChecksForUsageValid(lockerToCheck)) {
                return false;
            }
            lockerToCheck = getLockerToAdd(lockerToCheck);
            lockerList.addLockerAtPosition(lockerToCheck, i);
        }
        return true;
    }

    private static boolean areChecksForUsageValid(Locker checkUsage) {
        assert checkUsage.getUsage().isPresent();
        Usage checkForValidity = checkUsage.getUsage().get();
        return LockerDate.isDifferenceBetweenDatesValid(checkForValidity.getStartDate().getDate(),
                checkForValidity.getEndDate().getDate());
    }

    private static Locker getLockerToAdd(Locker locker) throws DukeException {
        if (locker.isOfTypeInUse()) {
            assert locker.getUsage().isPresent();
            Usage usage = locker.getUsage().get();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT)
                    .withResolverStyle(ResolverStyle.STRICT);
            LocalDate localDate = LocalDate.now();
            if (LockerDate.isEndDateBeforeCurrentDate(
                    usage.getEndDate().getDate(), formatter.format(localDate))) {
                return new Locker(locker.getSerialNumber(), locker.getAddress(), locker.getZone(),
                        new Tag(Tag.NOT_IN_USE), null);
            }
        }
        return locker;
    }
}

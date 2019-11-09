package duke.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import duke.exceptions.DukeException;
import duke.models.locker.Locker;
import duke.models.locker.SerialNumber;
import duke.models.tag.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class LockerList {

    private static final String NO_LOCKER_FOUND = " There are no lockers associated to "
            + "the serial number entered";
    public static final String DUPLICATE_LOCKERS_FOUND = " Duplicate entries not allowed. "
            + "Serial number for every locker should be unique";
    private static final int EMPTY_LIST = 0;

    private List<Locker> lockerList;

    public LockerList(List<Locker> lockerList) {
        requireNonNull(lockerList);
        this.lockerList = lockerList;
    }

    public LockerList() {
        lockerList = new ArrayList<>();
    }

    /**
     * Used to check if the locker is already present in the list.
     * @param newLocker list of lockers to be checked
     * @return true if at least one of the locker is present
     */
    public boolean isPresentLocker(Locker newLocker) {
        requireNonNull(newLocker);
        return lockerList.stream()
                .anyMatch(locker -> locker.hasSameSerialNumber(newLocker));
    }

    /**
     * Used to check if the lockers are already present in the list.
     * @param newLockers list of lockers to be checked
     * @return true if atleast one of the locker is present
     */
    public boolean areLockersPresent(List<Locker> newLockers) {
        requireNonNull(newLockers);
        for (Locker newLocker: newLockers) {
            if (isPresentLocker(newLocker)) {
                return true;
            }
        }
        return false;
    }

    /**
     * returns all the lockers that match a given property.
     * @param isMatching stores the predicate for matching
     * @return list of lockers that match the given predicate
     */
    public List<Locker> getMatchingLockers(Predicate<Locker> isMatching) {
        requireNonNull(isMatching);
        return lockerList.stream()
                .filter(isMatching)
                .collect(Collectors.toList());
    }

    /**
     * returns a list of lockers that are tagged with availableTag.
     * @param availableTag tag used to check if the locker is currently not-in-use
     * @return list of available lockers
     */
    public List<Locker> getAnyAvailableLocker(Tag availableTag) {
        requireNonNull(availableTag);
        return lockerList.stream()
                .filter(locker -> locker.getTag().equals(availableTag))
                .collect(Collectors.toList());
    }

    /**
     * returns a locker that is associated with the serialNumber.
     * @param serialNumberToFind stores the serial number
     * @return locker with the given serial number
     * @throws DukeException if there are no lockers associated with the serial number
     */
    public Locker getLockerToEdit(SerialNumber serialNumberToFind) throws DukeException {
        requireNonNull(serialNumberToFind);
        List<Locker> checkAllLockers = lockerList.stream()
                .filter(locker -> locker.getSerialNumber().equals(serialNumberToFind))
                .collect(Collectors.toList());
        if (checkAllLockers.size() == EMPTY_LIST) {
            throw new DukeException(NO_LOCKER_FOUND);
        }
        return checkAllLockers.get(0);
    }

    public void addLocker(Locker locker) {
        requireNonNull(locker);
        lockerList.add(locker);
    }

    public void addLockerAtPosition(Locker locker, int index) {
        requireNonNull(locker);
        lockerList.add(index, locker);
    }

    public void setLockerInPosition(Locker locker, int index) {
        requireNonNull(locker);
        lockerList.set(index, locker);
    }

    public void addAllLockersInList(List<Locker> lockers) {
        requireNonNull(lockers);
        lockerList.addAll(lockers);
    }

    public void deleteLocker(Locker locker) {
        requireNonNull(locker);
        lockerList.remove(locker);
    }

    public Locker getLocker(int index) {
        return lockerList.get(index);
    }

    public int getIndexOfLocker(Locker locker) {
        requireNonNull(locker);
        return lockerList.indexOf(locker);
    }

    public int numLockers() {
        return lockerList.size();
    }

    public List<Locker> getAllLockers() {
        return lockerList;
    }

    @JsonGetter("lockers")
    public List<Locker> getLockerList() {
        return lockerList;
    }

    @JsonSetter("lockers")
    public void setLLockerList(List<Locker> lockerList) {
        this.lockerList = lockerList;
    }

}

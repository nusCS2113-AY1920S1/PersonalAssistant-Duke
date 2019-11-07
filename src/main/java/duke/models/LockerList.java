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

    private List<Locker> lockerList;

    public LockerList(List<Locker> lockerList) {
        requireNonNull(lockerList);
        this.lockerList = lockerList;
    }

    public LockerList() {
        lockerList = new ArrayList<>();
    }

    public boolean isPresentLocker(Locker newLocker) {
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
        return lockerList.stream()
                .filter(locker -> locker.getTag().equals(availableTag))
                .collect(Collectors.toList());
    }

    /**
     * returns a locker that is associated with the serialNumber.
     * @param serialNumber stores the serial number
     * @return locker with the given serial number
     * @throws DukeException if there are no lockers associated with the serial number
     */
    public Locker getLockerToEdit(SerialNumber serialNumber) throws DukeException {
        List<Locker> checkAllLockers = lockerList.stream()
                .filter(locker -> locker.getSerialNumber().equals(serialNumber))
                .collect(Collectors.toList());
        if (checkAllLockers.size() == 0) {
            throw new DukeException(" There are no lockers associated to the serial number entered");
        }
        return checkAllLockers.get(0);
    }

    public void addLocker(Locker locker) {
        lockerList.add(locker);
    }

    public void addLockerAtPosition(Locker locker, int index) {
        lockerList.add(index,locker);
    }

    public void setLockerInPosition(Locker locker, int index) {
        lockerList.set(index,locker);
    }

    public void addAllLockersInList(List<Locker> lockers) {
        lockerList.addAll(lockers);
    }

    public void deleteLocker(Locker locker) {
        lockerList.remove(locker);
    }

    public Locker getLocker(int index) {
        return lockerList.get(index);
    }

    public int getIndexOfLocker(Locker locker) {
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

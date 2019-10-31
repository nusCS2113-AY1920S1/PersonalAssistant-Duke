package duke.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import duke.models.locker.Locker;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class LockerList {

    private List<Locker> lockerList;

    public LockerList(List<Locker> lockerList) {
        this.lockerList = lockerList;
    }

    public LockerList() {
        lockerList = new ArrayList<>();
    }

    public boolean isPresentLocker(Locker newLocker) {
        return lockerList.stream()
                .anyMatch(locker -> locker.isPresent(newLocker));
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

    public void addLocker(Locker locker) {
        lockerList.add(locker);
    }

    public void addLockerInPosition(Locker locker, int index) {
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

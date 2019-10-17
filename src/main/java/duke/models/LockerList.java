package duke.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.ArrayList;
import java.util.List;

public class LockerList {

    private List<Locker> lockerList;

    public LockerList(List<Locker> lockerList) {
        this.lockerList = lockerList;
    }

    public LockerList() {
        lockerList = new ArrayList<>();
    }

    public void addLocker(Locker locker) {
        lockerList.add(locker);
    }

    public void deleteLocker(Locker locker) {
        lockerList.remove(locker);
    }

    public Locker getLocker(int index) {
        return lockerList.get(index);
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

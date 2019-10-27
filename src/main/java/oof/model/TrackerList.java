package oof.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TrackerList {
    protected List<Tracker> trackerList;

    /**
     * Constructor for trackerList.
     *
     * @param trackerList trackerList that contains Tracker objects.
     */
    public TrackerList(List<Tracker> trackerList) {
        this.trackerList = trackerList;
    }

    /**
     * Constructor for trackerList.
     */
    public TrackerList() {
        this.trackerList = new ArrayList<>();
    }

    /**
     * Retrieves all the Tracker objects in trackerList.
     *
     * @return trackerList containing all its Tracker objects.
     */
    public List<Tracker> getTrackers() {
        return trackerList;
    }

    /**
     * Retrieves the number of Tracker objects in the trackerList.
     *
     * @return Number of Tracker objects in the trackerList.
     */
    public int getSize() {
        return trackerList.size();
    }

    /**
     * Retrieves the Tracker object at a particular index.
     *
     * @param index Index of Tracker object, specified by the user.
     * @return Tracker object at a particular index.
     */
    public Tracker getTracker(int index) {
        return trackerList.get(index);
    }

    /**
     * Adds a Tracker object to the trackerList.
     *
     * @param tracker Tracker object to be added to trackerList.
     */
    public void addTracker(Tracker tracker) {
        trackerList.add(tracker);
    }

    /**
     * Adds a Tracker object to a specific index in the trackerList.
     *
     * @param index Index to be inserted.
     * @param tracker  Tracker to be added.
     */
    public void addTrackerToIndex(int index, Tracker tracker) {
        trackerList.add(index, tracker);
    }

    /**
     * Deletes a Tracker object from the trackerList.
     *
     * @param index Index of Tracker object, specified by the user.
     */
    public void deleteTracker(int index) {
        trackerList.remove(index);
    }

    /**
     * Checks if index is within bounds of trackerList.
     *
     * @param index Index of trackerList.
     * @return True if index is within bounds of trackerList, false otherwise.
     */
    public boolean isIndexValid(int index) {
        return index < this.getSize() && index >= 0;
    }

    /**
     * Compare time taken property between two Tracker objects.
     */
    public static Comparator<Tracker> timeTaken = new Comparator<>() {

        /**
         * Compare time taken property between two Tracker objects.
         * @param t1    Tracker object.
         * @param t2    Tracker object.
         * @return      difference between time taken property between two Tracker objects.
         */
        public int compare(Tracker t1, Tracker t2) {
            int timeTaken1 = (int) t1.getTotalTimeTaken();
            int timeTaken2 = (int) t2.getTotalTimeTaken();
            return timeTaken1 - timeTaken2;
        }
    };
}

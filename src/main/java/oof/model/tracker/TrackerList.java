package oof.model.tracker;

import java.util.ArrayList;
import java.util.Date;
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
     * Find Tracker object in TrackerList where descriptions match.
     *
     * @return Tracker object that matches user given description.
     */
    public Tracker findTrackerByDesc(String description) {
        for (int i = 0; i < getSize(); i++) {
            Tracker tracker = getTracker(i);
            String currentDesc = tracker.getDescription();
            if (description.equals(currentDesc)) {
                return tracker;
            }
        }
        return null;
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
     * Checks if index is within bounds of trackerList.
     *
     * @param index Index of trackerList.
     * @return True if index is within bounds of trackerList, false otherwise.
     */
    public boolean isIndexValid(int index) {
        return index < this.getSize() && index >= 0;
    }

    /**
     * Check if trackerList is empty.
     * @return  true if trackerList is empty.
     */
    public boolean isEmpty() {
        return trackerList.isEmpty();
    }
}

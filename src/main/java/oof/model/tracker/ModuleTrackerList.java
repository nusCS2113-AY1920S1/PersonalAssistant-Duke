package oof.model.tracker;

import java.util.ArrayList;
import java.util.Comparator;

public class ModuleTrackerList {

    private ArrayList<ModuleTracker> moduleTrackerList = new ArrayList<>();

    /**
     * Constructor for ModuleTrackerList.
     */
    public ModuleTrackerList() {

    }

    /**
     * Constructor for ModuleTrackerList.
     * @param moduleTrackerList     ArrayList of ModuleTracker objects.
     */
    public ModuleTrackerList(ArrayList<ModuleTracker> moduleTrackerList) {
        this.moduleTrackerList = moduleTrackerList;
    }

    /**
     * Compare time taken property between two ModuleTracker objects.
     */
    public static Comparator<ModuleTracker> timeTaken = new Comparator<>() {

        /**
         * Compare time taken property between two ModuleTracker objects.
         * @param mt1    ModuleTracker object.
         * @param mt2    ModuleTracker object.
         * @return      difference between time taken property between two ModuleTracker objects.
         */
        public int compare(ModuleTracker mt1, ModuleTracker mt2) {
            int timeTaken1 = (int) mt1.getTimeTaken();
            int timeTaken2 = (int) mt2.getTimeTaken();
            return timeTaken1 - timeTaken2;
        }
    };


    /**
     * Retrieves the number of Tracker objects in the trackerList.
     *
     * @return Number of Tracker objects in the trackerList.
     */
    public int getSize() {
        return moduleTrackerList.size();
    }

    /**
     * Retrieves the ModuleTracker object at a particular index.
     *
     * @param index Index of ModuleTracker object, specified by the user.
     * @return ModuleTracker object at a particular index.
     */
    public ModuleTracker getModuleTracker(int index) {
        return moduleTrackerList.get(index);
    }

    /**
     * Adds a ModuleTracker object to the moduleTrackerList.
     *
     * @param moduleTracker moduleTracker object to be added to moduleTrackerList.
     */
    public void addModuleTracker(ModuleTracker moduleTracker) {
        moduleTrackerList.add(moduleTracker);
    }

    /**
     * Adds a Tracker object to a specific index in the trackerList.
     *
     * @param index Index to be inserted.
     * @param moduleTracker  Tracker to be added.
     */
    public void addTrackerToIndex(int index, ModuleTracker moduleTracker) {
        moduleTrackerList.add(index, moduleTracker);
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
}

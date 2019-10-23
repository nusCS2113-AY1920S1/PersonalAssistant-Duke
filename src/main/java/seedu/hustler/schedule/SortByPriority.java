package seedu.hustler.schedule;

import java.util.Comparator;

/**
 * A comparator to sort the schedule based on priority.
 */
public class SortByPriority implements Comparator<ScheduleEntry> {

    /**
     * Compares two entries based on priority.
     *
     * @param entryA first entry to compare with
     * @param entryB second entry to compare with
     * @return an integer based on the comparison {1, 0, -1}
     */
    public int compare(ScheduleEntry entryA, ScheduleEntry entryB) {
        if (entryA.getPriorityScore() < entryB.getPriorityScore()) {
            return 1; 
        } else if (entryA.getPriorityScore() == entryB.getPriorityScore()) {
            return 0;
        } else {
            return -1;
        }
    } 
}

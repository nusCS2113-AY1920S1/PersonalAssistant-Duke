package Operations;

import Model_Classes.Deadline;
import Model_Classes.Event;
import Model_Classes.FixedDuration;
import Model_Classes.Task;

import java.util.ArrayList;
import java.util.Date;

/**
 * This class checks if there are clashes in timings for events
 */
public class CheckAnomaly {
    /**
     * Checks if the FixedDuration task has any clashes with any other tasks in the task list
     * If there is a clash, returns true
     * If there is no clash, returns false
     * @param currEvent FixedDuration being checked for time clashes
     * @return true if there are time clashes, false if there are no time clashes
     */
    public static Boolean checkTime(FixedDuration currEvent) {
        double currDuration;
        currDuration = currEvent.getDuration() * 3600000;
        ArrayList<Task> curr = TaskList.currentList();
        for( int i = 0; i<TaskList.currentList().size(); i++ ) {
            Date checkDate = currEvent.checkDate();
            if( curr.get(i) instanceof FixedDuration ) {
                double duration;
                duration = ((FixedDuration) curr.get(i)).getDuration() * 3600000;
                Date listDate = ((FixedDuration) curr.get(i)).checkDate();
                if ( listDate.getYear() == checkDate.getYear() && listDate.getMonth() == checkDate.getMonth() && listDate.getDay() == checkDate.getDay() ) {
                    double checkTime = listDate.getTime();
                    double currTime = checkDate.getTime();
                    if( checkTime <= currTime + currDuration && checkTime >= currTime || checkTime + duration <= currTime + currDuration && checkTime + duration >= currTime ) {
                        return false;
                    }
                }
            } else if( curr.get(i) instanceof Event ) {
                Date listDate = ((Event) curr.get(i)).checkDate();
                // Checks if both events are on the same day
                if ( listDate.getYear() == checkDate.getYear() && listDate.getMonth() == checkDate.getMonth() && listDate.getDay() == checkDate.getDay() ) {
                    double checkTime = listDate.getTime();
                    double currTime = checkDate.getTime();
                    if( checkTime <= currTime + currDuration && checkTime >= currTime ) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Overload function for checkTime
     * Checks if the FixedDuration task has any clashes with any other tasks in the task list
     * If there is a clash, returns true
     * If there is no clash, returns false
     * @param at Date of the event we are checking
     * @return true if there are time clashes, false if there are no time clashes
     */
    public static Boolean checkTime(Date at){
        ArrayList<Task> curr = TaskList.currentList();
        for( int i = 0; i<TaskList.currentList().size(); i++ ) {
            if( curr.get(i) instanceof Event && ((Event) curr.get(i)).checkDate().equals(at) ) {
                return true;
            } else if( curr.get(i) instanceof FixedDuration ) {
                return true;
            }
        }
        return false;
    }
}

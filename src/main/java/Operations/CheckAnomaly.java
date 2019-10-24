package Operations;

import Enums.TimeUnit;
import Model_Classes.Meeting;
import Model_Classes.Task;

import java.util.ArrayList;
import java.util.Date;

/**
 * This class checks if there are clashes in timings for meetings
 */
public class CheckAnomaly {
    /**
     * Checks first if the task is a meeting, then decides which check function to use depending on whether the meeting has a fixed duration
     * @param task task we are checking
     * @return true if there is a time clash, false if there is no clash.
     */
    public static Boolean checkTask(Task task) {
        if( task instanceof Meeting ) {
            if( ((Meeting) task).isFixedDuration() ) {
                return checkTimeDuration((Meeting) task);
            } else {
                return checkTime((Meeting) task);
            }
        }
        return false;
    }

    /**
     * Checks if the Meeting with fixed duration task has any clashes with any other meetings in the task list.
     * If there is a clash, returns true.
     * If there is no clash, returns false.
     * @param task task we are checking
     * @return true if there are time clashes, false if there are no time clashes.
     */
    private static Boolean checkTimeDuration(Task task) {
        ArrayList<Task> curr = TaskList.currentList();
        for( int i = 0; i<TaskList.currentList().size(); i++ ) {
            if( curr.get(i) instanceof Meeting  ) {
                if(  ((Meeting) curr.get(i)).isFixedDuration() && checkOverlap(((Meeting) curr.get(i)), task) ) {
                    return true;
                } else if( !(((Meeting) curr.get(i)).isFixedDuration()) && checkIntersect( curr.get(i).getDate(), task) ) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if the Meeting with no fixed duration has any clashes with any other tasks in the task list.
     * If there is a clash, returns true. If there is no clash, returns false.
     * @param task task we are checking for time clashes
     * @return true if there are time clashes, false if there are no time clashes.
     */
    private static Boolean checkTime(Task task){
        Date at = task.getDate();
        ArrayList<Task> curr = TaskList.currentList();
        // Goes down list of Tasks
        for( int i = 0; i<TaskList.currentList().size(); i++ ) {
            // If task is a meeting, checks if it has a fixed duration
            if ( curr.get(i) instanceof Meeting ) {
                if( ((Meeting) curr.get(i)).isFixedDuration() ) {
                    if( checkIntersect(at, (Meeting) curr.get(i)) ) {
                        return true;
                    }
                } else if( curr.get(i).getDate().equals(at) ) {
                    return true;
                }
            } //else if( curr.get(i) instanceof Meeting && ((Meeting) curr.get(i)).isFixedDuration() ) {

           //}
        }
        return false;
    }

    /**
     * Checks if a timing clashes with the duration of another meeting.
     * @param time Timing we are checking.
     * @param task task we are checking.
     * @return True if the two timings clash and False if there is no clash.
     */
    private static Boolean checkIntersect(Date time, Task task) {
        Date rangeTime = task.getDate();
        if( rangeTime.getYear() == time.getYear() && rangeTime.getMonth() == time.getMonth() && rangeTime.getDay() == time.getDay() ) {
            long meetingTime = task.getDate().getTime();
            long currTime = time.getTime();
            long duration;
            if( task instanceof Meeting ) {
                duration = timeToMilSeconds(Long.parseLong(((Meeting) task).getDuration()), ((Meeting) task).getTimeUnit());
            } else {
                // task is a Leave
                duration = timeToMilSeconds(Long.parseLong(((Meeting) task).getDuration()), ((Meeting) task).getTimeUnit());
            }
            if(currTime < meetingTime + duration && currTime >= meetingTime) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the timings of two Meetings overlap.
     * @param first First task input.
     * @param second Second task input.
     * @return True if there is an overlap and false if there is no overlap.
     */
    private static Boolean checkOverlap(Task first, Task second) {
        Date date1 = first.getDate();
        Date date2 = second.getDate();
        if( date1.getYear() == date2.getYear() && date1.getMonth() == date2.getMonth() && date1.getDay() == date2.getDay() ) {
            long duration1;
            long duration2;
            if( first instanceof Meeting ) {
                duration1 = timeToMilSeconds(Integer.parseInt(((Meeting) first).getDuration()), ((Meeting) first).getTimeUnit());
            } else {
                // task is a leave
                duration1 = timeToMilSeconds(Integer.parseInt(((Meeting) first).getDuration()), ((Meeting) first).getTimeUnit());
            }
            if( second instanceof Meeting ) {
                duration2 = timeToMilSeconds(Integer.parseInt(((Meeting) first).getDuration()), ((Meeting) first).getTimeUnit());
            } else {
                // task is a leave
                duration2 = timeToMilSeconds(Integer.parseInt(((Meeting) first).getDuration()), ((Meeting) first).getTimeUnit());
            }
            long time1 = date1.getTime();
            long time2 = date2.getTime();
            if( (time1 < time2 + duration2 && time1 >= time2) || (time2 < time1 + duration1 && time2 >= time1) ) {
                return true;
            }
        }
        return false;
    }

    /**
     * Converts time to milliseconds.
     * @param duration duration of the Meeting.
     * @param unit unit the duration of the Meeting is in.
     * @return duration of Meeting in milliseconds.
     */
    private static long timeToMilSeconds(long duration, TimeUnit unit) {
        switch (unit) {
            case day:
                return duration * 60 * 60 * 24 * 1000;
            case hours:
                return duration * 60 * 60 * 1000;
            case minutes:
                return  duration * 60 * 1000;
            default:
                return duration;
        }
    }
}

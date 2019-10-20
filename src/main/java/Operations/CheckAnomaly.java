package Operations;

import Enums.TimeUnit;
import Model_Classes.Assignment;
import Model_Classes.FixedDuration;
import Model_Classes.Meeting;
import Model_Classes.Task;

import java.util.ArrayList;
import java.util.Date;

/**
 * This class checks if there are clashes in timings for events
 */
public class CheckAnomaly {
    /**
     * Checks if the Meeting with fixed duration task has any clashes with any other meetings in the task list.
     * If there is a clash, returns true.
     * If there is no clash, returns false.
     * @param date Date of Meeting
     * @param duration Duration of Meeting
     * @param timeUnit Unit of the duration
     * @return true if there are time clashes, false if there are no time clashes.
     */
    public static Boolean checkTime(Date date, int duration, TimeUnit timeUnit) {
        Meeting meeting = new Meeting("null", date, duration, timeUnit);
        ArrayList<Task> curr = TaskList.currentList();
        for( int i = 0; i<TaskList.currentList().size(); i++ ) {
            if( curr.get(i) instanceof Meeting  ) {
                if(  ((Meeting) curr.get(i)).isFixedDuration() && checkOverlap(((Meeting) curr.get(i)), meeting) ) {
                    return true;
                } else if( !(((Meeting) curr.get(i)).isFixedDuration()) && checkIntersect( curr.get(i).getDate(), meeting) ) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Overload function for checkTime.
     * Checks if the FixedDuration task has any clashes with any other tasks in the task list.
     * If there is a clash, returns true.
     * If there is no clash, returns false.
     * @param at Date of the event we are checking.
     * @return true if there are time clashes, false if there are no time clashes.
     */
    public static Boolean checkTime(Date at){
        ArrayList<Task> curr = TaskList.currentList();
        // Goes down list of Tasks
        for( int i = 0; i<TaskList.currentList().size(); i++ ) {
            // If task is a meeting, checks if it has a fixed duration
            if ( curr.get(i) instanceof Meeting && ((Meeting) curr.get(i)).isFixedDuration() ) {
                if( checkIntersect(at, (Meeting) curr.get(i)) ) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if a timing clashes with the duration of another meeting.
     * @param time Timing we are checking.
     * @param meeting Meeting we are checking.
     * @return True if the two timings clash and False if there is no clash.
     */
    public static Boolean checkIntersect(Date time, Meeting meeting) {
        Date rangeTime = meeting.getDate();
        if( rangeTime.getYear() == time.getYear() && rangeTime.getMonth() == time.getMonth() && rangeTime.getDay() == time.getDay() ) {
            long meetingTime = meeting.getDate().getTime();
            long currTime = time.getTime();
            long duration = timeToMilSeconds(meetingTime, meeting.getTimeUnit());
            if(meetingTime <= currTime + duration && meetingTime >= currTime || meetingTime + duration <= currTime + duration && meetingTime + duration >= currTime) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the timings of two Meetings overlap.
     * @param first First Meeting input.
     * @param second Second Meeting input.
     * @return True if there is an overlap and false if there is no overlap.
     */
    public static Boolean checkOverlap(Meeting first, Meeting second) {
        Date date1 = first.getDate();
        Date date2 = second.getDate();
        if( date1.getYear() == date2.getYear() && date1.getMonth() == date2.getMonth() && date1.getDay() == date2.getDay() ) {
            long duration1 = timeToMilSeconds(Integer.parseInt(first.getDuration()), first.getTimeUnit());
            long duration2 = timeToMilSeconds(Integer.parseInt(second.getDuration()), second.getTimeUnit());
            long time1 = date1.getTime();
            long time2 = date2.getTime();
            if( (duration1 <= duration2 + time2 && duration1 >= duration2) || (duration2 <= duration1 + time1 && duration2 >= duration1) ) {
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
    public static long timeToMilSeconds(long duration, TimeUnit unit) {
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

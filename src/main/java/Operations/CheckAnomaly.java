package Operations;

import Enums.TimeUnit;
import Model_Classes.Meeting;
import Model_Classes.Task;

import java.util.ArrayList;
import java.util.Date;

/**
 * This class checks if there are clashes in timings for meetings.
 */
public class CheckAnomaly {

    /**
     * Checks for tasks with the same description when adding a new task.
     * @param task task we are checking
     * @return current index if duplicate detected and -1 if no duplicate detected
     */
    public static int isDuplicate(Task task) {
        String name = task.getDescription();
        String assignee = task.getAssignee();
        String date = task.getDate().toString();
        for (int i = 0; i < TaskList.getCurrentList().size(); i++) {
            boolean isSameDescription = TaskList.getCurrentList().get(i).getDescription().equals(name);
            boolean isSameAssignee = TaskList.getCurrentList().get(i).getAssignee().equals(assignee);
            boolean isSameDate = TaskList.getCurrentList().get(i).getDate().toString().equals(date);
            boolean isSameClass = TaskList.getCurrentList().get(i).getClass().equals(task.getClass());
            if (isSameDescription && isSameAssignee
                    && isSameClass && isSameDate) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Checks for tasks in the overdue list for duplicates.
     * @param task task to be checked
     * @return true if duplicate found in overdue list
     */
    static Boolean isDuplicateOverdue(Task task) {
        String name = task.getDescription();
        String assignee = task.getAssignee();
        String date = task.getDate().toString();
        ArrayList<Task> temp = OverdueList.getOverdueList();
        for (Task value : temp) {
            boolean isSameDescription = value.getDescription().equals(name);
            boolean isSameAssignee = value.getAssignee().equals(assignee);
            boolean isSameDate = value.getDate().toString().equals(assignee);
            boolean isSameClass = value.getClass().equals(task.getClass());
            if (isSameDescription && isSameAssignee
                    && isSameDate && isSameClass) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks time clashes in RoomShare for meetings.
     * Checks first if the task is a meeting, then decides which check function
     * to use depending on whether the meeting has a fixed duration.
     * @param task task we are checking
     * @return current index if there is a time clash, -1 if there is no clash.
     */
    public static int isTimeClash(Task task) {
        if (task instanceof Meeting) {
            if (((Meeting) task).isFixedDuration()) {
                return isTimeDuration(task);
            } else {
                return isTime(task);
            }
        }
        return -1;
    }

    /**
     * Checks if the Meeting with fixed duration task has any clashes with any other meetings in the task list.
     * @param task task we are checking
     * @return current index if there are time clashes, -1 if there are no time clashes.
     */
    private static int isTimeDuration(Task task) {
        ArrayList<Task> curr = TaskList.getCurrentList();
        for (int i = 0; i < TaskList.getCurrentList().size(); i++) {
            if (curr.get(i) instanceof Meeting) {
                if (((Meeting) curr.get(i)).isFixedDuration() && isOverlap(curr.get(i), task)) {
                    return i;
                } else if (!(((Meeting) curr.get(i)).isFixedDuration())
                        && isIntersect(curr.get(i).getDate(), task)) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Checks if the Meeting with no fixed duration has any clashes with any other tasks in the task list.
     * @param task task we are checking for time clashes
     * @return current index if there are time clashes, -1 if there are no time clashes.
     */
    private static int isTime(Task task){
        Date at = task.getDate();
        ArrayList<Task> curr = TaskList.getCurrentList();
        // Goes down list of Tasks
        for (int i = 0; i < TaskList.getCurrentList().size(); i++) {
            // If task is a meeting, checks if it has a fixed duration
            if (curr.get(i) instanceof Meeting) {
                long check1 = curr.get(i).getDate().getTime() / 10000 * 10000;
                long check2 = at.getTime() / 10000 * 10000;
                if (((Meeting) curr.get(i)).isFixedDuration()) {
                    if (isIntersect(at, curr.get(i))) {
                        return i;
                    }
                } else if (check1 == check2) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Checks if a timing clashes with the duration of another meeting.
     * @param time Timing we are checking.
     * @param task task we are checking.
     * @return True if the two timings clash and False if there is no clash.
     */
    private static Boolean isIntersect(Date time, Task task) {
        Date rangeTime = task.getDate();
        if (rangeTime.getYear() == time.getYear()
                && rangeTime.getMonth() == time.getMonth()
                && rangeTime.getDay() == time.getDay()) {
            long meetingTime = task.getDate().getTime();
            long currTime = time.getTime();
            long duration;
            if (task instanceof Meeting) {
                String durationAsString = ((Meeting) task).getDuration();
                long taskDuration = Long.parseLong(durationAsString);
                TimeUnit timeUnit = ((Meeting) task).getTimeUnit();
                duration = timeToMilSeconds(taskDuration, timeUnit);
            } else {
                // task is a Leave
                return false;
            }
            return currTime < meetingTime + duration && currTime >= meetingTime;
        }
        return false;
    }

    /**
     * Checks if the timings of two Meetings overlap.
     * @param first First task input.
     * @param second Second task input.
     * @return True if there is an overlap and false if there is no overlap.
     */
    private static Boolean isOverlap(Task first, Task second) {
        Date date1 = first.getDate();
        Date date2 = second.getDate();
        boolean isSameYear = date1.getYear() == date2.getYear();
        boolean isSameMonth = date1.getMonth() == date2.getMonth();
        boolean isSameDay = date1.getDay() == date2.getDay();
        if (isSameYear && isSameMonth && isSameDay) {
            long duration1;
            long duration2;
            if (first instanceof Meeting) {
                String durationFirstString = ((Meeting) first).getDuration();
                int durationFirst = Integer.parseInt(durationFirstString);
                TimeUnit timeUnitFirst = ((Meeting) first).getTimeUnit();
                duration1 = timeToMilSeconds(durationFirst, timeUnitFirst);
            } else {
                // task is a leave, no need to check leave
                return false;
            }
            if (second instanceof Meeting) {
                String durationSecondString = ((Meeting) second).getDuration();
                int durationSecond = Integer.parseInt(durationSecondString);
                TimeUnit timeUnitSecond = ((Meeting) second).getTimeUnit();
                duration2 = timeToMilSeconds(durationSecond, timeUnitSecond);
            } else {
                // task is a leave, no need to check leave
                return false;
            }
            long time1 = date1.getTime();
            long time2 = date2.getTime();
            boolean isOverlapTime1 = (time1 < time2 + duration2 && time1 >= time2);
            boolean isOverlapTime2 = (time2 < time1 + duration1 && time2 >= time1);

            return isOverlapTime1 || isOverlapTime2;
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

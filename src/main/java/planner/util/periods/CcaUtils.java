package planner.util.periods;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import planner.exceptions.original.ModInvalidTimePeriodException;
import planner.modules.inherited.Task;
import planner.modules.inherited.TaskWithPeriod;

public class CcaUtils {

    /**
     * Get free time slots for a specific date.
     * @param localDate input date
     * @return a list of free time slots
     */
    public static ArrayList<TimePeriodSpanning> getFreeTimePeriods(
            LocalDate localDate, ArrayList<TaskWithPeriod> tasksInDate) {
        ArrayList<TimePeriodSpanning> ret = new ArrayList<>();
        LocalTime begin = LocalTime.MIN;
        LocalTime end;
        for (Task task: tasksInDate) {
            TaskWithPeriod currentTask = ((TaskWithPeriod) task);
            end = currentTask.getBeginTime();
            if (end.isAfter(begin)) {
                try {
                    ret.add(new TimePeriodSpanning(begin, end));
                } catch (ModInvalidTimePeriodException ex) {
                    begin = currentTask.getEndTime();
                    continue;
                }
            }
            begin = currentTask.getEndTime();
        }
        return ret;
    }
}

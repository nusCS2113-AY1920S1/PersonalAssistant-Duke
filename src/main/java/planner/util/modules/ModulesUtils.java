package planner.util.modules;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import planner.logic.exceptions.legacy.ModInvalidTimePeriodException;
import planner.logic.modules.legacy.task.Task;
import planner.logic.modules.legacy.task.TaskWithPeriod;
import planner.util.legacy.periods.TimePeriodSpanning;

public class ModulesUtils {

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

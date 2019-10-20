package planner.util;

import planner.command.ScheduleCommand;
import planner.exceptions.ModInvalidTimePeriodException;
import planner.modules.Task;
import planner.modules.TaskWithPeriod;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class CcaUtils {

    /**
     * Get free time slots for a specific date.
     * @param localDate input date
     * @return a list of free time slots
     */
    public static ArrayList<TimePeriodSpanning> getFreeTimePeriods(LocalDate localDate, TaskList tasks) {
        ArrayList<TaskWithPeriod> tasksInDate = ScheduleCommand.getTasksIn(localDate, tasks, true);
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

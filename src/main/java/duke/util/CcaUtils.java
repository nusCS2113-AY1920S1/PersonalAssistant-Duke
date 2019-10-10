package duke.util;

import duke.command.ScheduleCommand;
import duke.exceptions.ModInvalidTimePeriodException;
import duke.modules.Task;
import duke.modules.TaskWithPeriod;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class CcaUtils {

    /**
     * Get free time slots for a specific date.
     * @param localDate input date
     * @return a list of free time slots
     */
    public static ArrayList<TimePeriod> getFreeTimePeriods(LocalDate localDate, TaskList tasks) {
        ArrayList<Task> tasksInDate = ScheduleCommand.getTasksIn(localDate, tasks, true);
        ArrayList<TimePeriod> ret = new ArrayList<>();
        LocalDateTime begin = LocalDateTime.of(localDate, LocalTime.MIN);
        LocalDateTime end;
        for (Task task: tasksInDate) {
            TaskWithPeriod currentTask = ((TaskWithPeriod) task);
            end = currentTask.getBegin();
            if (end.isAfter(begin)) {
                try {
                    ret.add(new TimePeriod(begin, end));
                } catch (ModInvalidTimePeriodException ex) {
                    begin = currentTask.getEnd();
                    continue;
                }
            }
            begin = currentTask.getEnd();
        }
        return ret;
    }
}

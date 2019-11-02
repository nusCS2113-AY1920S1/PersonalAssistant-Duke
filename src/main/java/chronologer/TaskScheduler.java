package chronologer;

import chronologer.task.Event;
import chronologer.task.TaskList;
import chronologer.ui.UiTemporary;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public final class TaskScheduler {

    public static void scheduleByDeadline(TaskList tasks, Long durationToSchedule, LocalDateTime deadlineDate) {
        ArrayList<Event> dateList = tasks.obtainEventList(deadlineDate);
        if (dateList.size() == 0) {
            UiTemporary.printOutput("You can schedule this task from now till the deadline.\n");
            return;
        }

        Long duration;
        LocalDateTime nextStartDate = dateList.get(0).getStartDate();
        duration = ChronoUnit.HOURS.between(LocalDateTime.now(), nextStartDate);
        if (durationToSchedule <= duration) {
            UiTemporary.printOutput("You can schedule this task from now till " + nextStartDate);
            return;
        }

        boolean isFreeBetweenEvents = false;
        for (int i = 0; i < dateList.size(); i++) {
            LocalDateTime currentEndDate = dateList.get(i).getEndDate();
            if (i == dateList.size() - 1) {
                nextStartDate = deadlineDate;
                if (currentEndDate.isAfter(deadlineDate)) {
                    currentEndDate = deadlineDate;
                }
            } else {
                nextStartDate = dateList.get(i + 1).getStartDate();
            }

            duration = ChronoUnit.HOURS.between(currentEndDate, nextStartDate);
            if (durationToSchedule <= duration) {
                isFreeBetweenEvents = true;
                UiTemporary.printOutput("You can schedule this task from " + currentEndDate
                        + " till " + nextStartDate);
                break;
            }
        }

        if (!isFreeBetweenEvents) {
            UiTemporary.printOutput("There is no free slot to insert the task. Consider freeing up your schedule.");
        }
    }

    public static void scheduleTask(TaskList tasks, Long durationToSchedule) {

    }
}

package chronologer;

import chronologer.task.Event;
import chronologer.task.TaskList;
import chronologer.ui.UiTemporary;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public final class TaskScheduler {

    private static final int SEARCH_HARD_LIMIT = 30;

    public static void scheduleByDeadline(TaskList tasks, Long durationToSchedule, LocalDateTime deadlineDate) {
        ArrayList<Event> dateList = tasks.obtainEventList(deadlineDate);
        if (dateList.size() == 0) {
            UiTemporary.printOutput("You can schedule this task from now till the deadline.\n");
            return;
        }

        searchFreePeriodsInEventList(durationToSchedule, deadlineDate, dateList);
    }

    public static void scheduleTask(TaskList tasks, Long durationToSchedule) {
        LocalDateTime deadlineDate = LocalDateTime.now().plusDays(SEARCH_HARD_LIMIT);
        ArrayList<Event> eventList = tasks.obtainEventList(deadlineDate);
        if (eventList.size() == 0) {
            UiTemporary.printOutput("You can schedule this task anytime.\n");
            return;
        }

        searchFreePeriodsInEventList(durationToSchedule, deadlineDate, eventList);
    }

    private static void searchFreePeriodsInEventList(Long durationToSchedule, LocalDateTime deadlineDate, ArrayList<Event> eventList) {
        Long duration;
        LocalDateTime nextStartDate = eventList.get(0).getStartDate();
        duration = ChronoUnit.HOURS.between(LocalDateTime.now(), nextStartDate);
        if (durationToSchedule <= duration) {
            UiTemporary.printOutput("You can schedule this task from now till " + nextStartDate);
            return;
        }

        boolean isFreeBetweenEvents = false;
        for (int i = 0; i < eventList.size(); i++) {
            LocalDateTime currentEndDate = eventList.get(i).getEndDate();
            if (i == eventList.size() - 1) {
                nextStartDate = deadlineDate;
                if (currentEndDate.isAfter(deadlineDate)) {
                    currentEndDate = deadlineDate;
                }
            } else {
                nextStartDate = eventList.get(i + 1).getStartDate();
            }

            duration = ChronoUnit.HOURS.between(currentEndDate, nextStartDate);
            if (durationToSchedule <= duration) {
                isFreeBetweenEvents = true;
                UiTemporary.printOutput("You can schedule this task from " + currentEndDate
                        + " till " + nextStartDate + "\n");
            }
        }

        if (!isFreeBetweenEvents) {
            UiTemporary.printOutput("There is no free slot to insert the task. Consider freeing up your schedule.");
        }
    }
}

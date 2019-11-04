package chronologer;

import chronologer.parser.DateTimeExtractor;
import chronologer.task.Event;
import chronologer.task.TaskList;
import chronologer.ui.UiTemporary;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public final class TaskScheduler {

    private static final int SEARCH_HARD_LIMIT = 30;
    private static final String SCHEDULE_ANYTIME_BY_DEADLINE =
            "You can schedule this task from now till the deadline.\n";
    private static final String SCHEDULE_ANYTIME = "You can schedule this task anytime.\n";
    private static final String SCHEDULE_NOW_TILL_FORMAT = "You can schedule this task from now till %s\n";
    private static final String SCHEDULE_FROM_TILL_FORMAT = "You can schedule this task from %s till %s\n";
    private static final String NO_FREE_SLOTS =
            "There is no free slot to insert the task. Consider freeing up your schedule.\n";

    /**
     * Finds a free period of time within the user's schedule for a given duration by a given deadline.
     * @param tasks is the master task list in the program
     * @param durationToSchedule is the minimum duration to find a large enough period that is free
     * @param deadlineDate is the date to find any periods by
     */
    public static void scheduleByDeadline(TaskList tasks, Long durationToSchedule, LocalDateTime deadlineDate) {
        ArrayList<Event> dateList = tasks.obtainEventList(deadlineDate);
        if (dateList.size() == 0) {
            UiTemporary.printOutput(SCHEDULE_ANYTIME_BY_DEADLINE);
            return;
        }

        searchFreePeriodsInEventList(durationToSchedule, deadlineDate, dateList);
    }

    /**
     * Finds a free period of time within the user's schedule for a given duration without concerning a deadline.
     * @param tasks is the master task list in the program
     * @param durationToSchedule is the minimum duration to find a large enough period that is free
     */
    public static void scheduleTask(TaskList tasks, Long durationToSchedule) {
        LocalDateTime deadlineDate = LocalDateTime.now().plusDays(SEARCH_HARD_LIMIT);
        ArrayList<Event> eventList = tasks.obtainEventList(deadlineDate);
        if (eventList.size() == 0) {
            UiTemporary.printOutput(SCHEDULE_ANYTIME);
            return;
        }

        searchFreePeriodsInEventList(durationToSchedule, deadlineDate, eventList);
    }

    private static void searchFreePeriodsInEventList(Long durationToSchedule, LocalDateTime deadlineDate,
                                                     ArrayList<Event> eventList) {
        boolean isFreeBetweenEvents = false;
        Long duration;
        LocalDateTime nextStartDate = eventList.get(0).getStartDate();
        duration = ChronoUnit.HOURS.between(LocalDateTime.now(), nextStartDate);
        if (durationToSchedule <= duration) {
            isFreeBetweenEvents = true;
            String formattedNextStartDate = nextStartDate.format(DateTimeExtractor.DATE_FORMATTER);
            UiTemporary.loadMultiLineOutput(String.format(SCHEDULE_NOW_TILL_FORMAT, formattedNextStartDate));
        }

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
                String formattedCurrentEndDate = currentEndDate.format(DateTimeExtractor.DATE_FORMATTER);
                String formattedNextStartDate = nextStartDate.format(DateTimeExtractor.DATE_FORMATTER);
                UiTemporary.loadMultiLineOutput(String.format(SCHEDULE_FROM_TILL_FORMAT,
                        formattedCurrentEndDate, formattedNextStartDate));
            }
        }

        if (!isFreeBetweenEvents) {
            UiTemporary.printOutput(NO_FREE_SLOTS);
            return;
        }

        UiTemporary.printMultiLineOutput();
    }
}

package chronologer;

import chronologer.parser.DateTimeExtractor;
import chronologer.task.Event;
import chronologer.task.TaskList;
import chronologer.ui.MessageBuilder;

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

    private static ArrayList<Event> eventList;
    private static LocalDateTime hardLimitDeadlineDate = LocalDateTime.now().plusDays(SEARCH_HARD_LIMIT);
    private static boolean isFreeBetweenEvents;

    /**
     * Finds a free period of time within the user's schedule for a given duration by a given deadline.
     * @param tasks is the master task list in the program
     * @param durationToSchedule is the minimum duration to find a large enough period that is free
     * @param deadlineDate is the date to find any periods by
     */
    public static String scheduleByDeadline(TaskList tasks, Long durationToSchedule, LocalDateTime deadlineDate) {
        assert tasks != null;
        assert durationToSchedule != null;
        assert deadlineDate != null;

        setupEventList(tasks, deadlineDate);
        if (isEventListEmpty()) {
            return SCHEDULE_ANYTIME_BY_DEADLINE;
        }
        MessageBuilder.initialiseMessage();
        searchFreePeriodsInEventList(durationToSchedule, deadlineDate);
        return MessageBuilder.getMessage();
    }

    /**
     * Finds a free period of time within the user's schedule for a given duration without concerning a deadline.
     * @param tasks is the master task list in the program
     * @param durationToSchedule is the minimum duration to find a large enough period that is free
     */
    public static String scheduleTask(TaskList tasks, Long durationToSchedule) {
        assert tasks != null;
        assert durationToSchedule != null;

        setupEventList(tasks, hardLimitDeadlineDate);
        if (isEventListEmpty()) {
            return SCHEDULE_ANYTIME;
        }
        MessageBuilder.initialiseMessage();
        searchFreePeriodsInEventList(durationToSchedule, hardLimitDeadlineDate);
        return MessageBuilder.getMessage();
    }

    private static void setupEventList(TaskList tasks, LocalDateTime deadlineDate) {
        eventList = tasks.obtainEventList(deadlineDate);
    }

    private static boolean isEventListEmpty() {
        return eventList.size() == 0;
    }

    private static void searchFreePeriodsInEventList(Long durationToSchedule, LocalDateTime deadlineDate) {
        assert eventList.size() != 0;

        if (isFreeFromNowTillFirstEvent(durationToSchedule)) {
            loadResult();
        }
        for (int i = 0; i < eventList.size() - 1; i++) {
            if (isFreeBetweenThisEventTillNextEvent(durationToSchedule, i)) {
                loadResult(i);
            }
        }
        if (isFreeBetweenLastEventTillDeadline(durationToSchedule, deadlineDate)) {
            loadResult(deadlineDate);
        }

        if (!isFreeBetweenEvents) {
            MessageBuilder.loadMessage(NO_FREE_SLOTS);
        }
    }

    private static boolean isFreeFromNowTillFirstEvent(Long durationToSchedule) {
        LocalDateTime nextStartDate = eventList.get(0).getStartDate();
        Long duration = ChronoUnit.HOURS.between(LocalDateTime.now(), nextStartDate);
        if (durationToSchedule <= duration) {
            isFreeBetweenEvents = true;
            return true;
        }
        return false;
    }

    private static boolean isFreeBetweenThisEventTillNextEvent(Long durationToSchedule, int i) {
        LocalDateTime currentEndDate = eventList.get(i).getEndDate();
        LocalDateTime nextStartDate = eventList.get(i + 1).getStartDate();
        Long duration = ChronoUnit.HOURS.between(currentEndDate, nextStartDate);
        if (durationToSchedule <= duration) {
            isFreeBetweenEvents = true;
            return true;
        }
        return false;
    }

    private static boolean isFreeBetweenLastEventTillDeadline(Long durationToSchedule, LocalDateTime deadlineDate) {
        LocalDateTime currentEndDate = eventList.get(eventList.size() - 1).getEndDate();
        if (currentEndDate.isAfter(deadlineDate)) {
            return false;
        }
        Long duration = ChronoUnit.HOURS.between(currentEndDate, deadlineDate);
        if (durationToSchedule <= duration) {
            isFreeBetweenEvents = true;
            return true;
        }
        return false;
    }

    private static void loadResult() {
        String formattedNextStartDate = eventList.get(0).getStartDate().format(DateTimeExtractor.DATE_FORMATTER);
        MessageBuilder.loadMessage(String.format(SCHEDULE_NOW_TILL_FORMAT, formattedNextStartDate));
    }

    private static void loadResult(int index) {
        String formattedCurrentEndDate = eventList.get(index).getEndDate().format(DateTimeExtractor.DATE_FORMATTER);
        String formattedNextStartDate = eventList.get(index + 1).getStartDate()
                .format(DateTimeExtractor.DATE_FORMATTER);
        MessageBuilder.loadMessage(String.format(SCHEDULE_FROM_TILL_FORMAT, formattedCurrentEndDate,
                formattedNextStartDate));
    }

    private static void loadResult(LocalDateTime deadlineDate) {
        String formattedCurrentEndDate = eventList.get(eventList.size() - 1).getEndDate()
                .format(DateTimeExtractor.DATE_FORMATTER);
        String formattedDeadlineDate = deadlineDate.format(DateTimeExtractor.DATE_FORMATTER);
        MessageBuilder.loadMessage(String.format(SCHEDULE_FROM_TILL_FORMAT, formattedCurrentEndDate,
                formattedDeadlineDate));
    }
}

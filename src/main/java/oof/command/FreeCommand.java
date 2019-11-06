package oof.command;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

import oof.Ui;
import oof.exception.command.CommandException;
import oof.exception.command.InvalidArgumentException;
import oof.model.module.Lesson;
import oof.model.module.Module;
import oof.model.module.Semester;
import oof.model.module.SemesterList;
import oof.model.task.Deadline;
import oof.model.task.Event;
import oof.model.task.Task;
import oof.model.task.TaskList;
import oof.storage.StorageManager;

//@@author Kenlhc

/**
 * Represents a Command to search for free time slots.
 */
public class FreeCommand extends Command {

    public static final String COMMAND_WORD = "free";
    private String dateWanted;
    private static final int INDEX_DATE = 0;
    private static final int INDEX_TIME = 0;
    private static final int INDEX_TIME_START = 1;
    private static final int INDEX_TIME_END = 1;
    private static final int TOTAL_TIME_SLOTS = 17;
    private static final int SLOT_FREE = 0;
    private static final int SLOT_BUSY = 1;
    private static final int SUGGESTIONS_BLOCK = 4;
    private static final int ONE_WEEK = 7;
    private static final String FIRST_START_SLOT = "07:00";
    private static final String LAST_END_SLOT = "23:59";
    private String[] startingTimeSlots = {"07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00",
        "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
    private String[] endingTimeSlots = {"08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00",
        "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00", "23:59"};
    private ArrayList<Date> eventStartTimes = new ArrayList<>();
    private ArrayList<Date> eventEndTimes = new ArrayList<>();
    private ArrayList<Date> deadlinesDue = new ArrayList<>();
    private ArrayList<String> deadlineNames = new ArrayList<>();
    private ArrayList<String> sortedDeadlineNames = new ArrayList<>();
    private ArrayList<Integer> slotStates = new ArrayList<>();

    /**
     * Constructor for FreeCommand.
     *
     * @param dateWanted The date to search for free time slots given by user.
     */
    public FreeCommand(String dateWanted) {
        super();
        this.dateWanted = dateWanted;
    }

    /**
     * Finds free time during the queried time period.
     *
     * @param semesterList   Instance of SemesterList that stores Semester objects.
     * @param taskList       Instance of TaskList that contains list of tasks.
     * @param ui             Instance of Ui that is responsible for visual feedback.
     * @param storageManager Instance of Storage that enables the reading and writing of Task
     *                       objects to hard disk.
     * @throws CommandException if user input contains missing or invalid arguments.
     */
    @Override
    public void execute(SemesterList semesterList, TaskList taskList, Ui ui, StorageManager storageManager)
        throws CommandException {
        Date current = new Date();
        try {
            if (isDateAfterCurrentDate(current, dateWanted) || isDateSame(current, dateWanted)) {
                findFreeTime(semesterList, ui, taskList, this.dateWanted);
            } else {
                throw new InvalidArgumentException("OOPS!!! Please enter either today's date or a date in the future!");
            }
        } catch (ParseException e) {
            throw new InvalidArgumentException("OOPS!!! Please enter the date in the following format: DD-MM-YYYY");
        }
    }

    /**
     * Search for free time slots based on the current events recorded.
     *
     * @param ui       Instance of Ui that is responsible for visual feedback.
     * @param tasks    Instance of TaskList that stores Task Objects.
     * @param freeDate The user specified date.
     * @throws ParseException   exception may be thrown when parsing datetime.
     * @throws CommandException print customised error message.
     */
    private void findFreeTime(SemesterList semesterList, Ui ui, TaskList tasks, String freeDate)
        throws ParseException, CommandException {
        for (int i = 0; i < tasks.getSize(); i++) {
            Task task = tasks.getTask(i);
            if (task instanceof Event) {
                Event event = (Event) tasks.getTask(i);
                String dateStart = event.getStartDateTime().split(" ")[INDEX_DATE];
                String dateEnd = event.getEndDateTime().split(" ")[INDEX_DATE];
                String startTime = event.getStartDateTime().split(" ")[INDEX_TIME_START];
                String endTime = event.getEndDateTime().split(" ")[INDEX_TIME_END];
                populateEventTimes(dateStart, dateEnd, freeDate, startTime, endTime);
            } else if (task instanceof Deadline) {
                Deadline deadline = (Deadline) tasks.getTask(i);
                String dueDateAndTime = deadline.getDeadlineDateTime();
                String dueDate = deadline.getDeadlineDateTime().split(" ")[INDEX_DATE];
                String fullDescription = deadline.toString();
                boolean isCompleted = deadline.getStatus();
                populateDeadlines(dueDateAndTime, fullDescription, freeDate, dueDate, isCompleted);
            }
        }
        parseSemesterList(semesterList, freeDate);
        eventStartTimes.sort(new SortByTime());
        eventEndTimes.sort(new SortByTime());
        deadlinesDue.sort(new SortByTime());
        sortDeadlineNames();
        ui.printFreeTimeHeader(freeDate, getDayOfTheWeek(freeDate));
        parseSlotStates();
        parseOutput(ui);
    }

    /**
     * Parses the semester list for lessons that occurs within the queried date.
     *
     * @param semesterList Instance of SemesterList that stores Semester objects.
     * @param freeDate     The user specified date.
     */
    private void parseSemesterList(SemesterList semesterList, String freeDate) throws ParseException {
        for (Semester semester : semesterList.getSemesterList()) {
            Date startDate = convertStringToDate(semester.getStartDate());
            Date endDate = convertStringToDate(semester.getEndDate());
            Date freeSlotsDate = convertStringToDate(freeDate);
            if (isWithinRange(freeSlotsDate, startDate, endDate)) {
                queryModules(semester, freeSlotsDate);
            }
        }
    }

    /**
     * Adds lessons of each module to the calendar if they fall within the queried month.
     *
     * @param semester Instance of Semester containing Module data.
     * @param freeDate The user specified date.
     */
    private void queryModules(Semester semester, Date freeDate) throws ParseException {
        for (Module module : semester.getModules()) {
            addLessons(freeDate, module);
        }
    }

    /**
     * Adds lesson to the calendar.
     *
     * @param freeDate The user specified date.
     * @param module   Instance of Module containing Lesson data.
     */
    private void addLessons(Date freeDate, Module module) throws ParseException {
        String freeSlotsDate = convertDateToString(freeDate);
        for (Lesson lesson : module.getLessons()) {
            DayOfWeek day = lesson.getDay();
            String dayOfWeek = day.getDisplayName(TextStyle.FULL, Locale.getDefault());
            Date lessonStart = convertStringToTime(lesson.getStartTime());
            Date lessonEnd = convertStringToTime(lesson.getEndTime());
            if (getDayOfTheWeek(freeSlotsDate).equals(dayOfWeek) && !isDuplicateEvent(lessonStart, lessonEnd)) {
                eventStartTimes.add(lessonStart);
                eventEndTimes.add(lessonEnd);
            }
        }
    }

    /**
     * Parses the slot states for all time slots if they are free or busy.
     *
     * @throws InvalidArgumentException if user input contains invalid arguments.
     */
    private void parseSlotStates() throws CommandException {
        try {
            for (int i = 0; i < TOTAL_TIME_SLOTS; i++) {
                Date slotStart = convertStringToTime(startingTimeSlots[i]);
                Date slotEnd = convertStringToTime(endingTimeSlots[i]);
                if (eventStartTimes.isEmpty()) {
                    slotStates.add(SLOT_FREE);
                } else if (isClash(slotStart, slotEnd, eventStartTimes.get(INDEX_TIME),
                        eventEndTimes.get(INDEX_TIME))) {
                    slotStates.add(SLOT_BUSY);
                    removeEventTimes(slotEnd);
                } else {
                    slotStates.add(SLOT_FREE);
                }
            }
        } catch (DateTimeException | ParseException e) {
            throw new InvalidArgumentException("Timestamp given is invalid! Please try again.");
        }
    }

    /**
     * Removes event timings if the end timing is within the current time slot.
     *
     * @param endTimeSlot The end timing of the time slot.
     */
    private void removeEventTimes(Date endTimeSlot) {
        if (isEventEndTimeWithinSlot(endTimeSlot, eventEndTimes.get(INDEX_TIME))) {
            eventStartTimes.remove(INDEX_TIME);
            eventEndTimes.remove(INDEX_TIME);
        }
    }

    /**
     * Parses the output for finding free time slots if there are occupied slots.
     *
     * @param ui Prints relevant output.
     * @throws InvalidArgumentException if user input contains invalid arguments.
     */
    private void parseOutput(Ui ui) throws InvalidArgumentException {
        try {
            for (int i = 0; i < TOTAL_TIME_SLOTS; i++) {
                if (slotStates.get(i) == SLOT_FREE) {
                    ui.printFreeSlots(startingTimeSlots[i], endingTimeSlots[i]);
                } else if (slotStates.get(i) == SLOT_BUSY) {
                    ui.printBusySlots(startingTimeSlots[i], endingTimeSlots[i]);
                }
            }
            if (isSuggestionBlockPresent() && !sortedDeadlineNames.isEmpty()) {
                ui.printSuggestionDetails(sortedDeadlineNames);
            }
        } catch (DateTimeException e) {
            throw new InvalidArgumentException("Timestamp given is invalid! Please try again.");
        }
    }

    /**
     * Checks the list of slot states for a consecutive 4 hour free block.
     *
     * @return true if there is a 4 hour free block, false otherwise
     */
    private boolean isSuggestionBlockPresent() {
        int consecutiveFreeSlots = 0;
        for (int i = 0; i < TOTAL_TIME_SLOTS; i++) {
            if (consecutiveFreeSlots == SUGGESTIONS_BLOCK) {
                return true;
            } else if (SLOT_FREE == slotStates.get(i)) {
                consecutiveFreeSlots++;
            } else {
                consecutiveFreeSlots = 0;
            }
        }
        return false;
    }

    /**
     * Populates the lists for tracking event times.
     *
     * @param dateStart     Start date of event.
     * @param dateEnd       End date of event.
     * @param freeSlotsDate Date inputted by user.
     * @param startTime     Starting time of event.
     * @param endTime       Ending time of event.
     * @throws InvalidArgumentException if user input contains invalid arguments.
     */
    private void populateEventTimes(String dateStart, String dateEnd, String freeSlotsDate, String startTime,
            String endTime) throws InvalidArgumentException {
        try {
            Date freeDate = convertStringToDate(freeSlotsDate);
            Date eventStartTime = convertStringToTime(startTime);
            Date eventEndTime = convertStringToTime(endTime);
            Date eventStartDate = convertStringToDate(dateStart);
            Date eventEndDate = convertStringToDate(dateEnd);
            if (isEventDateWithin(dateStart, dateEnd, freeDate) && !isDuplicateEvent(eventStartTime, eventEndTime)) {
                if (isDateAfterCurrentDate(eventStartDate, freeSlotsDate)) {
                    eventStartTimes.add(convertStringToTime(FIRST_START_SLOT));
                    eventEndTimes.add(eventEndTime);
                } else if (isDateBeforeCurrentDate(eventEndDate, freeDate)) {
                    eventStartTimes.add(eventStartTime);
                    eventEndTimes.add(convertStringToTime(LAST_END_SLOT));
                } else {
                    eventStartTimes.add(eventStartTime);
                    eventEndTimes.add(eventEndTime);
                }
            }

        } catch (DateTimeException | ParseException e) {
            throw new InvalidArgumentException("Timestamp given is invalid! Please try again.");
        }
    }

    /**
     * Populates the list for upcoming deadlines.
     *
     * @param dueDateAndTime Due date and time of deadline.
     * @throws InvalidArgumentException if user input contains invalid arguments.
     */
    private void populateDeadlines(String dueDateAndTime, String fullDescription, String freeSlotsDate, String dueDate,
            boolean isCompleted) throws InvalidArgumentException {
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            Date upcomingDateAndTime = format.parse(dueDateAndTime);
            Date upcomingDate = convertStringToDate(dueDate);
            if (isDeadlineDueNextWeek(upcomingDate, freeSlotsDate) && !isDuplicateDeadline(upcomingDateAndTime)
                    && !isCompleted) {
                deadlinesDue.add(upcomingDateAndTime);
                deadlineNames.add(fullDescription);
                sortedDeadlineNames.add(fullDescription);
            }
        } catch (ParseException e) {
            throw new InvalidArgumentException("OOPS!!! Please enter the date in the following format: DD-MM-YYYY");
        }
    }

    /**
     * Checks for the list of events for duplicates.
     *
     * @param startTime The start time of the event
     * @return true if the list contains the same start and end time, false otherwise.
     */
    private boolean isDuplicateEvent(Date startTime, Date endTime) {
        if (eventStartTimes.isEmpty()) {
            return false;
        } else {
            return eventStartTimes.contains(startTime) && eventEndTimes.contains(endTime);
        }
    }

    /**
     * Checks the list of deadlines for duplicates.
     *
     * @param dueDateAndTime The date and time of the deadline.
     * @return true if the list contains the same date and time, false otherwise.
     */
    private boolean isDuplicateDeadline(Date dueDateAndTime) {
        if (deadlinesDue.isEmpty()) {
            return false;
        } else {
            return deadlinesDue.contains(dueDateAndTime);
        }
    }

    /**
     * Checks if the deadline is due within a week.
     *
     * @param dueDate The date of the deadline.
     * @return true if deadline is due within a week, false otherwise.
     */
    private boolean isDeadlineDueNextWeek(Date dueDate, String freeDate) throws ParseException {
        Date freeSlotsDate = convertStringToDate(freeDate);
        Calendar oneWeekFromFreeDate = Calendar.getInstance();
        oneWeekFromFreeDate.setTime(freeSlotsDate);
        oneWeekFromFreeDate.add(Calendar.DATE, ONE_WEEK);
        Date nextWeek = convertStringToDate(convertDateToString(oneWeekFromFreeDate.getTime()));
        return (dueDate.compareTo(nextWeek) <= 0 && dueDate.compareTo(freeSlotsDate) >= 0);
    }

    /**
     * Checks if there is an overlap of event timing with hourly time slots.
     *
     * @param slotStartTime Start time of the time slot being compared.
     * @param slotEndTime   End time of the time slot being compared.
     * @param eventStart    Start time of event being compared.
     * @param eventEnd      End time of event being compared.
     * @return true if there is an overlap of event timing.
     */
    private boolean isClash(Date slotStartTime, Date slotEndTime, Date eventStart, Date eventEnd) {
        return (slotStartTime.compareTo(eventStart) <= 0 && slotEndTime.compareTo(eventStart) > 0)
            || (slotStartTime.compareTo(eventStart) >= 0 && eventEnd.compareTo(slotEndTime) <= 0)
            || (slotStartTime.compareTo(eventStart) >= 0 && slotEndTime.compareTo(eventEnd) <= 0);
    }

    /**
     * Checks if event end time falls within the time slot.
     *
     * @param slotEndTime End time of the time slot being compared.
     * @param eventEnd    End time of event being compared.
     * @return true if the event end time lies within the time slot.
     */
    private boolean isEventEndTimeWithinSlot(Date slotEndTime, Date eventEnd) {
        return eventEnd.compareTo(slotEndTime) <= 0;
    }

    /**
     * Checks if the user specified date is before the current date being compared.
     *
     * @param currDate Current date being compared.
     * @param freeDate User specified date to search for free time.
     * @return true if user specified date is before the current date being compared.
     */
    private boolean isDateBeforeCurrentDate(Date currDate, Date freeDate) {
        return freeDate.compareTo(currDate) < 0;
    }

    /**
     * Checks if the user specified date is after the current date being compared.
     *
     * @param currDate Current date being compared.
     * @param freeDate User specified date to search for free time.
     * @return true if user specified date is after the current date being compared, false otherwise.
     * @throws ParseException throws an exception if date cannot be parsed.
     */
    private boolean isDateAfterCurrentDate(Date currDate, String freeDate) throws ParseException {
        Date freeSlotsDate = convertStringToDate(freeDate);
        return freeSlotsDate.compareTo(currDate) > 0;
    }

    /**
     * Checks if user specified date is current date.
     *
     * @param currDate Current Date.
     * @param freeDate User specified date to search for free time.
     * @return true if user specified date is current date.
     */
    private boolean isDateSame(Date currDate, String freeDate) {
        String currentDate = convertDateToString(currDate);
        return freeDate.equals(currentDate);
    }

    /**
     * Checks if event dates fall within the user specified date.
     *
     * @param eventStart Start date of event.
     * @param eventEnd   End date of event.
     * @param freeDate   User specified date to search for free time.
     * @return true if event dates are within the user specified date, false otherwise.
     * @throws ParseException throws an exception if date cannot be parsed.
     */
    private boolean isEventDateWithin(String eventStart, String eventEnd, Date freeDate) throws ParseException {
        Date startDate = convertStringToDate(eventStart);
        Date endDate = convertStringToDate(eventEnd);
        return freeDate.compareTo(startDate) == 0 || freeDate.compareTo(endDate) == 0;
    }

    /**
     * Comparator to sort events by their time in ascending order.
     */
    class SortByTime implements Comparator<Date> {
        @Override
        public int compare(Date firstStartTime, Date secondStartTime) {
            return firstStartTime.compareTo(secondStartTime);
        }
    }

    /**
     * Sorts the deadlines by their due dates in chronological order.
     */
    private void sortDeadlineNames() {
        for (int i = 0; i < sortedDeadlineNames.size(); i++) {
            String fullDescription = deadlineNames.get(i);
            for (int j = 0; j < deadlinesDue.size(); j++) {
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                String dueDate = format.format(deadlinesDue.get(j));
                if (fullDescription.contains(dueDate)) {
                    sortedDeadlineNames.set(j, fullDescription);
                }
            }
        }
    }

    /**
     * Gets the day of the week from the user specified date.
     *
     * @param freeDate The user specified date to search for free time.
     * @return the day of the week spelt in full.
     * @throws ParseException throws an exception if date cannot be parsed.
     */
    private String getDayOfTheWeek(String freeDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("EEEE");
        Date dayOfTheWeek = convertStringToDate(freeDate);
        return format.format(dayOfTheWeek);
    }

    /**
     * Checks if a date is within two dates.
     *
     * @param queryDate Date to be checked.
     * @param startDate Starting Date used for checking.
     * @param endDate   Ending Date used for checking.
     * @return true if queried date is within start and end date.
     */
    private boolean isWithinRange(Date queryDate, Date startDate, Date endDate) {
        return queryDate.compareTo(startDate) > 0 && queryDate.compareTo(endDate) < 0
            || queryDate.compareTo(startDate) == 0 || queryDate.compareTo(endDate) == 0;
    }

}
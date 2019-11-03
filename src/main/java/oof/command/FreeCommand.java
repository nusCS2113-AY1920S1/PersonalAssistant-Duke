
package oof.command;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import oof.Ui;
import oof.exception.OofException;
import oof.model.module.SemesterList;
import oof.model.task.Event;
import oof.model.task.Task;
import oof.model.task.TaskList;
import oof.storage.StorageManager;

/**
 * Represents a Command to search for free time slots.
 */
public class FreeCommand extends Command {

    public static final String COMMAND_WORD = "free";
    private String dateWanted;
    private ArrayList<Date> eventStartTimes = new ArrayList<>();
    private ArrayList<Date> eventEndTimes = new ArrayList<>();
    private static final int INDEX_DATE = 0;
    private static final int INDEX_TIME = 0;
    private static final int INDEX_TIME_START = 1;
    private static final int INDEX_TIME_END = 1;
    private static final int TOTAL_TIME_SLOTS = 17;
    private String[] startingTimeSlots = {"07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00",
            "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
    private String[] endingTimeSlots = {"08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00",
            "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00", "00:00"};

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
     * @throws OofException if user input invalid commands.
     */
    @Override
    public void execute(SemesterList semesterList, TaskList taskList, Ui ui, StorageManager storageManager)
            throws OofException {
        Date current = new Date();
        try {
            if (isDateAfterCurrentDate(current, dateWanted) || isDateSame(current, dateWanted)) {
                findFreeTime(ui, taskList, this.dateWanted);
            } else {
                throw new OofException("OOPS!!! Please enter a valid date!");
            }
        } catch (ParseException e) {
            throw new OofException("OOPS!!! Date is in the wrong format!");
        }
    }

    /**
     * Search for free time slots based on the current events recorded.
     *
     * @param ui            Instance of Ui that is responsible for visual feedback.
     * @param tasks         Instance of TaskList that stores Task Objects.
     * @param freeSlotsDate The user specified date.
     * @throws ParseException Exception may be thrown when parsing datetime.
     * @throws OofException   Print customised error message.
     */
    private void findFreeTime(Ui ui, TaskList tasks, String freeSlotsDate) throws ParseException, OofException {
        for (int i = 0; i < tasks.getSize(); i++) {
            Task task = tasks.getTask(i);
            if (task instanceof Event) {
                Event event = (Event) tasks.getTask(i);
                String dateStart = event.getStartDateTime().split(" ")[INDEX_DATE];
                String startTime = event.getStartDateTime().split(" ")[INDEX_TIME_START];
                String endTime = event.getEndDateTime().split(" ")[INDEX_TIME_END];
                populateList(dateStart, freeSlotsDate, startTime, endTime, event);
            }
        }
        if (eventStartTimes.isEmpty()) {
            ui.printFreeTimeHeader(freeSlotsDate, getDayOfTheWeek(freeSlotsDate));
            for (int i = 0; i < TOTAL_TIME_SLOTS; i++) {
                ui.printFreeSlots(startingTimeSlots[i], endingTimeSlots[i]);
            }
        } else {
            eventStartTimes.sort(new SortByTime());
            eventEndTimes.sort(new SortByTime());
            ui.printFreeTimeHeader(freeSlotsDate, getDayOfTheWeek(freeSlotsDate));
            parseOutput(ui);
        }
    }

    /**
     * Parses the output for finding free time slots if there are occupied slots.
     *
     * @param ui Prints relevant output.
     * @throws OofException Prints customised error message.
     */
    private void parseOutput(Ui ui) throws OofException {
        try {
            for (int i = 0; i < TOTAL_TIME_SLOTS; i++) {
                Date startTimeSlot = convertStringToDate(startingTimeSlots[i]);
                Date endTimeSlot = convertStringToDate(endingTimeSlots[i]);
                if (eventStartTimes.isEmpty()) {
                    ui.printFreeSlots(startingTimeSlots[i], endingTimeSlots[i]);
                } else if (isClash(startTimeSlot, endTimeSlot, eventStartTimes.get(INDEX_TIME),
                        eventEndTimes.get(INDEX_TIME))) {
                    ui.printEventDetails(startingTimeSlots[i], endingTimeSlots[i]);
                    if (isEventEndTimeWithinSlot(endTimeSlot, eventEndTimes.get(INDEX_TIME))) {
                        eventStartTimes.remove(INDEX_TIME);
                        eventEndTimes.remove(INDEX_TIME);
                    }
                } else {
                    ui.printFreeSlots(startingTimeSlots[i], endingTimeSlots[i]);
                }
            }
        } catch (DateTimeException | ParseException e) {
            throw new OofException("Timestamp given is invalid! Please try again.");
        }
    }

    /**
     * Populates the lists for tracking time and events.
     *
     * @param dateStart     Start date of task.
     * @param freeSlotsDate Date inputted by user.
     * @param startTime     Starting time of task.
     * @param endTime       Ending time of task.
     * @param event         Event task object.
     * @throws OofException Prints customised exception message.
     */
    private void populateList(String dateStart, String freeSlotsDate, String startTime, String endTime, Event event)
            throws OofException {
        try {
            if (isSameDate(dateStart, freeSlotsDate) && !isDuplicate(convertStringToDate(startTime))) {
                eventStartTimes.add(convertStringToDate(startTime));
                eventEndTimes.add(convertStringToDate(endTime));
            }
        } catch (DateTimeException | ParseException e) {
            throw new OofException("Timestamp given is invalid! Please try again.");
        }
    }

    /**
     * Checks for the list of events for duplicate timings.
     *
     * @param startTime The start time of the event
     * @return true if the list contains the same start time, false otherwise.
     */
    private boolean isDuplicate(Date startTime) {
        if (eventStartTimes.isEmpty()) {
            return false;
        } else {
            return eventStartTimes.contains(startTime);
        }
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
     * Checks if the event date is the same as the user specified date.
     *
     * @param eventStartDate Start date of event being compared.
     * @param freeSlotsDate  Date of free time to search for.
     * @return true if event date and user specified date is the same.
     */
    private boolean isSameDate(String eventStartDate, String freeSlotsDate) {
        return eventStartDate.equals(freeSlotsDate);
    }

    /**
     * Checks if user specified date is after the current date.
     * .
     *
     * @param currDate Current date.
     * @param freeDate User specified date to search for free time.
     * @return true if user specified date is after the current date, false otherwise.
     * @throws ParseException Throws an exception if date cannot be parsed.
     */
    private boolean isDateAfterCurrentDate(Date currDate, String freeDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date freeSlotsDate = format.parse(freeDate);
        return freeSlotsDate.compareTo(currDate) >= 0;
    }

    /**
     * Checks if user specified date is current date.
     *
     * @param currDate Current Date.
     * @param freeDate User specified date to search for free time.
     * @return true if user specified date is current date.
     */
    private boolean isDateSame(Date currDate, String freeDate) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = format.format(currDate);
        return currentDate.contains(freeDate);
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
     * Gets the day of the week from the user specified date.
     *
     * @param freeDate The user specified date to search for free time.
     * @return The day of the week spelt in full.
     * @throws ParseException Throws an exception if datetime cannot be parsed.
     */
    private String getDayOfTheWeek(String freeDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("EEEE");
        Date dayOfWeek = (new SimpleDateFormat("dd-MM-yyyy")).parse(freeDate);
        return format.format(dayOfWeek);
    }

}
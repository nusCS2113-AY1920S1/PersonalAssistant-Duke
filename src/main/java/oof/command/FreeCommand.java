
package oof.command;

import oof.Storage;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;
import oof.Ui;
import oof.exception.OofException;
import oof.model.task.Event;
import oof.model.task.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

/**
 * Represents a Command to search for free time slots.
 */
public class FreeCommand extends Command {

    private String dateWanted;
    private static final int EMPTY = 0;
    private static final int INDEX_TIME = 0;
    private static final int INDEX_NAME = 0;
    private static final int INDEX_START_TIME = 1;
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
     * @param semesterList Instance of SemesterList that stores Semester objects.
     * @param tasks        Instance of TaskList that stores Task objects.
     * @param ui           Instance of Ui that is responsible for visual feedback.
     * @param storage      Instance of Storage that enables the reading and writing of Task
     *                     objects to hard disk.
     * @throws OofException if user input invalid commands.
     */
    @Override
    public void execute(SemesterList semesterList, TaskList tasks, Ui ui, Storage storage) throws OofException {
        Date current = new Date();
        try {
            if (isDateAfterCurrentDate(current, dateWanted) || isDateSame(current, dateWanted)) {
                findFreeTime(ui, tasks, this.dateWanted);
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
     */
    private void findFreeTime(Ui ui, TaskList tasks, String freeSlotsDate) throws ParseException {
        ArrayList<String> eventsOnSameDay = new ArrayList<>();
        ArrayList<Date> eventStartTimes = new ArrayList<>();
        ArrayList<Date> eventEndTimes = new ArrayList<>();
        ArrayList<String> eventNamesSorted = new ArrayList<>();
        for (int i = 0; i < tasks.getSize(); i++) {
            Task task = tasks.getTask(i);
            if (task instanceof Event) {
                Event event = (Event) tasks.getTask(i);
                String date = event.getStartTime().substring(0, 10).trim();
                String startTime = event.getStartTime().substring(11, 16).trim();
                String endTime = event.getEndTime().substring(11, 16).trim();
                try {
                    if (isSameDate(date, freeSlotsDate)) {
                        String eventNameAndStartTime = event.getDescription() + "-" + startTime;
                        eventsOnSameDay.add(eventNameAndStartTime);
                        eventStartTimes.add(convertStringToDate(startTime));
                        eventEndTimes.add(convertStringToDate(endTime));
                    }
                } catch (DateTimeException | ParseException e) {
                    System.out.println("Timestamp given is invalid! Please try again.");
                }
            }
        }
        if (eventStartTimes.size() == EMPTY) {
            ui.printFreeTimeHeader(freeSlotsDate, getDayOfTheWeek(freeSlotsDate));
            for (int i = 0; i < 17; i++) {
                ui.printFreeSlots(startingTimeSlots[i], endingTimeSlots[i]);
            }
        } else {
            eventStartTimes.sort(new SortByTime());
            eventEndTimes.sort(new SortByTime());
            sortEventNames(eventsOnSameDay, eventStartTimes, eventNamesSorted);
            ui.printFreeTimeHeader(freeSlotsDate, getDayOfTheWeek(freeSlotsDate));
            for (int i = 0; i < 17; i++) {
                Date startTimeSlot = convertStringToDate(startingTimeSlots[i]);
                Date endTimeSlot = convertStringToDate(endingTimeSlots[i]);
                if (eventStartTimes.size() == EMPTY) {
                    ui.printFreeSlots(startingTimeSlots[i], endingTimeSlots[i]);
                } else if (isClash(startTimeSlot, endTimeSlot, eventStartTimes.get(INDEX_TIME),
                        eventEndTimes.get(INDEX_TIME))) {
                    ui.printEventDetails(eventNamesSorted.get(INDEX_NAME), startingTimeSlots[i], endingTimeSlots[i]);
                    if (isEventEndTimeWithinSlot(endTimeSlot, eventEndTimes.get(INDEX_TIME))) {
                        eventStartTimes.remove(INDEX_TIME);
                        eventEndTimes.remove(INDEX_TIME);
                        eventNamesSorted.remove(INDEX_NAME);
                    }
                } else {
                    ui.printFreeSlots(startingTimeSlots[i], endingTimeSlots[i]);
                }
            }
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
                || (slotStartTime.compareTo(eventStart) >= 0 && eventEnd.compareTo(slotEndTime) <= 0);
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
     * @param eventDate     Date of event being compared.
     * @param freeSlotsDate Date of free time to search for.
     * @return true if event date and user specified date is the same.
     */
    private boolean isSameDate(String eventDate, String freeSlotsDate) {
        return eventDate.equals(freeSlotsDate);
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
     * Sorts all event names according to their event start time.
     *
     * @param eventsOnSameDay  ArrayList containing the unsorted event name and start time.
     * @param eventStartTimes  ArrayList containing the start times sorted according in ascending order.
     * @param eventNamesSorted ArrayList containing the sorted event names according to start time.
     */
    private void sortEventNames(ArrayList<String> eventsOnSameDay, ArrayList<Date> eventStartTimes,
                                ArrayList<String> eventNamesSorted) {
        eventNamesSorted.addAll(eventsOnSameDay);
        for (int i = 0; i < eventsOnSameDay.size(); i++) {
            String[] lineSplit = eventsOnSameDay.get(i).split("-");
            String time = lineSplit[INDEX_START_TIME];
            String eventName = lineSplit[INDEX_NAME];
            for (int j = 0; j < eventsOnSameDay.size(); j++) {
                if (time.equals(convertDateToString(eventStartTimes.get(j)))) {
                    eventNamesSorted.set(j, eventName);
                    break;
                }
            }
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

    @Override
    public boolean isExit() {
        return false;
    }
}
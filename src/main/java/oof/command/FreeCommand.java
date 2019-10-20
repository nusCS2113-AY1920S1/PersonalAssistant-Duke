package oof.command;

import oof.Storage;
import oof.TaskList;
import oof.Ui;
import oof.exception.OofException;
import oof.task.Event;
import oof.task.Task;

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

    private String endTiming;
    private static final int EMPTY = 0;
    private static final int INDEX_DATE = 1;
    private static final int INDEX_START_DATE = 0;
    private static final int INDEX_END_DATE = 1;

    /**
     * Constructor for FreeCommand.
     *
     * @param endTiming Command inputted by user.
     */
    public FreeCommand(String endTiming) {
        super();
        this.endTiming = endTiming;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws OofException {
        Date current = new Date();
        try {
            if (isEndDateAfterCurrentTime(current, endTiming)) {
                findFreeTime(ui, taskList, this.endTiming, current);
            } else {
                throw new OofException("OOPS!!! Please enter a valid date and time!");
            }
        } catch (ParseException e) {
            throw new OofException("OOPS!!! Datetime is in the wrong format!");
        }
    }

    /**
     * Search for free time slots based on the current events recorded.
     *
     * @param ui Instance of Ui that is responsible for visual feedback.
     * @param taskList Instance of TaskList that stores Task Objects.
     * @param endTiming The user specified end date.
     * @param current Current time.
     * @throws ParseException Throws an exception if datetime cannot be parsed.
     */
    private void findFreeTime(Ui ui, TaskList taskList, String endTiming, Date current) throws ParseException {
        ui.printFree();
        int count = 1;
        Date rangeStop = convertStringToDate(endTiming);
        ArrayList<Date> eventStartTimes = new ArrayList<>();
        ArrayList<Date> eventEndTimes = new ArrayList<>();
        for (int i = 0; i < taskList.getSize(); i++) {
            Task task = taskList.getTask(i);
            if (task instanceof Event) {
                String[] lineSplit = task.toString().split("from: ");
                String[] dateSplit = lineSplit[INDEX_DATE].split("to: ");
                String start = dateSplit[INDEX_START_DATE].trim();
                String end = dateSplit[INDEX_END_DATE].substring(0, dateSplit[INDEX_END_DATE].length() - 1);
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                try {
                    Date startDate = format.parse(start);
                    Date endDate = format.parse(end);
                    if (!isEventOver(endDate, current)) {
                        eventStartTimes.add(startDate);
                        eventStartTimes.add(endDate);
                    }
                } catch (ParseException | DateTimeException e) {
                    System.out.println("Timestamp given is invalid! Please try again.");
                }
            }
        }
        if (!isEventBetween(eventStartTimes)) {
            ui.printFreeTimes(convertDateToString(current), convertDateToString(rangeStop), count);
        } else {
            eventStartTimes.sort(new SortByDate());
            eventEndTimes.sort(new SortByDate());
            for (int i = 0; i < eventStartTimes.size(); i++) {
                if (isOutOfRange(eventStartTimes.get(i), rangeStop)) {
                    ui.printFreeTimes(convertDateToString(current), convertDateToString(rangeStop), count);
                    break;
                } else if (!isClash(eventStartTimes.get(i), eventEndTimes.get(i), current)) {
                    ui.printFreeTimes(convertDateToString(current), convertDateToString(eventStartTimes.get(i)), count);
                    current = eventEndTimes.get(i);
                    count++;
                }
                if (isOutOfRange(current, rangeStop)) {
                    break;
                }
                if (isLastTask(i, taskList)) {
                    ui.printFreeTimes(convertDateToString(current), convertDateToString(rangeStop), count);
                }
            }
        }
    }

    /**
     * Checks if there is an overlap of current time and event timing.
     *
     * @param eventStartTime Start time of event being compared.
     * @param eventEndTime End time of event being compared.
     * @param currTime Current time.
     * @return true if there is an overlap of timings.
     */
    private boolean isClash(Date eventStartTime, Date eventEndTime, Date currTime) {
        return (currTime.compareTo(eventStartTime) >= 0 && currTime.compareTo(eventEndTime) <= 0);
    }

    /**
     * Checks if the event being compared has ended.
     *
     * @param eventEndTime End time of event being compared.
     * @param currTime Current time.
     * @return true if current time is after event end time.
     */
    private boolean isEventOver(Date eventEndTime, Date currTime) {
        return (currTime.compareTo(eventEndTime) >= 0);
    }

    /**
     * Checks if the current time being compared is out of the user specified end date.
     *
     * @param currTime Current time being compared.
     * @param rangeEnd End time for free time slots to be found.
     * @return true if current time occurs after end time, false otherwise.
     */
    private boolean isOutOfRange(Date currTime, Date rangeEnd) {
        return (currTime.compareTo(rangeEnd) >= 0);
    }

    /**
     * Checks if the task being compared is the final entry in the list.
     *
     * @param index Index of the task to be checked.
     * @param taskList Instance of TaskList that stores Task objects.
     * @return true if current task being compared is indeed the final entry, false otherwise.
     */
    private boolean isLastTask(int index, TaskList taskList) {
        return (index == taskList.getSize() - 1);
    }

    /**
     * Checks if user specified end date occurs after the current time
     * .
     * @param currTime Current Time.
     * @param end User specified end date.
     * @return true if end date occurs after current time, false otherwise.
     * @throws ParseException Throws an exception if datetime cannot be parsed.
     */
    private boolean isEndDateAfterCurrentTime(Date currTime, String end) throws ParseException {
        Date endDate = convertStringToDate(end);
        return endDate.compareTo(currTime) > 0;
    }

    /**
     * Checks if there is an event between the specified time.
     *
     * @param event The list of events between the specified time.
     * @return true if there are events between the specified time, false otherwise.
     */
    private boolean isEventBetween(ArrayList<Date> event) {
        return event.size() != EMPTY;
    }

    /**
     * Comparator to sort events by their date in ascending order.
     */
    class SortByDate implements Comparator<Date> {
        @Override
        public int compare(Date firstStartTime, Date secondStartTime) {
            return firstStartTime.compareTo(secondStartTime);
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
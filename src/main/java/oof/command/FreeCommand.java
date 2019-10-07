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
import java.util.Date;

/**
 * Represents a Command to search for free time slots.
 */
public class FreeCommand extends Command {

    private String stop;

    /**
     * Constructor for FreeCommand.
     *
     * @param stop Command inputted by user.
     */
    public FreeCommand(String stop) {
        super();
        this.stop = stop;
    }

    /**
     * Performs a series of tasks.
     * Get the current time.
     * Checks the validity of user specified end date.
     * Searches for free time between current time and the user specified end date.
     * Prints all the free time slots found.
     *
     * @param taskList  Instance of TaskList that stores Task Objects.
     * @param ui        Instance of Ui that is responsible for visual feedback.
     * @param storage   Instance of Storage that enables the reading and writing of Task
     *                  objects to hard disk.
     * @throws OofException Catches invalid commands given by user.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws OofException {
        Date current = new Date();
        try {
            if (isEndDateAfterCurrentTime(current, stop)) {
                findFreeTime(ui, taskList, this.stop, current);
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
     * @param stop The user specified end date.
     * @param current Current time.
     * @throws ParseException Throws an exception if datetime cannot be parsed.
     */
    private void findFreeTime(Ui ui, TaskList taskList, String stop, Date current) throws ParseException {
        ui.printFree();
        int count = 1;
        Date rangeStop = convertStringToDate(stop);
        for (int i = 0; i < taskList.getSize(); i++) {
            if (isOutOfRange(current, rangeStop)) {
                ui.printFreeTimings(convertDateToString(current), convertDateToString(rangeStop), count);
                break;
            }
            Task task = taskList.getTask(i);
            if (task instanceof Event) {
                String[] lineSplit = task.toString().split("from: ");
                String[] dateSplit = lineSplit[1].split("to: ");
                String start = dateSplit[0].trim();
                String end = dateSplit[1].substring(0, dateSplit[1].length() - 1); // remove closing bracket
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                try {
                    Date startDate = format.parse(start);
                    Date endDate = format.parse(end);
                    if (!isClash(startDate, endDate, current)) {
                        ui.printFreeTimings(convertDateToString(current), convertDateToString(startDate), count);
                        count++;
                    }
                    current = endDate;
                } catch (ParseException | DateTimeException e) {
                    System.out.println("Timestamp given is invalid! Please try again.");
                }
            }
            if (isLastTask(i, taskList)) {
                ui.printFreeTimings(convertDateToString(current), convertDateToString(rangeStop), count);
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
        return (currTime.compareTo(eventStartTime) >= 0
                && currTime.compareTo(eventEndTime) < 0);
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
     * Checks if user specified end date occurs after the current time.
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
     * Converts date to a string.
     *
     * @param date The date to be converted.
     * @return A string in the date format specified.
     */
    private String convertDateToString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return format.format(date);
    }

    /**
     * Converts a string into a date.
     *
     * @param date The string to be converted.
     * @return A date in the date format specified.
     * @throws ParseException Throws an exception if datetime cannot be parsed.
     */
    private Date convertStringToDate(String date) throws ParseException {
        return new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(date);
    }

    /**
     * Checks if ExitCommand is called for Oof to terminate.
     *
     * @return false.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
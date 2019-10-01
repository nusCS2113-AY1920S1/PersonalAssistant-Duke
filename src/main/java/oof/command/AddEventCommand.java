package oof.command;

import oof.TaskList;
import oof.Ui;
import oof.Storage;
import oof.exception.OofException;
import oof.task.Event;
import oof.task.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Represents a Command to add Event objects
 * to the TaskList.
 */
public class AddEventCommand extends Command {

    private String line;

    /**
     * Constructor for AddEventCommand.
     *
     * @param line Command inputted by user.
     */
    public AddEventCommand(String line) {
        super();
        this.line = line;
    }

    /**
     * Performs a series of three main tasks.
     * Processes the Command inputted by user into description and date.
     * Checks for the validity of the format of date.
     * Adds an Event object to the TaskList
     * and prints the object added before calling methods in Storage to
     * store the object added in the hard disk.
     *
     * @param arr     Instance of TaskList that stores Task objects.
     * @param ui      Instance of Ui that is responsible for visual feedback.
     * @param storage Instance of Storage that enables the reading and writing of Task
     *                objects to hard disk.
     * @throws OofException Catches invalid commands given by user.
     */
    public void execute(TaskList arr, Ui ui, Storage storage) throws OofException {
        String[] lineSplit = line.split(" /from ");
        if (!hasDescription(lineSplit)) {
            throw new OofException("OOPS!!! The event needs a description.");
        } else if (!hasStartDate(lineSplit)) {
            throw new OofException("OOPS!!! The event needs a start date.");
        } else if (!hasEndDate(lineSplit)) {
            throw new OofException("OOPS!!! The event needs an end date.");
        }
        String description = lineSplit[0].trim();
        String[] dateSplit = lineSplit[1].split(" /to ");
        String startDate = parseTimeStamp(dateSplit[0]);
        String endDate = parseTimeStamp(dateSplit[1]);
        if (isDateValid(startDate) && isDateValid(endDate)) {
            ArrayList<Event> eventClashes = new ArrayList<>();
            SimpleDateFormat format = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm");
            try {
                Date newEventStartTime = format.parse(dateSplit[0]);
                Date newEventEndTime = format.parse(dateSplit[1]);
                if (!isStartDateBeforeEndDate(newEventStartTime, newEventEndTime)) {
                    throw new OofException("OOPS!!! The start date of an event cannot be after the end date.");
                }
                for (Task t : arr.getTasks()) {
                    if (t instanceof Event) {
                        Event event = (Event) t;
                        Date currEventStartTime = format.parse(event.getStartTiming());
                        Date currEventEndTime = format.parse(event.getEndTiming());
                        if (isClash(newEventStartTime, newEventEndTime, currEventStartTime, currEventEndTime)) {
                            eventClashes.add(event);
                        }
                    }
                }
                boolean hasClashes = !eventClashes.isEmpty();
                if (hasClashes) {
                    ui.printClashWarning(eventClashes);
                    if (!isContinue(ui)) {
                        return;
                    }
                }
            } catch (ParseException e) {
                throw new OofException("Timestamp given is invalid! Please try again.");
            }
            Task task = new Event(description, dateSplit[0], dateSplit[1]);
            arr.addTask(task);
            ui.addTaskMessage(task, arr.getSize());
            storage.writeToFile(arr);
        } else if (isDateValid(startDate)) {
            throw new OofException("OOPS!!! The end date is invalid.");
        } else if (isDateValid(endDate)) {
            throw new OofException("OOPS!!! The start date is invalid.");
        } else {
            throw new OofException("OOPS!!! The start and end dates are invalid.");
        }
    }


    /**
     * Checks if input has a description.
     * @param lineSplit processed user input.
     * @return true if description is more than length 0 and is not whitespace.
     */
    private boolean hasDescription(String[] lineSplit) {
        return lineSplit[0].trim().length() > 0;
    }

    /**
     * Checks if input has a start date (argument given before "/to").
     * @param lineSplit processed user input.
     * @return true if there is a start date and start date is not whitespace.
     */
    private boolean hasStartDate(String[] lineSplit) {
        return lineSplit.length > 1 && lineSplit[1].split(" /to ")[0].trim().length() > 0;
    }

    /**
     * Checks if input has an end date (argument given after "/to").
     * @param lineSplit processed user input.
     * @return true if there is an end date and end date is not whitespace.
     */
    private boolean hasEndDate(String[] lineSplit) {
        String[] dateSplit = lineSplit[1].split(" /to ");
        return dateSplit.length > 1 && dateSplit[1].trim().length() > 0;
    }

    /**
     * Checks if start and end date are chronologically accurate.
     *
     * @param newEventStartTime Start time of event being added.
     * @param newEventEndTime   End time of event being added.
     * @return true if start date occurs before end date, false otherwise.
     */
    private boolean isStartDateBeforeEndDate(Date newEventStartTime, Date newEventEndTime) {
        return newEventStartTime.compareTo(newEventEndTime) <= 0;
    }

    /**
     * Checks if there is an overlap of event timing.
     *
     * @param newEventStartTime  Start time of event being added.
     * @param newEventEndTime    End time of event being added.
     * @param currEventStartTime Start time of event being compared.
     * @param currEventEndTime   End time of event being added.
     * @return true if there is an overlap of event timing.
     */
    private boolean isClash(Date newEventStartTime, Date newEventEndTime, Date currEventStartTime,
                            Date currEventEndTime) {
        return (newEventStartTime.compareTo(currEventStartTime) >= 0
                && newEventStartTime.compareTo(currEventEndTime) < 0)
                || (newEventEndTime.compareTo(currEventStartTime) > 0
                && newEventEndTime.compareTo(currEventEndTime) <= 0);
    }

    /**
     * Checks if user wants to continue adding an event when there is a clash in events.
     *
     * @param ui Instance of Ui that is responsible for visual feedback.
     * @return true if response equals "Y", false otherwise.
     */
    private boolean isContinue(Ui ui) {
        String response = ui.printContinuePrompt();
        return response.equals("Y");
    }

    /**
     * Checks if ExitCommand is called for Oof to terminate.
     *
     * @return false.
     */
    public boolean isExit() {
        return false;
    }
}

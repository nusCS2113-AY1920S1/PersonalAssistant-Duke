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
        if (hasValidDate(startDate) && hasValidDate(endDate)) {
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
                if (hasClashes(eventClashes)) {
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
        } else if (hasValidDate(startDate)) {
            throw new OofException("OOPS!!! The end date is invalid.");
        } else if (hasValidDate(endDate)) {
            throw new OofException("OOPS!!! The start date is invalid.");
        } else {
            throw new OofException("OOPS!!! The start and end dates are invalid.");
        }
    }

    private boolean hasClashes(ArrayList<Event> eventClashes) {
        return !eventClashes.isEmpty();
    }


    private boolean hasDescription(String[] lineSplit) {
        return lineSplit[0].trim().length() != 0;
    }

    private boolean hasStartDate(String[] lineSplit) {
        return lineSplit.length > 1 && lineSplit[1].split(" /to ")[0].trim().length() != 0;
    }

    private boolean hasEndDate(String[] lineSplit) {
        String[] dateSplit = lineSplit[1].split(" /to ");
        return dateSplit.length > 1 && dateSplit[1].trim().length() != 0;
    }

    private boolean hasValidDate(String date) {
        return !date.equals("failed");
    }

    private boolean isStartDateBeforeEndDate(Date newEventStartTime, Date newEventEndTime) {
        return newEventStartTime.compareTo(newEventEndTime) <= 0;
    }

    private boolean isClash(Date newEventStartTime, Date newEventEndTime, Date currEventStartTime,
                            Date currEventEndTime) {
        return (newEventStartTime.compareTo(currEventStartTime) >= 0
                && newEventStartTime.compareTo(currEventEndTime) < 0)
                || (newEventEndTime.compareTo(currEventStartTime) > 0
                && newEventEndTime.compareTo(currEventEndTime) <= 0);
    }

    private boolean isContinue(Ui ui) {
        return ui.printContinuePrompt();
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

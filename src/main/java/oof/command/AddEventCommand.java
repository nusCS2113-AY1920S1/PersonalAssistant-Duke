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
     * store the object added in the harddisk.
     *
     * @param arr     Instance of TaskList that stores Task objects.
     * @param ui      Instance of Ui that is responsible for visual feedback.
     * @param storage Instance of Storage that enables the reading and writing of Task
     *                objects to hard disk.
     * @throws OofException Catches invalid commands given by user.
     */
    public void execute(TaskList arr, Ui ui, Storage storage) throws OofException {
        String[] lineSplit = line.split(" /from ");
        if (lineSplit.length == 1) {
            throw new OofException("OOPS!!! The description of an event needs a date.");
        }
        String description = lineSplit[0].trim();
        String[] dateSplit = lineSplit[1].split(" /to ");
        if (dateSplit.length == 1) {
            throw new OofException("OOPS!!! The description of an event needs a date.");
        } else if (dateSplit[0].trim().length() == 0 || dateSplit[1].trim().length() == 0) {
            throw new OofException("OOPS!!! The datetime of an event cannot be empty.");
        } else {
            if (!parseTimeStamp(dateSplit[0]).equals("failed") && !parseTimeStamp(dateSplit[1]).equals("failed")) {
                ArrayList<Event> eventClashes = new ArrayList<>();
                SimpleDateFormat format = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm");
                try {
                    Date newEventStartTime = format.parse(dateSplit[0]);
                    Date newEventEndTime = format.parse(dateSplit[1]);
                    if (newEventStartTime.compareTo(newEventEndTime) > 0) {
                        throw new OofException("OOPS!!! The start date of an event cannot be after the end date.");
                    }
                    for (Task t : arr.getTasks()) {
                        if (t instanceof Event) {
                            Event event = (Event) t;
                            Date currEventStartTime = format.parse(event.getStartTiming());
                            Date currEventEndTime = format.parse(event.getEndTiming());
                            if ((newEventStartTime.compareTo(currEventStartTime) >= 0
                                    && newEventStartTime.compareTo(currEventEndTime) < 0)
                                    || (newEventEndTime.compareTo(currEventStartTime) > 0
                                    && newEventEndTime.compareTo(currEventEndTime) <= 0)) {
                                eventClashes.add(event);
                            }
                        }
                    }
                    if (!eventClashes.isEmpty()) {
                        ui.printClashWarning();
                        for (Event e : eventClashes) {
                            System.out.println(e.toString());
                        }
                        if (!ui.printContinuePrompt()) {
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
            }
        }
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

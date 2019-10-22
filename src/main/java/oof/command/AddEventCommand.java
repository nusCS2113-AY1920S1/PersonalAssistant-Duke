package oof.command;

import oof.model.module.SemesterList;
import oof.model.task.TaskList;
import oof.Ui;
import oof.Storage;
import oof.exception.OofException;
import oof.model.task.Event;
import oof.model.task.Task;

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
     * @param semesterList Instance of SemesterList that stores Semester objects.
     * @param tasks        Instance of TaskList that stores Task objects.
     * @param ui           Instance of Ui that is responsible for visual feedback.
     * @param storage      Instance of Storage that enables the reading and writing of Task
     *                     objects to hard disk.
     * @throws OofException if user input invalid commands.
     */
    public void execute(SemesterList semesterList, TaskList tasks, Ui ui, Storage storage) throws OofException {
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
            SimpleDateFormat format = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm");
            try {
                Date newStartTime = format.parse(dateSplit[0]);
                Date newEndTime = format.parse(dateSplit[1]);
                if (!isStartDateBeforeEndDate(newStartTime, newEndTime)) {
                    throw new OofException("OOPS!!! The start date of an event cannot be after the end date.");
                }
                ArrayList<Event> eventClashes = checkClashes(tasks, newStartTime, newEndTime);
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
            tasks.addTask(task);
            ui.addTaskMessage(task, tasks.getSize());
            storage.writeTaskList(tasks);
        } else if (isDateValid(startDate)) {
            throw new OofException("OOPS!!! The end date is invalid.");
        } else if (isDateValid(endDate)) {
            throw new OofException("OOPS!!! The start date is invalid.");
        } else {
            throw new OofException("OOPS!!! The start and end dates are invalid.");
        }
    }

    /**
     * Iterates through current task and checks if any events clashes with new event being added.
     *
     * @param arr          Instance of TaskList that stores Task objects.
     * @param newStartTime Start time of event.
     * @param newEndTime   End time of event.
     * @throws ParseException if time entry is invalid.
     */
    public ArrayList<Event> checkClashes(TaskList arr, Date newStartTime, Date newEndTime) throws ParseException {
        ArrayList<Event> clashes = new ArrayList<>();
        SimpleDateFormat format = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm");
        for (Task t : arr.getTasks()) {
            if (t instanceof Event) {
                Event event = (Event) t;
                Date currStartTime = format.parse(event.getStartTime());
                Date currEndTime = format.parse(event.getEndTime());
                if (isClash(newStartTime, newEndTime, currStartTime, currEndTime)) {
                    clashes.add(event);
                }
            }
        }
        return clashes;
    }


    /**
     * Checks if input has a description.
     *
     * @param lineSplit processed user input.
     * @return true if description is more than length 0 and is not whitespace.
     */
    private boolean hasDescription(String[] lineSplit) {
        return lineSplit[0].trim().length() > 0;
    }

    /**
     * Checks if input has a start date (argument given before "/to").
     *
     * @param lineSplit processed user input.
     * @return true if there is a start date and start date is not whitespace.
     */
    private boolean hasStartDate(String[] lineSplit) {
        return lineSplit.length > 1 && lineSplit[1].split(" /to ")[0].trim().length() > 0;
    }

    /**
     * Checks if input has an end date (argument given after "/to").
     *
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
     * @param startTime Start time of event being added.
     * @param endTime   End time of event being added.
     * @return true if start date occurs before end date, false otherwise.
     */
    private boolean isStartDateBeforeEndDate(Date startTime, Date endTime) {
        return startTime.compareTo(endTime) <= 0;
    }

    /**
     * Checks if there is an overlap of event timing.
     *
     * @param newStartTime  Start time of event being added.
     * @param newEndTime    End time of event being added.
     * @param currStartTime Start time of event being compared.
     * @param currEndTime   End time of event being added.
     * @return true if there is an overlap of event timing.
     */
    private boolean isClash(Date newStartTime, Date newEndTime, Date currStartTime, Date currEndTime) {
        return (newStartTime.compareTo(currStartTime) >= 0 && newStartTime.compareTo(currEndTime) < 0)
                || (newEndTime.compareTo(currStartTime) > 0 && newEndTime.compareTo(currEndTime) <= 0);
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

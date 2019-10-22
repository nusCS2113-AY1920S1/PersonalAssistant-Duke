package oof.command;

import oof.model.module.SemesterList;
import oof.model.task.TaskList;
import oof.Ui;
import oof.Storage;
import oof.exception.OofException;
import oof.model.task.Task;
import oof.model.task.Todo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.Date;

/**
 * Represents a Command to add Todo objects to TaskList.
 */
public class AddToDoCommand extends Command {

    private String line;
    private static final int INDEX_DESCRIPTION = 0;
    private static final int INDEX_DATE_ON = 1;

    /**
     * Constructor for AddTodoCommand.
     *
     * @param line Command inputted by user.
     */
    public AddToDoCommand(String line) {
        super();
        this.line = line;
    }

    /**
     * Performs a series of three main tasks.
     * Processes the Command inputted by user.
     * Adds the Todo object to TaskList
     * Stores the object in hard disk by calling Storage before printing the object added.
     *
     * @param semesterList Instance of SemesterList that stores Semester objects.
     * @param tasks     Instance of TaskList that stores Task objects.
     * @param ui      Instance of Ui that is responsible for visual feedback.
     * @param storage Instance of Storage that enables the reading and writing of Task
     *                objects to hard disk.
     * @throws OofException if user input invalid commands.
     */
    public void execute(SemesterList semesterList, TaskList tasks, Ui ui, Storage storage) throws OofException {
        String[] lineSplit = line.split("/on");
        if (!hasDescription(lineSplit)) {
            throw new OofException("OOPS!!! The todo needs a description.");
        } else if (!hasOnDate(lineSplit)) {
            throw new OofException("OOPS!!! The todo needs a date.");
        }
        String description = lineSplit[INDEX_DESCRIPTION].trim();
        String onDate = parseTimeStamp(lineSplit[INDEX_DATE_ON].trim());
        if (isDateValid(onDate)) {
            Task task = new Todo(description, onDate);
            tasks.addTask(task);
            storage.writeTaskList(tasks);
            ui.addTaskMessage(task, tasks.getSize());
        } else {
            throw new OofException("OOPS!!! The date is invalid.");
        }
    }

    @Override
    public String parseTimeStamp(String date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            Date parsed = format.parse(date);
            return format.format(parsed);
        } catch (ParseException | DateTimeException e) {
            return "failed";
        }
    }

    /**
     * Checks if input has a description.
     *
     * @return true if description is more than length 0 and is not whitespace.
     */
    private boolean hasDescription(String[] lineSplit) {
        return lineSplit[0].trim().length() > 0;
    }

    /**
     * Checks if input has a date.
     *
     * @param lineSplit processed user input.
     * @return true if there is a date and date is not whitespace.
     */
    private boolean hasOnDate(String[] lineSplit) {
        return lineSplit.length != 1 && lineSplit[INDEX_DATE_ON].trim().length() > 0;
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


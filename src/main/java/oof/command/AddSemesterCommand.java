package oof.command;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import oof.Ui;
import oof.exception.CommandException.CommandException;
import oof.exception.CommandException.InvalidArgumentException;
import oof.exception.CommandException.MissingArgumentException;
import oof.model.module.Semester;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;
import oof.storage.StorageManager;

public class AddSemesterCommand extends Command {

    public static final String COMMAND_WORD = "semester";
    private ArrayList<String> arguments;
    private static final int INDEX_YEAR = 0;
    private static final int INDEX_NAME = 1;
    private static final int INDEX_DATE_START = 2;
    private static final int INDEX_DATE_END = 3;
    private static final int SIZE_NAME = 2;
    private static final int SIZE_DATE_START = 3;
    private static final int SIZE_DATE_END = 4;


    /**
     * Constructor for AddSemesterCommand.
     *
     * @param arguments Command inputted by user for processing.
     */
    public AddSemesterCommand(ArrayList<String> arguments) {
        super();
        this.arguments = arguments;
    }

    /**
     * Checks if semester being added clashes with other semesters.
     *
     * @param semesterList Instance of SemesterList that stores Semesters.
     * @param startDate    Start date of Semester being added.
     * @param endDate      End date of Semester being added.
     * @return true if semester being added clashes with other semesters, false otherwise.
     * @throws OofException if start date is after end date or if date is invalid.
     */
    private boolean hasClashes(SemesterList semesterList, String startDate, String endDate) throws CommandException {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date dateStart = format.parse(startDate);
            Date dateEnd = format.parse(endDate);
            if (!isStartDateBeforeEndDate(dateStart, dateEnd)) {
                throw new InvalidArgumentException("OOPS!! The start date of a semester cannot be after the end date.");
            }
            for (Semester semester : semesterList.getSemesterList()) {
                Date semesterDateStart = format.parse(semester.getStartDate());
                Date semesterEndStart = format.parse(semester.getEndDate());
                if (isClash(dateStart, dateEnd, semesterDateStart, semesterEndStart)) {
                    return true;
                }
            }
        } catch (ParseException e) {
            throw new InvalidArgumentException("OOPS!! The date is invalid.");
        }
        return false;
    }

    /**
     * Adds a semester to semesterList.
     *
     * @param semesterList   Instance of SemesterList that stores Semester objects.
     * @param tasks          Instance of TaskList that stores Task objects.
     * @param ui             Instance of Ui that is responsible for visual feedback.
     * @param storageManager Instance of Storage that enables the reading and writing of Task
     *                       objects to hard disk.
     * @throws CommandException if user input contains missing or invalid arguments.
     */
    @Override
    public void execute(SemesterList semesterList, TaskList tasks, Ui ui, StorageManager storageManager)
            throws CommandException {
        if (arguments.get(INDEX_YEAR).isEmpty()) {
            throw new MissingArgumentException("OOPS!! The semester needs a year.");
        } else if (arguments.size() < SIZE_NAME || arguments.get(INDEX_NAME).isEmpty()) {
            throw new MissingArgumentException("OOPS!! The semester needs a name.");
        } else if (arguments.size() < SIZE_DATE_START || arguments.get(INDEX_DATE_START).isEmpty()) {
            throw new MissingArgumentException("OOPS!! The semester needs a start date.");
        } else if (arguments.size() < SIZE_DATE_END || arguments.get(INDEX_DATE_END).isEmpty()) {
            throw new MissingArgumentException("OOPS!! The semester needs an end date.");
        }
        String year = arguments.get(INDEX_YEAR);
        String name = arguments.get(INDEX_NAME);
        String startDate = arguments.get(INDEX_DATE_START);
        String endDate = arguments.get(INDEX_DATE_END);
        String description = year + " " + name;
        if (exceedsMaxLength(description)) {
            throw new InvalidArgumentException("Task exceeds maximum description length!");
        }
        if (isDateValid(startDate) && isDateValid(endDate) && (hasClashes(semesterList, startDate, endDate))) {
            throw new InvalidArgumentException("OOPS!! The semester clashes with another semester.");
        }
        Semester semester = new Semester(year, name, startDate, endDate);
        semesterList.addSemester(semester);
        ui.printSemesterAddedMessage(semester);
        storageManager.writeSemesterList(semesterList);
    }

    /**
     * Checks if start and end date are chronologically accurate.
     *
     * @param startTime Start date of semester being added.
     * @param endTime   End date of semester being added.
     * @return true if start date occurs before end date, false otherwise.
     */
    private boolean isStartDateBeforeEndDate(Date startTime, Date endTime) {
        return startTime.compareTo(endTime) <= 0;
    }

    /**
     * Checks if there is an overlap of semester date.
     *
     * @param newStartTime  Start date of semester being added.
     * @param newEndTime    End date of semester being added.
     * @param currStartTime Start date of semester being compared.
     * @param currEndTime   End date of semester being added.
     * @return true if there is an overlap of event timing.
     */
    private boolean isClash(Date newStartTime, Date newEndTime, Date currStartTime, Date currEndTime) {
        return (newStartTime.compareTo(currStartTime) >= 0 && newStartTime.compareTo(currEndTime) < 0)
                || (newEndTime.compareTo(currStartTime) > 0 && newEndTime.compareTo(currEndTime) <= 0);
    }
}
